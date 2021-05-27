package com.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import communication.MqttConnector

object Model {

    //broker.hivemq.com
    val mqttBroker    = "192.168.0.94" // "broker.hivemq.com"
    val mainTopic     = "/fhnwforms/"
    val mqttConnector = MqttConnector(mqttBroker, mainTopic)

    var text by mutableStateOf("")
    var id : Long = 0

    var isConnected: Boolean = false

    fun connectAndSubscribe(){
        mqttConnector.connectAndSubscribe(onNewMessage = { text = it}, onConntected = {isConnected = true})
    }

    fun publish(){
        mqttConnector.publish(text, onPublished = { print("message sent") })
    }

}