package model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import communication.*
import server.embeddedMqtt
import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.util.presentationElements.Group
import model.util.attribute.*
import java.util.*

abstract class BaseModel(private val withServer: Boolean = false) : IModel {

    //******************************************************************************************************
    //Properties

    private var title               = ""
    private var allGroups           = mutableStateListOf<Group>()
    private var changedForAll       = mutableStateOf(false)
    private val validForAll         = mutableStateOf(true)
    private var currentLanguage     = mutableStateOf<String>(if (getPossibleLanguages().size > 0) getPossibleLanguages()[0] else "")



    val mqttBroker    = "localhost"
    val mainTopic     = "/fhnwforms/"
    open val mqttConnectorText = MqttConnector(mqttBroker, mainTopic)
    open val mqttConnectorCommand = MqttConnector(mqttBroker, mainTopic)
    open val mqttConnectorValidation = MqttConnector(mqttBroker, mainTopic)
    open val mqttConnectorAttribute = MqttConnector(mqttBroker, mainTopic)

    private val modelScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    var startedUp = false

    private fun startUp(){
        if(withServer)
        modelScope.launch {
            if(!startedUp) {
                startedUp = true
                embeddedMqtt.start()
                connectAndSubscribe()
            }
        }
    }

    private fun init(){
        CoroutineScope(SupervisorJob()).launch {
            delay(500) //TODO: Maybe waiting for any attribute is initialized?

            getGroups().forEach {
                it.getAttributes().forEach {
                    it.setListenersOnOtherAttributes()
                }
            }
        }
    }

    //******************************************************************************************************
    //Functions called on user actions

    /**
     * This method saves all attributes, if all attributes are valid.
     * @return if the attributes where saved or not : Boolean
     */
    override fun saveAll(): Boolean {
        return if(isValidForAll()){
            allGroups.forEach{it.getAttributes().forEach{ it.save() }}
            true
        }else{
            false
        }
    }

    /**
     * This method resets all attributes if there is at leased one change
     * @return if the attributes had changes and were reseted or not : Boolean
     */
    override fun resetAll(): Boolean {
        return if(isChangedForAll()){
            allGroups.forEach{it.getAttributes().forEach{ it.reset() }}
            true
        }else{
            false
        }

    }

    /**
     * This method sets the currentLanguage for all attributes
     * @param lang : Locale
     */
    override fun setCurrentLanguageForAll(lang: String){
        currentLanguage.value = lang
        allGroups.forEach{it.getAttributes().forEach{attribute -> attribute.setCurrentLanguage(lang) }}
    }

    override fun validateAll() {
        allGroups.forEach{it.getAttributes().forEach{it.revalidate()}}
    }


    //******************************************************************************************************
    //Setter

    /**
     * This method checks if there is at least one attribute with a change.
     * If yes, changedForAll is set true. If not, changedForAll is set false.
     */
    override fun setChangedForAll(){
        changedForAll.value = allGroups.flatMap{it.getAttributes()}.any(Attribute<*,*,*>::isChanged)
    }

    /**
     * This method checks if there is at least one attribute with a change.
     * If yes, changed is set true. If not, changed is set false.
     */
    override fun setValidForAll(){
        validForAll.value = allGroups.flatMap{it.getAttributes()}.all(Attribute<*,*,*>::isValid)
    }

    override fun setTitle(title: String){
        this.title = title
    }

    //******************************************************************************************************
    //Getter

    override fun getGroups(): List<Group> {
        return allGroups
    }

    override fun getTitle(): String {
        return title
    }

    override fun isChangedForAll() : Boolean{
        return changedForAll.value
    }

    override fun isValidForAll() : Boolean{
        return validForAll.value
    }

    override fun isCurrentLanguageForAll(language : String) : Boolean{
        return currentLanguage.value == language
    }

//    override fun addAttribute(attr: Attribute<*,*,*>) { //TODO
//        allGroups.flatMap{it.attributes}.add(attr)
//        if(getCurrentLanguage() != ""){
//            attr.setCurrentLanguage(getCurrentLanguage())
//        }
//    }

    override fun addGroup(group: Group) {
        allGroups.add(group)
    }

    override fun getCurrentLanguage(): String {
        return currentLanguage.value
    }

    fun getAttributeType(attr: Attribute<*, *, *>): AttributeType {
        return when (attr){
            is DoubleAttribute -> AttributeType.DOUBLE
            is FloatAttribute -> AttributeType.FLOAT
            is IntegerAttribute -> AttributeType.INTEGER
            is LongAttribute -> AttributeType.LONG
            is SelectionAttribute -> AttributeType.SELECTION
            is ShortAttribute -> AttributeType.SHORT
            is StringAttribute -> AttributeType.STRING
            else -> AttributeType.OTHER
        }
    }

    //List with focus requester to change focus in the model
    private var focusRequesters: MutableList<Pair<FocusRequester, Attribute<*,*,*>>> = mutableListOf()
    private var currentFocusIndex = mutableStateOf<Int?>(null)

    override fun getCurrentFocusIndex(): Int? {
        return currentFocusIndex.value
    }

    private fun getCurrentFocusAttributeId(): Int{
        return if(getCurrentFocusIndex()?: Int.MAX_VALUE < focusRequesters.size){
            focusRequesters[getCurrentFocusIndex()!!].second.getId()
        }else{
            -1
        }
    }

    /**
     * Set current focus index if it differs from current focus index and is greater or equals 0
     * TODO: Check if the attr id also can be used?!
     * @param index
     */
    override fun setCurrentFocusIndex(index: Int?) {
        if(index != currentFocusIndex.value && index?:0 >= 0) {
            currentFocusIndex.value = index
            val attr: Attribute<*,*,*>? = getAttributeById(getCurrentFocusAttributeId())
            if(attr != null) {
                sendAll(attr)
            }
            if(currentFocusIndex.value != null  && currentFocusIndex.value!! < focusRequesters.size && currentFocusIndex.value!! >= 0) {
                focusRequesters[currentFocusIndex.value!!].first.requestFocus()
            }
        }
    }

    /**
     * Adds focus requester as a pair with the attribute. Only adds if the pair is not in the list.
     * @param fr: FocusRequester for the attribute
     * @param attr: Attribute that the FR is made for
     * @return Int: index of the pair in the list. Returns -1 if the pair is already in the list
     */
    override fun addFocusRequester(fr: FocusRequester, attr: Attribute<*, *, *>): Int {
        val p = Pair(fr, attr)
        if(p !in focusRequesters) {
            focusRequesters.add(p)
//            if(focusRequesters.size == 1) fr.requestFocus()
            return focusRequesters.size -1
        }
        return -1
    }

    /**
     * This method focuses the next field if there is an already focused field.
     * It sets the current focus to the next greater value. If it is out of bounds then starts from beginning
     */
    override fun focusNext() {
        if(currentFocusIndex.value != null){
            setCurrentFocusIndex((currentFocusIndex.value!! + 1) % focusRequesters.size)
        }else{
            setCurrentFocusIndex(0)
        }
    }

    /**
     * This method focuses the previous field if there is an already focused field.
     * It sets the current focus to the previous value. If it is a negative value, set the highest value
     */
    override fun focusPrevious() {
        if(currentFocusIndex.value != null) {
            setCurrentFocusIndex((currentFocusIndex.value!! + focusRequesters.size - 1) % focusRequesters.size)
        }else{
            setCurrentFocusIndex(focusRequesters.size -1)
        }
    }

    /**
     * Publishing the attribute as a DTOText on the text channel if the attribute is the current selected attribute
     * @param attr: Attribute used for getting the text
     * TODO: Sync with setCurrentFocusIndex for which attribute is selected
     */
    override fun textChanged(attr: Attribute<*,*,*>){
        if(attr.getId() == getCurrentFocusAttributeId()) {
            val dtoText = DTOText(attr.getId(), attr.getValueAsText())
            val string = Json.encodeToString(dtoText)
            mqttConnectorText.publish(message = string, subtopic = "text", onPublished = { println("Sent:" + string) })
        }
    }

    /**
     * Publishing the attribute as DTOAttribute to the attribute channel
     * @param attr: Attribute that has to be published
     */
    override fun attributeChanged(attr: Attribute<*, *, *>) {
        val dtoAttr = DTOAttribute(attr.getId(), attr.getLabel(), getAttributeType(attr), attr.getPossibleSelections())
        val string = Json.encodeToString(dtoAttr)
        mqttConnectorAttribute.publish(message = string, subtopic = "attribute", onPublished = { println("Sent:" + string) })
    }

    /**
     * Publishing the validation result of the attribute as DTOValidation if the attribute is the current selected
     * @param attr: Attribute from which the validation has changed
     */
    override fun validationChanged(attr: Attribute<*, *, *>) {
        if(attr.getId() == getCurrentFocusAttributeId()) {
            val dtoValidation = DTOValidation(
                attr.isRightTrackValid(), attr.isValid(),
                attr.isReadOnly(), attr.getErrorMessages()
            )
            val string = Json.encodeToString(dtoValidation)
            mqttConnectorValidation.publish(
                message = string,
                subtopic = "validation",
                onPublished = { println("sent: " + string) })
        }
    }

    /**
     * Function that sets for an ID, provided in the string the value as text for the value in the string
     * @param string: String as JSON from DTOText
     */
    fun onReceivedText(string: String) {
        val dtotext = Json.decodeFromString<DTOText>(string)
        getAttributeById(dtotext.id)?.setValueAsText(dtotext.text)
    }


    /**
     * Publishing all kinds of changes
     * @param attr: Attribute that the values are used from
     */
    fun sendAll(attr: Attribute<*,*,*>){
        attributeChanged(attr)
        textChanged(attr)
        validationChanged(attr)
    }

    /**
     * Function that handles a command
     * @param string: DTOCommand as JSON String
     */
    fun onReceivedCommand(string: String) {
        val commandDTO = Json.decodeFromString<DTOCommand>(string)

        when(commandDTO.command){
            Command.NEXT -> focusNext()
            Command.PREVIOUS -> focusPrevious()
            Command.REQUEST -> {
                val attr: Attribute<*,*,*>? = getAttributeById(getCurrentFocusAttributeId())
                if(attr != null) {
                    sendAll(attr)
                }
            }
            Command.SAVE -> println("save")
            Command.RESET -> println("next")
        }
    }

    /**
     * Connecting all channels to the server and set handle function for the new messages for each channel
     */
    fun connectAndSubscribe(){
        mqttConnectorText.connectAndSubscribe(subtopic = "text", onNewMessage =
        {
            onReceivedText(it)
            println("Recieved: " + it)
        })

        mqttConnectorAttribute.connectAndSubscribe(subtopic = "attribute", onNewMessage =
        {
        })

        mqttConnectorCommand.connectAndSubscribe(subtopic = "command", onNewMessage =
        {
            onReceivedCommand(it)
            println("Recieved: " + it)
        })

        mqttConnectorValidation.connectAndSubscribe(subtopic = "validation", onNewMessage =
        {
        })
    }


    init{
        init()
        startUp()
    }
}