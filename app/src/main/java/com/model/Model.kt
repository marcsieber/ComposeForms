package com.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import communication.DTOText
import communication.MqttConnector
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

object Model {

    //broker.hivemq.com
    val mqttBroker    = "192.168.0.94" // "broker.hivemq.com"
    val mainTopic     = "/fhnwforms/"
    val mqttConnectorText = MqttConnector(mqttBroker, mainTopic)

    var text by mutableStateOf("")
    var id : Long = 0

    var isConnected: Boolean = false

    fun connectAndSubscribe(){
        mqttConnectorText.connectAndSubscribe(subtopic = "text", onNewMessage = {
             text = Json.decodeFromString<DTOText>(it).text
        }, onConnected = {isConnected = true})
    }

    fun publish(){
        mqttConnectorText.publish(message = DTOText(0, text), subtopic = "text", onPublished = { print("message sent") })
    }

}