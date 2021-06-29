package server

import communication.MqttConnector
import io.mockk.clearAllMocks
import io.mockk.mockk
import io.mockk.verify
import model.BaseModel
import model.util.presentationElements.Group
import model.util.ILabel
import model.util.attribute.Attribute
import model.util.attribute.StringAttribute
import model.util.presentationElements.Field
import model.validators.semanticValidators.StringValidator
import org.junit.jupiter.api.*

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CommunicationITest {

    enum class testLabels(val test: String): ILabel{
        TEST("test")
    }

    lateinit var mqttConnectorTextT : MqttConnector
    lateinit var mqttConnectorAttributeT : MqttConnector
    lateinit var mqttConnectorCommandT : MqttConnector
    lateinit var mqttConnectorValidationT : MqttConnector

    var model: BaseModel? = null

    var attribute1 : Attribute<*,*,*>? = null
    var attribute2 : Attribute<*,*,*>? = null
    var group      : Group? = null

    @BeforeEach
    fun setUp(){
        Attribute.resetId()
        clearAllMocks()
        initObjects()
    }

    @BeforeAll
    private fun initMocks(){
        mqttConnectorTextT = mockk(relaxed = true)
        mqttConnectorAttributeT = mockk(relaxed = true)
        mqttConnectorCommandT = mockk(relaxed = true)
        mqttConnectorValidationT = mockk(relaxed = true)
    }

    private fun initObjects(){
        model = null
        model = object: BaseModel(testLabels.TEST){
            override val mqttConnectorText = mqttConnectorTextT
            override val mqttConnectorAttribute = mqttConnectorAttributeT
            override val mqttConnectorCommand = mqttConnectorCommandT
            override val mqttConnectorValidation = mqttConnectorValidationT
        }

        //when
        attribute1 = StringAttribute(model!!, testLabels.TEST, value = "", validators = listOf(StringValidator(2,5)))
        attribute2 = StringAttribute(model!!, testLabels.TEST, value = "")

        group = Group(model!!, "testgroup", Field(attribute1!!), Field(attribute2!!))

        model!!.addFocusRequester(mockk(relaxed = true), attribute1!!)
        model!!.addFocusRequester(mockk(relaxed = true), attribute2!!)
    }


    @Test
    fun testSendAmount(){

        model!!.setCurrentFocusIndex(0)
        clearAllMocks()

        verify(exactly = 0) {
            mqttConnectorValidationT.publish(any(), any(), any(), any())
            mqttConnectorTextT.publish(any(), any(), any(), any())
            mqttConnectorAttributeT.publish(any(), any(), any(), any())
            mqttConnectorCommandT.publish(any(), any(), any(), any())
        }

        attribute1!!.setValueAsText("123")

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
            mqttConnectorValidationT.publish(any(), any(), any(), any())
            mqttConnectorTextT.publish(any(), any(), any(), any())
            mqttConnectorAttributeT.publish(any(), any(), any(), any())
            mqttConnectorCommandT.publish(any(), any(), any(), any())
        }

        model!!.setCurrentFocusIndex(1)

        verify(exactly = 0) {
            mqttConnectorCommandT.publish(any())
        }
        verify(exactly = 1) {
            mqttConnectorAttributeT.publish(any(), any(), any(), any())
            mqttConnectorTextT.publish(any(), any(), any(), any())
            mqttConnectorValidationT.publish(any(), any(), any(), any())
        }
    }


    @Test
    fun testOnReceive(){
        model!!.setCurrentFocusIndex(0)
        clearAllMocks() //This test is only interested on the workflow of the command received. Therefore the mocks are cleared before the test

        val start = "{ \"command\" :"
        val end = " }"
        var mid = ""

        //when
        mid = "\"REQUEST\""
        val command1 = start + mid + end
        model!!.onReceivedCommand(command1)
        //then
        verify(exactly = 1){
            mqttConnectorAttributeT.publish(any(), any(), any(), any())
            mqttConnectorTextT.publish(any(), any(), any(), any())
            mqttConnectorValidationT.publish(any(), any(), any(), any())
        }
    }

    @Test
    fun testOnReceiveOutOfBoundsSelection(){
        model!!.setCurrentFocusIndex(0)
        clearAllMocks() //This test is only interested on the workflow of the command received. Therefore the mocks are cleared before the test

        model!!.setCurrentFocusIndex(5)
        val start = "{ \"command\" :"
        val end = " }"
        var mid = ""

        //when
        mid = "\"REQUEST\""
        val command1 = start + mid + end
        model!!.onReceivedCommand(command1)
        //then
        verify(exactly = 0){
            mqttConnectorAttributeT.publish(any(), any(), any(), any())
            mqttConnectorTextT.publish(any(), any(), any(), any())
            mqttConnectorValidationT.publish(any(), any(), any(), any())
        }
    }

    @Test
    fun testConnectAndSubscribeConnectingAllChannels(){
        clearAllMocks() //Only interested in interactions from connect and subscribe
        //when
        model!!.connectAndSubscribe()
        //then
        verify(exactly = 1) {
            mqttConnectorValidationT.connectAndSubscribe(any(), any(), any(), any())
            mqttConnectorTextT.connectAndSubscribe(any(), any(), any(), any())
            mqttConnectorCommandT.connectAndSubscribe(any(), any(), any(), any())
            mqttConnectorAttributeT.connectAndSubscribe(any(), any(), any(), any())
        }

    }
}