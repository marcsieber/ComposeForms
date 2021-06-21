package communication

import io.mockk.clearAllMocks
import io.mockk.clearMocks
import io.mockk.mockk
import io.mockk.verify
import model.BaseFormModel
import model.util.ILabel
import model.util.attribute.Attribute
import model.util.attribute.StringAttribute
import model.validators.semanticValidators.StringValidator
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommunicationITest {

    enum class testLabels(test: String): ILabel{
        test("test"),
        label1("L1"),
        label2("L2")
    }

    lateinit var mqttConnectorTextT : MqttConnector
    lateinit var mqttConnectorAttributeT : MqttConnector
    lateinit var mqttConnectorCommandT : MqttConnector
    lateinit var mqttConnectorValidationT : MqttConnector
    lateinit var model: BaseFormModel

    lateinit var attribute1 : Attribute<*,*,*>
    lateinit var attribute2 : Attribute<*,*,*>

    @BeforeAll
    fun setUp(){
        println("Setup all")
        mqttConnectorTextT = mockk<MqttConnector>(relaxed = true)
        mqttConnectorAttributeT = mockk<MqttConnector>(relaxed = true)
        mqttConnectorCommandT = mockk<MqttConnector>(relaxed = true)
        mqttConnectorValidationT = mockk<MqttConnector>(relaxed = true)

        model = object: BaseFormModel(){
            override val mqttConnectorText = mqttConnectorTextT
            override val mqttConnectorAttribute = mqttConnectorAttributeT
            override val mqttConnectorCommand = mqttConnectorCommandT
            override val mqttConnectorValidation = mqttConnectorValidationT

            override fun getPossibleLanguages(): List<String> {
                return emptyList()
            }
        }

        attribute1 = StringAttribute(model, testLabels.test, value = "", validators = listOf(StringValidator(2,5)))
        attribute2 = StringAttribute(model, testLabels.test, value = "", required = true)
    }

    @BeforeEach
    fun setupEach(){
        // Clearing all mocks - so initial calls e.g. creating Attribute that calls a publish for validation will be reset.
        // Has to be done to not reinitialize everything and not be dependent on ordering of the tests
        clearAllMocks()
    }


    @Test
    fun testSendAmount(){
        verify(exactly = 0) {
            mqttConnectorValidationT.publish(any(), any(), any(), any()) // One Validation will be done on initialization of attribute
            mqttConnectorTextT.publish(any(), any(), any(), any())
            mqttConnectorAttributeT.publish(any(), any(), any(), any())
            mqttConnectorCommandT.publish(any(), any(), any(), any())
        }

        attribute1.setValueAsText("123")

        verify(exactly = 0) {
            mqttConnectorCommandT.publish(any(), any(), any(), any())
            mqttConnectorAttributeT.publish(any(), any(), any(), any())
        }
        verify(exactly = 1) {
            mqttConnectorTextT.publish(any(), any(), any(), any())
        }
        verify(exactly = 2) {
            mqttConnectorValidationT.publish(any(), any(), any(), any())
        }
    }

    @Test
    fun testChangeSelection(){
        verify(exactly = 0) {
            mqttConnectorValidationT.publish(any(), any(), any(), any()) // One Validation will be done on initialization of attribute
            mqttConnectorTextT.publish(any(), any(), any(), any())
            mqttConnectorAttributeT.publish(any(), any(), any(), any())
            mqttConnectorCommandT.publish(any(), any(), any(), any())
        }

        model.setCurrentFocusIndex(1)

        verify(exactly = 0) {
            mqttConnectorCommandT.publish(any())
        }
        verify(exactly = 1) {
            mqttConnectorAttributeT.publish(any(), any(), any(), any())
            mqttConnectorTextT.publish(any(), any(), any(), any())
            mqttConnectorValidationT.publish(any(), any(), any(), any())
        }
    }


//    @Test
//    fun testOnReceive(){
//
//    }
}