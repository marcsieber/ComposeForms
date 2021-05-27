package communication

import com.hivemq.embedded.EmbeddedHiveMQ
import com.hivemq.embedded.EmbeddedHiveMQBuilder

fun runEmbeddedMQServer() {

    val embeddedHiveMQBuilder : EmbeddedHiveMQBuilder = EmbeddedHiveMQ.builder()
//            .withConfigurationFolder(Path.of("/resources/"))
//            .withDataFolder(Path.of("/path/to/embedded-data-folder"))
//            .withExtensionsFolder(Path.of("/path/to/embedded-extensions-folder"));
    val hiveMQ = embeddedHiveMQBuilder.build()
    try {
        hiveMQ.start().join()
    } catch (ex : Exception) {
        ex.printStackTrace()
    }
}