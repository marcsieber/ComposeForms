package model.util.attribute

import model.BaseFormModel
import model.util.Labels

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

abstract class AttributeTest<T : Any> {

    var model = object: BaseFormModel() {
        override fun getPossibleLanguages(): List<String> {
            return emptyList()
        }
    }

    lateinit var validValue1Uneven : T
    lateinit var validValue2 : T
    lateinit var validValue3 : T
    lateinit var validValue4 : T
    lateinit var validValue1AsText : String
    lateinit var validValue2AsText : String
    lateinit var validValue3AsText : String
    lateinit var validValue4AsText : String
    lateinit var notValidValueAsText : String

    lateinit var attribute : Attribute<*,*,*>

    protected abstract fun provideAttribute(model: BaseFormModel, value: T?) : Attribute<*, Any, *>

    @BeforeEach
    fun setUp(){
        //given
        attribute = provideAttribute(model, validValue1Uneven)
    }

    @Test
    fun testSave() {
        //when
        attribute.setValueAsText(validValue2AsText)
        attribute.save()

        //then
        assertEquals(     validValue2,          attribute.getValue())
        assertEquals(     validValue2,          attribute.getSavedValue())
        assertEquals(   validValue2AsText,    attribute.getValueAsText())
        assertFalse(attribute.isChanged())


        if(attribute !is StringAttribute) { //type string is always correct, if no validator is used

            //when
            attribute.setValueAsText(notValidValueAsText)
            attribute.save()

            //then
            assertEquals(notValidValueAsText, attribute.getValueAsText())
            assertEquals(validValue2, attribute.getValue())
            assertEquals(validValue2, attribute.getSavedValue())
            assertFalse(attribute.isChanged())

        }

        //when
        attribute.setValueAsText(validValue3AsText)

        //then
        assertTrue(attribute.isChanged())
    }

    @Test
    fun testUndo() {
        //when
        attribute.setValueAsText(validValue2AsText)
        attribute.undo()

        //then
        assertEquals(validValue1Uneven,         attribute.getValue())
        assertEquals(validValue1Uneven,         attribute.getSavedValue())
        assertEquals(validValue1Uneven.toString(), attribute.getValueAsText())
        assertFalse(attribute.isChanged())

        //when
        attribute.setValueAsText(validValue2AsText)
        attribute.save()
        attribute.setValueAsText(validValue3AsText)
        attribute.setValueAsText(validValue4AsText)
        attribute.undo()

        //then
        assertEquals(validValue2,         attribute.getValue())
        assertEquals(validValue2,         attribute.getSavedValue())
        assertEquals(validValue2.toString(), attribute.getValueAsText())
        assertFalse(attribute.isChanged())
    }

    @Test
    fun testSetCurrentLanguage() {
        //when
        attribute.setCurrentLanguage("test")

        //then
        assertEquals(Labels.TEST.test, attribute.getLabel())
        assertTrue(attribute.isCurrentLanguage("test"))

        //when
        attribute.setCurrentLanguage("eng")

        //then
        assertEquals(Labels.TEST.eng, attribute.getLabel())
        assertTrue(attribute.isCurrentLanguage("eng"))
    }

    @Test
    fun testSetValueAsText() {
        //when
        attribute.setValueAsText(validValue2AsText)

        //then
        assertEquals(validValue2AsText, attribute.getValueAsText())
        assertEquals(validValue2,attribute.getValue())

        if(attribute !is StringAttribute) { //type string is always correct
            //when
            attribute.setValueAsText(notValidValueAsText)

            //then
            assertFalse(attribute.isValid())
            assertEquals("This is not the correct input type",    attribute.getErrorMessages()[0])
            assertEquals(validValue2,              attribute.getValue())
            assertEquals(notValidValueAsText,   attribute.getValueAsText())
        }
    }
    @Test
    fun testSetRequired() {
        //when
        attribute.setRequired(true)

        //then
        assertTrue(attribute.isRequired())

        //when
        attribute.setRequired(false)

        //then
        assertFalse(attribute.isRequired())
    }

//    @Test
//    fun testRequiredValidator() {
//
//        if(attribute is DoubleAttribute){
//
//            //given
//            var reqValidator = RequiredValidator<Double>(true)
//            (attribute as DoubleAttribute).addValidator(reqValidator)
//
//            //when
//            attribute.setValueAsText("")
//
//            //then
//            assertTrue(attribute.isRequired())
//            assertFalse(attribute.isValid())
//
//            //when
//            reqValidator.overrideRequiredValidator(false)
//
//            //then
//            assertFalse(attribute.isRequired())
//            assertTrue(attribute.isValid())
//
//        }
//        if(attribute is FloatAttribute){
//
//            //given
//            var reqValidator = RequiredValidator<Float>(true)
//            (attribute as FloatAttribute).addValidator(reqValidator)
//
//            //when
//            attribute.setValueAsText("")
//
//            //then
//            assertTrue(attribute.isRequired())
//            assertFalse(attribute.isValid())
//
//            //when
//            reqValidator.overrideRequiredValidator(false)
//
//            //then
//            assertFalse(attribute.isRequired())
//            assertTrue(attribute.isValid())
//
//
//        }
//        if(attribute is IntegerAttribute) {
//
//            //given
//            var reqValidator = RequiredValidator<Int>(true)
//            (attribute as IntegerAttribute).addValidator(reqValidator)
//
//            //when
//            attribute.setValueAsText("")
//
//            //then
//            assertTrue(attribute.isRequired())
//            assertFalse(attribute.isValid())
//
//            //when
//            reqValidator.overrideRequiredValidator(false)
//
//            //then
//            assertFalse(attribute.isRequired())
//            assertTrue(attribute.isValid())
//        }
//        if(attribute is LongAttribute){
//
//            //given
//            var reqValidator = RequiredValidator<Long>(true)
//            (attribute as LongAttribute).addValidator(reqValidator)
//
//            //when
//            attribute.setValueAsText("")
//
//            //then
//            assertTrue(attribute.isRequired())
//            assertFalse(attribute.isValid())
//
//            //when
//            reqValidator.overrideRequiredValidator(false)
//
//            //then
//            assertFalse(attribute.isRequired())
//            assertTrue(attribute.isValid())
//        }
//        if(attribute is ShortAttribute){
//
//            //given
//            var reqValidator = RequiredValidator<Short>(true)
//            (attribute as ShortAttribute).addValidator(reqValidator)
//
//            //when
//            attribute.setValueAsText("")
//
//            //then
//            assertTrue(attribute.isRequired())
//            assertFalse(attribute.isValid())
//
//            //when
//            reqValidator.overrideRequiredValidator(false)
//
//            //then
//            assertFalse(attribute.isRequired())
//            assertTrue(attribute.isValid())
//        }
//        if(attribute is StringAttribute){
//
//            //given
//            var reqValidator = RequiredValidator<String>(true)
//            (attribute as StringAttribute).addValidator(reqValidator)
//
//            //when
//            attribute.setValueAsText("")
//
//            //then
//            assertTrue(attribute.isRequired())
//            assertFalse(attribute.isValid())
//
//            //when
//            reqValidator.overrideRequiredValidator(false)
//
//            //then
//            assertFalse(attribute.isRequired())
//            assertTrue(attribute.isValid())
//        }
//    }

    @Test
    fun testSetReadOnly() {
        //given
        val readOnly = true
        val notReadOnly = false

        //when
        attribute.setReadOnly(readOnly)

        //then
        assertEquals(readOnly, attribute.isReadOnly())

        //when
        attribute.setReadOnly(notReadOnly)

        //then
        assertEquals(notReadOnly, attribute.isReadOnly())
    }

    @Test
    fun testSetValue() {
        //then
        assertEquals(validValue1Uneven, attribute.getValue())

        //when
        attribute.setValueAsText(validValue2AsText)

        //then
        assertEquals(validValue2, attribute.getValue())
    }

    @Test
    fun testSetValidationMessage() {
        //when
        attribute.setValueAsText(validValue2AsText)

        //then
        assertEquals(0, attribute.getErrorMessages().size)

        if(attribute !is StringAttribute) { //type string is always correct

            //when
            attribute.setValueAsText(notValidValueAsText)

            //then
            assertEquals("This is not the correct input type", attribute.getErrorMessages()[0])
        }
    }

    @Test
    fun testGetValue() {
        //then
        assertEquals(validValue1Uneven, attribute.getValue())
    }

    @Test
    fun testGetSavedValue() {
        //when
        attribute.setValueAsText(validValue2AsText)
        attribute.save()

        //then
        assertEquals(validValue2,attribute.getSavedValue())

        //when
        attribute.setValueAsText(validValue3AsText)

        //then
        assertEquals(validValue2,attribute.getSavedValue())
    }

    @Test
    fun testGetValueAsText() {
        //then
        assertEquals(validValue1Uneven.toString(), attribute.getValueAsText())
    }

    @Test
    fun testIsCurrentLanguage() {
        //given
        val lang = "eng"

        //when
        attribute.setCurrentLanguage(lang)

        //then
        assertTrue(attribute.isCurrentLanguage(lang))
        assertFalse(attribute.isCurrentLanguage("test"))
    }

    @Test
    fun testIsRequired() {
        //given
        val required = true
        val notRequired = false

        //when
        attribute.setRequired(required)

        //then
        assertEquals(required, attribute.isRequired())

        //when
        attribute.setRequired(notRequired)

        //then
        assertEquals(notRequired, attribute.isRequired())
    }

    @Test
    fun testIsReadOnly() {
        //given
        val readOnly = true
        val notReadOnly = false

        //when
        attribute.setReadOnly(readOnly)
        attribute.setValueAsText(validValue2AsText)

        //then
        assertEquals(readOnly, attribute.isReadOnly())
        assertEquals(validValue1Uneven.toString(), attribute.getValueAsText())

        //when
        attribute.setReadOnly(notReadOnly)

        //then
        assertEquals(notReadOnly, attribute.isReadOnly())
    }

    @Test
    fun testGetValidationMessage() {
        if(attribute !is StringAttribute) { //type string is always correct
            //when
            attribute.setValueAsText(notValidValueAsText)

            //then
            assertEquals("This is not the correct input type", attribute.getErrorMessages()[0])
        }
    }

    @Test
    fun testIsChanged() {
        //then
        assertFalse(attribute.isChanged())

        //when
        attribute.setValueAsText(validValue2AsText)

        //then
        assertTrue(attribute.isChanged())

        //when
        attribute.save()

        //then
        assertFalse(attribute.isChanged())
    }

    @Test
    fun testNullValues() {
        //given
        val attr = provideAttribute(model, null)

        //then
        assertEquals(null, attr.getValue())
        assertEquals(null, attr.getSavedValue())
        assertEquals("", attr.getValueAsText())
        assertTrue(attr.isValid())

        //when
        attr.setValueAsText(validValue1AsText)
        attr.setValueAsText("")

        //then
        assertEquals(0, attr.getErrorMessages().size)
        assertEquals(null, attr.getValue())
        assertEquals(null, attr.getSavedValue())
        assertEquals("", attr.getValueAsText())
        assertTrue(attr.isValid())

        //when
        attr.setRequired(true)

        attr.setValueAsText(validValue1AsText)
        attr.setValueAsText("")

        //then
        assertEquals("Input required", attr.getErrorMessages()[0])
        assertEquals(validValue1Uneven, attr.getValue(), "Non-valid values are not set in the value")
        assertEquals(null, attr.getSavedValue())
        assertEquals("", attr.getValueAsText())
        assertFalse(attr.isValid())
    }
}