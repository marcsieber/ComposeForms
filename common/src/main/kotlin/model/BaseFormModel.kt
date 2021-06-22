package model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import communication.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.util.Group
import model.util.attribute.*
import java.util.*

abstract class BaseFormModel : FormModel {

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
     * This method undoes all attributes,
     * if there is at leased one change
     * @return if the attributes had changes and where undone or not : Boolean
     */
    override fun undoAll(): Boolean {
        return if(isChangedForAll()){
            allGroups.forEach{it.getAttributes().forEach{ it.undo() }}
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

    private var focusRequesters: MutableList<Pair<FocusRequester, Attribute<*,*,*>>> = mutableListOf()
    private var currentFocusIndex = mutableStateOf(0)

    override fun getCurrentFocusIndex(): Int {
        return currentFocusIndex.value
    }

    override fun setCurrentFocusIndex(index: Int) {
        if(index != currentFocusIndex.value) {
            currentFocusIndex.value = index
            val attr: Attribute<*,*,*>? = getAttributeById(currentFocusIndex.value)
            if(attr != null) {
                sendAll(attr)
            }
            if(currentFocusIndex.value < focusRequesters.size) {
                focusRequesters[currentFocusIndex.value].first.requestFocus()
            }
        }
    }

    override fun addFocusRequester(fr: FocusRequester, attr: Attribute<*, *, *>): Int {
        val p = Pair(fr, attr)
        if(p !in focusRequesters) {
            focusRequesters.add(p)
//            if(focusRequesters.size == 1) fr.requestFocus()
            return focusRequesters.size -1
        }

        return -1
    }

    override fun focusNext() {
        setCurrentFocusIndex((currentFocusIndex.value + 1) % focusRequesters.size)
    }

    override fun focusPrevious() {
        setCurrentFocusIndex((currentFocusIndex.value + focusRequesters.size - 1) % focusRequesters.size)
    }

    override fun textChanged(attr: Attribute<*,*,*>){
        if(attr.getId() == getCurrentFocusIndex()) {
            val dtoText = DTOText(attr.getId(), attr.getValueAsText())
            val string = Json.encodeToString(dtoText)
            mqttConnectorText.publish(message = string, subtopic = "text", onPublished = { println("Sent:" + string) })
        }
    }

    override fun attributeChanged(attr: Attribute<*, *, *>) {
        val dtoAttr = DTOAttribute(attr.getId(), attr.getLabel(), getAttributeType(attr), attr.getPossibleSelections())
        val string = Json.encodeToString(dtoAttr)
        mqttConnectorAttribute.publish(message = string, subtopic = "attribute", onPublished = { println("Sent:" + string) })
    }

    override fun validationChanged(attr: Attribute<*, *, *>) {
        if(attr.getId() == getCurrentFocusIndex()) {
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


    fun onReceivedText(string: String) {
        val dtotext = Json.decodeFromString<DTOText>(string)
        getAttributeById(dtotext.id)?.setValueAsText(dtotext.text)
    }


    fun sendAll(attr: Attribute<*,*,*>){
        attributeChanged(attr)
        textChanged(attr)
        validationChanged(attr)
    }

    fun onReceivedCommand(string: String) {
        val commandDTO = Json.decodeFromString<DTOCommand>(string)

        when(commandDTO.command){
            Command.NEXT -> focusNext()
            Command.PREVIOUS -> focusPrevious()
            Command.REQUEST -> {
                val attr: Attribute<*,*,*>? = getAttributeById(getCurrentFocusIndex())
                if(attr != null) {
                    sendAll(attr)
                }
            }
            Command.SAVE -> println("save")
            Command.UNDO -> println("next")
        }
    }

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
}