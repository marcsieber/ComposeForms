package server

import com.hivemq.embedded.EmbeddedHiveMQ
import com.hivemq.embedded.EmbeddedHiveMQBuilder


object embeddedMqtt {

    private val hiveMQ: EmbeddedHiveMQ?

    private var started = false

    init{
        val embeddedHiveMQBuilder : EmbeddedHiveMQBuilder = EmbeddedHiveMQ.builder()
//            .withConfigurationFolder(Path.of("/resources/"))
//            .withDataFolder(Path.of("/path/to/embedded-data-folder"))
//            .withExtensionsFolder(Path.of("/path/to/embedded-extensions-folder"));
        hiveMQ = embeddedHiveMQBuilder.build()
    }

    fun start(){
        try {
            if(!started) {
                started = true
                hiveMQ?.start()?.join()
            }
        } catch (ex : Exception) {
            ex.printStackTrace()
        }
    }

}
