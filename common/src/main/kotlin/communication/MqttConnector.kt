/*
 *
 *   ========================LICENSE_START=================================
 *   Compose Forms
 *   %%
 *   Copyright (C) 2021 FHNW Technik
 *   %%
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *   =========================LICENSE_END==================================
 *
 */

package communication

import com.hivemq.client.mqtt.datatypes.MqttQos
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
import java.nio.charset.StandardCharsets
import java.util.*

/**
 * TODO: Write Comment
 * ACHTUNG: Das ist nur eine erste Konfiguration eines Mqtt-Brokers.
 *
 * Dient vor allem dazu mit den verschiedenen Parametern experimentieren zu können
 *
 * siehe Doku:
 * https://hivemq.github.io/hivemq-mqtt-client/
 * https://github.com/hivemq/hivemq-mqtt-client
 *
 * Ein generischer Mqtt-Client (gut um Messages zu kontrollieren)
 * http://www.hivemq.com/demos/websocket-client/
 *
 * @author Louisa Reinger
 * @author Steve Vogel
 */

class MqttConnector (val mqttBroker: String, val maintopic: String,
                     val qos: MqttQos = MqttQos.EXACTLY_ONCE){

    private val client = Mqtt5Client.builder()
        .serverHost(mqttBroker)
        .serverPort(1883)
        .identifier(UUID.randomUUID().toString())
        .buildAsync()

    /**
     * Connecting to the server.
     *
     * @param subtopic: default #, listening to all channels
     * @param onNewMessage: function that is invoked with the ned message
     * @param onConnectionFailed: function that is invoked when the connection could not be etablished
     * @param onConnected: function that is invoked when the connection has etablished
     */
    fun connectAndSubscribe(subtopic: String = "#",
                            onNewMessage: (String) -> Unit = {},
                            onConnectionFailed: () -> Unit = {},
                            onConnected: () -> Unit = {}) {
        client.connectWith()
            .cleanStart(true)
            .keepAlive(30)
            .send()
            .whenComplete { _, throwable ->
                if (throwable != null) {
                    println("connection failed")
                    throwable.printStackTrace()
                    onConnectionFailed.invoke()
                } else { //erst wenn die Connection aufgebaut ist, kann subscribed werden
                    println("Connected")
                    subscribe(subtopic = subtopic, onNewMessage = onNewMessage)
                    onConnected()
                }
            }
    }

    /**
     * Subscribing to one channel
     * @param subtopic: subtopic that is subscribed to
     * @param onNewMessage: function that is invoked when a new message arrived
     */
    fun subscribe(subtopic: String = "#", onNewMessage: (String) -> Unit){
        client.subscribeWith()
            .topicFilter(maintopic + subtopic)
            .qos(qos)
            .noLocal(true)
            .callback {
                onNewMessage.invoke(it.payloadAsString())
            }
            .send()
    }

    /**
     * Publishing / Sending a message to a subtopic.
     * @param message: String that will be sent
     * @param subtopic: Subtopic / channel to which it will be sent
     * @param onPublished: function that is invoked after sending was successful
     * @param onError: function that is invoked when an error occurs during sending
     */
    fun publish(message: String, subtopic: String = "", onPublished: () -> Unit = {}, onError: () -> Unit = {}) {
        client.publishWith()
            .topic(maintopic + subtopic)
            .payload(message.asPayload())
            .qos(qos)
            .retain(false)
            .messageExpiryInterval(120)
            .send()
            .whenComplete{_, throwable ->
                if(throwable != null){
                    onError.invoke()
                }
                else {
                    onPublished.invoke()
                }
            }
    }

    /**
     * disconnect from the server
     */
    fun disconnect() {
        client.disconnectWith()
            .sessionExpiryInterval(0)
            .send()
    }
}

//extension functions to transform strings
private fun String.asPayload() : ByteArray = toByteArray(StandardCharsets.UTF_8)
private fun Mqtt5Publish.payloadAsString() : String = String(payloadAsBytes, StandardCharsets.UTF_8)