package com.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import communication.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object Model {

//    var mqttBroker    = "broker.hivemq.com"
//    var mqttBroker    = "192.168.0.94" oftr
    var mqttBroker    = "192.168.178.55" //ifconfig en0 - witt
//    var mqttBroker    = "192.168.178.26" // inz
    val mainTopic     = "/fhnwforms/"
    lateinit var mqttConnectorText : MqttConnector
    lateinit var mqttConnectorCommand : MqttConnector
    lateinit var mqttConnectorValidation : MqttConnector
    lateinit var mqttConnectorAttribute : MqttConnector

    var id : Int = 0

    var text by mutableStateOf("")
    var label by mutableStateOf("")
    var possibleSelections by mutableStateOf(emptySet<String>())
    var isValid by mutableStateOf(true)
    var isOnRightTrack by mutableStateOf(true)
    var type by mutableStateOf(AttributeType.OTHER)
    var errorMessages = mutableStateOf<List<String>>(emptyList())

    var isConnected: Boolean = false

    var calcModel = returnCalculatorModelWithCorrectType()

    private fun returnCalculatorModelWithCorrectType(): CalculatorModel<*>? {
        println("type: " + type)
        return when(type){
            AttributeType.DOUBLE    -> CalculatorModel<Double>(this, AttributeType.DOUBLE)
            AttributeType.FLOAT     -> CalculatorModel<Float>(this, AttributeType.FLOAT)
            AttributeType.INTEGER   -> CalculatorModel<Int>(this, AttributeType.INTEGER)
            AttributeType.LONG      -> CalculatorModel<Long>(this, AttributeType.LONG)
            AttributeType.SHORT     -> CalculatorModel<Short>(this, AttributeType.SHORT)
            else                    -> null
        }
    }

    fun connectAndSubscribe(){
        mqttConnectorText = MqttConnector(mqttBroker, mainTopic)
        mqttConnectorAttribute = MqttConnector(mqttBroker, mainTopic)
        mqttConnectorValidation = MqttConnector(mqttBroker, mainTopic)
        mqttConnectorCommand = MqttConnector(mqttBroker, mainTopic)

        mqttConnectorAttribute.connectAndSubscribe(subtopic = "attribute", onNewMessage = {
            val dtoText = Json.decodeFromString<DTOAttribute>(it)
            id = dtoText.id
            label = dtoText.label
            type = dtoText.attrType
            possibleSelections = dtoText.possibleSelections
            calcModel = returnCalculatorModelWithCorrectType()
        }, onConnected = {
            isConnected = true
            val dtoCommand = DTOCommand(Command.REQUEST)
            val string = Json.encodeToString(dtoCommand)
            mqttConnectorText.publish(subtopic = "command", message = string)
        })

        mqttConnectorText.connectAndSubscribe(subtopic = "text", onNewMessage = {
            val dtoText = Json.decodeFromString<DTOText>(it)
            id = dtoText.id
            text = dtoText.text
            calcModel?.reset()
        })

        mqttConnectorCommand.connectAndSubscribe(subtopic = "command", onNewMessage = {})

        mqttConnectorValidation.connectAndSubscribe(subtopic = "validation", onNewMessage = {
            val dtoValidation = Json.decodeFromString<DTOValidation>(it)
            isOnRightTrack = dtoValidation.onRightTrack
            isValid = dtoValidation.isValid
            errorMessages.value = dtoValidation.errorMessages
        }, onConnected = {isConnected = true})
    }

    fun publish(){
        val string = Json.encodeToString(DTOText(id, text))
        mqttConnectorText.publish(message = string, subtopic = "text", onPublished = { println("message sent") })
    }

    fun sendCommand(command : Command){
        val string = Json.encodeToString(DTOCommand(command))
        mqttConnectorCommand.publish(message = string, subtopic = "command", onPublished = { println("command sent") })
    }
}