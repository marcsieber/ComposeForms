package model.util.attribute

import model.BaseFormModel

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.util.*

abstract class AttributeTest<T : Any> {

    var model = object: BaseFormModel() { }

    lateinit var validValue1_uneven : T
    lateinit var validValue2 : T
    lateinit var validValue3 : T
    lateinit var validValue4 : T
    lateinit var validValue1AsText : String
    lateinit var validValue2AsText : String
    lateinit var validValue3AsText : String
    lateinit var validValue4AsText : String
    lateinit var notValidValueAsText : String
    lateinit var validationMessage : String

    lateinit var attribute : Attribute<*,*>

    protected abstract fun provideAttribute(model: BaseFormModel, value: T?) : Attribute<*, Any>

    @BeforeEach
    fun setUp(){
        //given
        attribute = provideAttribute(model, validValue1_uneven)
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

        //when
        attribute.setValueAsText(notValidValueAsText)
        attribute.save()

        //then
        assertEquals( notValidValueAsText, attribute.getValueAsText())
        assertEquals(     validValue2,          attribute.getValue())
        assertEquals(     validValue2,          attribute.getSavedValue())
        assertFalse(attribute.isChanged())

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
        assertEquals(validValue1_uneven,         attribute.getValue())
        assertEquals(validValue1_uneven,         attribute.getSavedValue())
        assertEquals(validValue1_uneven.toString(), attribute.getValueAsText())
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
        //given
        val defaultLabel = "..."
        val label = "LABEL"
        val lang = Locale.GERMAN

        //when
        attribute.setCurrentLanguage(lang)

        //then
        assertEquals(defaultLabel, attribute.getLabel())
        assertTrue(attribute.isCurrentLanguage(lang))

        //when
        attribute.setLabelForLanguage(Locale.GERMAN, label)

        //then
        assertEquals(label, attribute.getLabel())
        assertTrue(attribute.isCurrentLanguage(lang))
    }

    @Test
    fun testSetValueAsText() {
        //when
        attribute.setValueAsText(validValue2AsText)

        //then
        assertEquals(validValue2AsText, attribute.getValueAsText())
        assertEquals(validValue2,attribute.getValue())

        //when
        attribute.setValueAsText(notValidValueAsText)

        //then
        assertFalse(attribute.isValid())
        assertEquals(validationMessage,    attribute.getValidationMessage())
        assertEquals(validValue2,              attribute.getValue())
        assertEquals(notValidValueAsText,   attribute.getValueAsText())
    }

    @Test
    fun testSetLabel() {
        //given
        val label = "Name: "

        //when
        attribute.setLabel(label)

        //then
        assertEquals(label, attribute.getLabel())
    }

    @Test
    fun setLabelForLanguage() {
        //when
        attribute.setLabelForLanguage(Locale.GERMAN, "Hallo")
        attribute.setLabelForLanguage(Locale.ENGLISH, "Hi")

        //then
        assertEquals("Hallo", attribute.getLabel())

        //when
        attribute.setCurrentLanguage(Locale.ENGLISH)

        //then
        assertEquals("Hi", attribute.getLabel())
    }

    @Test
    fun testSetRequired() {
        val label = "Name: "
        val required = true
        val notRequired = false

        //when
        attribute.setLabel(label)
        attribute.setRequired(required)

        //then
        assertEquals(required, attribute.isRequired())
        assertEquals(label + "*", attribute.getLabel())

        //when
        attribute.setRequired(notRequired)

        //then
        assertEquals(notRequired, attribute.isRequired())
    }

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
    fun testSetValid() {
        //given
        val valid = true
        val notValid = false

        //when
        attribute.setValid(valid)

        //then
        assertEquals(valid, attribute.isValid())

        //when
        attribute.setValid(notValid)

        //then
        assertEquals(notValid, attribute.isValid())
    }

    @Test
    fun testSetValue() {
        //then
        assertEquals(validValue1_uneven, attribute.getValue())

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
        assertEquals("Valid Input", attribute.getValidationMessage())

        //when
        attribute.setValueAsText(notValidValueAsText)

        //then
        assertEquals(validationMessage, attribute.getValidationMessage())
    }

    @Test
    fun testGetValue() {
        //then
        assertEquals(validValue1_uneven, attribute.getValue())
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
        assertEquals(validValue1_uneven.toString(), attribute.getValueAsText())
    }

    @Test
    fun testIsCurrentLanguage() {
        //given
        val lang = Locale.GERMAN

        //when
        attribute.setCurrentLanguage(lang)

        //then
        assertTrue(attribute.isCurrentLanguage(lang))
        assertFalse(attribute.isCurrentLanguage(Locale.ENGLISH))
    }

    @Test
    fun testGetLabel() {
        //given
        val label = "Name: "

        //when
        attribute.setLabel(label)

        //then
        assertEquals(label,attribute.getLabel())
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
        assertEquals(validValue1_uneven.toString(), attribute.getValueAsText())

        //when
        attribute.setReadOnly(notReadOnly)

        //then
        assertEquals(notReadOnly, attribute.isReadOnly())
    }

    @Test
    fun testIsValid() {
        //given
        val valid = true
        val notValid = false

        //when
        attribute.setValid(valid)

        //then
        assertEquals(valid, attribute.isValid())

        //when
        attribute.setValid(notValid)

        //then
        assertEquals(notValid, attribute.isValid())
    }

    @Test
    fun testGetValidationMessage() {
        //when
        attribute.setValueAsText(notValidValueAsText)

        //then
        assertEquals(validationMessage, attribute.getValidationMessage())
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



        //after change
        attr.setValueAsText(validValue1AsText)
        attr.setValueAsText("")
        assertEquals("Valid Input", attr.getValidationMessage())
        assertEquals(null, attr.getSavedValue())
        assertEquals("", attr.getValueAsText())
        assertTrue(attr.isValid())

        //is required
    }
}