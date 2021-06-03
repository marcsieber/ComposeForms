package com.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import communication.Command
import communication.DTOCommand
import communication.DTOText
import communication.MqttConnector
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object Model {

    //broker.hivemq.com
    val mqttBroker    = "192.168.0.94" // "broker.hivemq.com"
    val mainTopic     = "/fhnwforms/"
    val mqttConnectorText = MqttConnector(mqttBroker, mainTopic)
    val mqttConnectorCommand = MqttConnector(mqttBroker, mainTopic)

    var id : Long = 0

    var text by mutableStateOf("")
    var isValid by mutableStateOf(true)
    var isOnRightTrack by mutableStateOf(true)

    var isConnected: Boolean = false

    fun connectAndSubscribe(){
        mqttConnectorText.connectAndSubscribe(subtopic = "text", onNewMessage = {
            val dtoText = Json.decodeFromString<DTOText>(it)
            id = dtoText.id
            text = dtoText.text
            isOnRightTrack = dtoText.onRightTrack
            isValid = dtoText.isValid
        }, onConnected = {isConnected = true})

        mqttConnectorCommand.connectAndSubscribe(subtopic = "command", onNewMessage = {})
    }

    fun publish(){
        val string = Json.encodeToString(DTOText(0, text))
        mqttConnectorText.publish(message = string, subtopic = "text", onPublished = { print("message sent") })
    }

    fun sendCommand(command : Command){
        val string = Json.encodeToString(DTOCommand(command))
        mqttConnectorCommand.publish(message = string, subtopic = "command", onPublished = { print("command sent") })
    }
}