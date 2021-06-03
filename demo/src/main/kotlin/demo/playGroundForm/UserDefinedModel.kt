package demo.playGroundForm

import androidx.compose.runtime.mutableStateOf
import communication.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import model.BaseFormModel
import model.convertables.CustomConvertable
import model.convertables.ReplacementPair
import model.util.attribute.*
import model.validators.semanticValidators.*
import java.time.LocalTime
import kotlin.concurrent.thread

class UserDefinedModel : BaseFormModel(){

    val mqttBroker    = "localhost"
    val mainTopic     = "/fhnwforms/"
    val mqttConnectorText = MqttConnector(mqttBroker, mainTopic)
    val mqttConnectorCommand = MqttConnector(mqttBroker, mainTopic)

    private val modelScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)


    init {
        setTitle("Demo Title")

        startUp()
    }

    fun startUp(){
        modelScope.launch {
            runEmbeddedMQServer()
            connectAndSubscribe()
        }
    }

    fun onReceivedText(string: String) {
        val dtotext = Json.decodeFromString<DTOText>(string)
        getAttributeById(dtotext.id)?.setValueAsText(dtotext.text)
    }

    fun onReceivedCommand(string: String) {
        val commandDTO = Json.decodeFromString<DTOCommand>(string)

        when(commandDTO.command){
            Command.NEXT -> println("next")
            Command.PREVIOUS -> println("previous")
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

        mqttConnectorCommand.connectAndSubscribe(subtopic = "command", onNewMessage =
        {
            onReceivedCommand(it)
            println("Recieved: " + it)
        })
    }


    override fun attributeChanged(attr: Attribute<*, *, *>) {
        val dtoText = DTOText(attr.getId(), attr.getValueAsText(), attr.getLabel(), attr.isRightTrackValid(), attr.isValid(),
            getAttributeType(attr), attr.isReadOnly(), attr.getErrorMessages())
        val string = Json.encodeToString(dtoText)
        mqttConnectorText.publish(message = string, subtopic = "text", onPublished = { print("message sent") })
    }

    override fun getPossibleLanguages(): List<String> {
       return Labels.getLanguages()
    }

    val strValidator = StringValidator(5, 10)
    val customValidator = CustomValidator<String>({value -> value!!.length in 3..5}, validationMessage = "Message")


    val s = StringAttribute(
        model = this,
        value = "",
        label = Labels.stringLabel,
        validators = listOf(RegexValidator<String>("^\\w+\\W\\w+\$", validationMessage = "Muss genau zwei Wörter sein"))
    )

    val d1 = DoubleAttribute(
        model = this,
        value = 0.0,
        label = Labels.convertOnUnfocussing,
        convertables = listOf(
            CustomConvertable(listOf(
                ReplacementPair("eins", "1"),
                ReplacementPair("zwei", "2")
            )),
            CustomConvertable(listOf(
                ReplacementPair("(\\d*)(,)(\\d*)", "$1.$3")
            ), convertUserView = true)
        )
    )

    val d2 = DoubleAttribute(
        model = this,
        value = 0.0,
        label = Labels.convertImmediately,
        convertables = listOf(
            CustomConvertable(listOf(
                ReplacementPair("eins", "1"),
                ReplacementPair("zwei", "2")
            ), true, true),
            CustomConvertable(listOf(
                ReplacementPair("(\\d*)(,)(\\d*)", "$1.$3")
            ), convertUserView = true, true)
        )
    )

    val d3 = DoubleAttribute(
        model = this,
        value = 0.0,
        label = Labels.doNotConvert,
        convertables = listOf(
            CustomConvertable(listOf(
                ReplacementPair("eins", "1"),
                ReplacementPair("zwei", "2")
            ), false),
            CustomConvertable(listOf(
                ReplacementPair("(\\d*)(,)(\\d*)", "$1.$3")
            ), convertUserView = false)
        )
    )


    val time = StringAttribute(
        model = this,
        label = Labels.timeLabel,
        convertables = listOf(CustomConvertable(listOf(
            ReplacementPair("now", LocalTime.now().toString())
            ), convertUserView = true)
        )
    )

    //String
    val strVal = StringValidator(5)
    val stringValue = StringAttribute(
        model = this,
        value = "Ich bin Text",
        readOnly = false,
        required = true,
        validators = listOf(strVal),
        label = Labels.stringLabel,
        onChangeListeners = listOf {
            if (it.equals("Ich")) {
                customValidator.overrideCustomValidator(validationMessage = "Neue Message")
            }
        }
    )

    val string = StringAttribute(
        model = this,
        value = "1234",
        validators = listOf(StringValidator(minLength = 3, maxLength = 5)),
        label = Labels.stringLabel
    )

    //Numbers
    val intValue1    = IntegerAttribute(
        model = this,
        label = Labels.intLabel,
        readOnly = false,
        validators = listOf(NumberValidator(0, 10, 2, 4, true, "LowerBound ist bei 0, upperBound bei 10, es sind nur 2er-Schritte zugelassen")),
        onChangeListeners = listOf{
            if(it == 4){
                strVal.overrideStringValidator(8)
            }

        }
    )

    val intValue2    = IntegerAttribute(model = this,
        value = 15,
        required = false,
        readOnly = true,
        label = Labels.intLabel
    )

    val shortValue = ShortAttribute(model = this,
        value = 9,
        label = Labels.shortLabel,
        readOnly = false,
        validators = listOf(NumberValidator(0,100,2, 9))
        )

    val longValue = LongAttribute(model = this,
        value = 9,
        label = Labels.longLabel,
        readOnly = false,
        validators = listOf(NumberValidator(0, 100, 2))
        )

    val floatValue = FloatAttribute(
        model = this,
        value = 9.5f,
        label = Labels.floatLabel,
        required = true,
        readOnly = false,
        validators = listOf(NumberValidator(0f, 100f, 3f, 9.5f, true)) ,
        onChangeListeners = listOf { longValue.setReadOnly(it == 12.5f) }
    )

    val doubleValue = DoubleAttribute(
        model = this,
        value = 7.7,
        label = Labels.doubleLabel,
        required = true,
        readOnly = false,
        validators = listOf(FloatingPointValidator(2, "Nur 2 Nachkommastellen")),
        onChangeListeners = listOf {
            if (it == 8.85) {
                selectionValue.addANewPossibleSelection("Neues Element")
            }
        }
    )

    val list = setOf("Hallo", "Louisa", "Steve", "Eintrag")
    val selectionValue = SelectionAttribute(
        model = this,
        value = setOf(),
        possibleSelections = list,
        label = Labels.selectionLabel,
        validators = listOf(SelectionValidator(0,2))
    )

    init{
        setCurrentLanguageForAll("English")
    }

    val dateValue   = mutableStateOf("01/05/2020")
    val booleanValue = mutableStateOf(true)
    val radioButtonValue = mutableStateOf(true)

    //Selection
    val dropDownItems  = setOf<String>("1","2","3","4")
    val dropDownOpen    = mutableStateOf(false)
    val dropDownSelIndex = mutableStateOf(0)


    init{
        thread {
            Thread.sleep(3000)
//            this.labels = label2
            this.setCurrentLanguageForAll("Deutsch")
            strValidator.overrideStringValidator(15,20,"Length must be between 15 and 20 characters")
        }
    }


//    Blalbla(
//    Group(name = "HalloGroup", ([attribut, 2],[attr, 2],2),(6)),
//    Group(name = "NextGroup", (2,4))
//    )

}