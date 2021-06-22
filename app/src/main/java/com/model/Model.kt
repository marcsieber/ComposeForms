package com.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import communication.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object Model {

    //broker.hivemq.com
//    val mqttBroker    = "broker.hivemq.com"
//    val mqttBroker    = "192.168.0.94"
    val mqttBroker    = "192.168.178.55" //ifconfig en0
    val mainTopic     = "/fhnwforms/"
    val mqttConnectorText = MqttConnector(mqttBroker, mainTopic)
    val mqttConnectorCommand = MqttConnector(mqttBroker, mainTopic)
    val mqttConnectorValidation = MqttConnector(mqttBroker, mainTopic)
    val mqttConnectorAttribute = MqttConnector(mqttBroker, mainTopic)

    var id : Int = 0

    var text by mutableStateOf("")
    var label by mutableStateOf("")
    var possibleSelections by mutableStateOf(emptySet<String>())
    var isValid by mutableStateOf(true)
    var isOnRightTrack by mutableStateOf(true)
    var type by mutableStateOf(AttributeType.OTHER)
    var errorMessages = mutableStateOf<List<String>>(emptyList())

    var isConnected: Boolean = false

    fun connectAndSubscribe(){
        mqttConnectorAttribute.connectAndSubscribe(subtopic = "attribute", onNewMessage = {
            val dtoText = Json.decodeFromString<DTOAttribute>(it)
            id = dtoText.id
            label = dtoText.label
            type = dtoText.attrType
            possibleSelections = dtoText.possibleSelections
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