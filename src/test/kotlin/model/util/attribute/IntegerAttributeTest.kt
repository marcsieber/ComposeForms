package model.util.attribute

import androidx.compose.runtime.mutableStateListOf
import model.BaseFormModel
import model.FormModel
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.lang.IllegalArgumentException
import java.util.*

internal class IntegerAttributeTest {

    var model : FormModel = object:FormModel{
        override fun getAttributes(): List<Attribute<*>> {
            return mutableStateListOf()
        }

        override fun getTitle(): String {
            return ""
        }

        override fun saveAll(): Boolean {
            return true
        }

        override fun undoAll(): Boolean {
            return true
        }

        override fun setCurrentLanguageForAll(lang: Locale) {

        }

        override fun setChangedForAll() {

        }

        override fun setValidForAll() {

        }
    }

    @Test
    fun testSave() {
        //given
        val intAttribute = IntegerAttribute(model, 5)

        //when
        intAttribute.setValueAsText("7")
        intAttribute.save()

        //then
        assertSame(7,intAttribute.getValue())
        assertSame(7, intAttribute.getSavedValue())
        assertEquals("7", intAttribute.getValueAsText())
        assertSame(false, intAttribute.isChanged())
    }

    @Test
    fun testUndo() {
        //given
        val intAttribute = IntegerAttribute(model,3)

        //when
        intAttribute.setValueAsText("7")
        intAttribute.undo()

        //then
        assertSame(3,intAttribute.getValue())
        assertSame(3, intAttribute.getSavedValue())
        assertEquals("3", intAttribute.getValueAsText())
        assertSame(false, intAttribute.isChanged())

        //when
        intAttribute.setValueAsText("5")
        intAttribute.save()
        intAttribute.setValueAsText("12")
        intAttribute.setValueAsText("14")
        intAttribute.undo()

        //then
        assertSame(5,intAttribute.getValue())
        assertSame(5, intAttribute.getSavedValue())
        assertEquals("5", intAttribute.getValueAsText())
        assertSame(false, intAttribute.isChanged())
    }

    @Test
    fun testSetCurrentLanguage() {
        //given
        val intA = IntegerAttribute(model,2)
        val defaultLabel = "..."
        val label = "LABEL"
        val lang = Locale.GERMAN

        //when
        intA.setCurrentLanguage(lang)

        //then
        assertEquals(defaultLabel, intA.getLabel())
        assertTrue(intA.isCurrentLanguage(lang))

        //when
        intA.setLabelForLanguage(Locale.GERMAN, label)

        //then
        assertEquals(label, intA.getLabel())
        assertTrue(intA.isCurrentLanguage(lang))
    }

    @Test
    fun testIsCurrentLanguage() {
        //given
        val intA = IntegerAttribute(model,4)
        val lang = Locale.GERMAN

        //when
        intA.setCurrentLanguage(lang)

        //then
        assertTrue(intA.isCurrentLanguage(lang))
        assertFalse(intA.isCurrentLanguage(Locale.ENGLISH))
    }

    @Test
    fun testGetValue() {
        //given
        val intA = IntegerAttribute(model,24)

        //then
        assertSame(24,intA.getValue())
    }

    @Test
    fun testGetSavedValue() {
        //given
        val intA = IntegerAttribute(model,24)

        //when
        intA.setValueAsText("20")
        intA.save()

        //then
        assertSame(20,intA.getSavedValue())

        //when
        intA.setValueAsText("3")

        //then
        assertSame(20,intA.getSavedValue())
    }

    @Test
    fun testSetValAsText() {
        //given
        val intA = IntegerAttribute(model,8)

        //when
        intA.setValueAsText("4")

        //then
        assertEquals("4", intA.getValueAsText())
        assertSame(4,intA.getValue())

        //when
        intA.setValueAsText("a")

        //then
        assertSame(false, intA.isValid())
        assertSame("No Integer", intA.getValidationMessage())
        assertSame(4,intA.getValue())
        assertEquals("a",intA.getValueAsText())
    }

    @Test
    fun testGetValAsText() {
        //given
        val intA = IntegerAttribute(model,24)

        //then
        assertEquals("24",intA.getValueAsText())
    }

    @Test
    fun testSetLabel() {
        //given
        val intA = IntegerAttribute(model,12)
        val label = "Name: "

        //when
        intA.setLabel(label)

        //then
        assertEquals(label, intA.getLabel())
    }

    @Test
    fun testGetLabel() {
        //given
        val intA = IntegerAttribute(model,24)
        val label = "Name: "

        //when
        intA.setLabel(label)

        //then
        assertSame(label,intA.getLabel())
    }

    @Test
    fun testSetLabelForLanguage() {
        //given
        val intA = IntegerAttribute(model,3)

        //when
        intA.setLabelForLanguage(Locale.GERMAN, "Hallo")
        intA.setLabelForLanguage(Locale.ENGLISH, "Hi")

        //then
        assertEquals("Hallo", intA.getLabel())

        //when
        intA.setCurrentLanguage(Locale.ENGLISH)

        //then
        assertEquals("Hi", intA.getLabel())
    }

    @Test
    fun testSetRequired() {
        //given
        val intA = IntegerAttribute(model,12)
        val label = "Name: "
        val required = true
        val notRequired = false

        //when
        intA.setLabel(label)
        intA.setRequired(required)

        //then
        assertEquals(required, intA.isRequired())
        assertEquals(label + "*", intA.getLabel())

        //when
        intA.setRequired(notRequired)

        //then
        assertEquals(notRequired, intA.isRequired())
    }

    @Test
    fun testIsRequired() {
        //given
        val intA = IntegerAttribute(model,24)
        val required = true
        val notRequired = false

        //when
        intA.setRequired(required)

        //then
        assertSame(required, intA.isRequired())

        //when
        intA.setRequired(notRequired)

        //then
        assertSame(notRequired, intA.isRequired())
    }

    @Test
    fun testSetReadOnly() {
        //given
        val intA = IntegerAttribute(model,12)
        val readOnly = true
        val notReadOnly = false

        //when
        intA.setReadOnly(readOnly)

        //then
        assertEquals(readOnly, intA.isReadOnly())

        //when
        intA.setReadOnly(notReadOnly)

        //then
        assertEquals(notReadOnly, intA.isReadOnly())
    }

    @Test
    fun testIsReadOnly() {
        //given
        val intA = IntegerAttribute(model,24)
        val readOnly = true
        val notReadOnly = false

        //when
        intA.setReadOnly(readOnly)
        intA.setValueAsText("2")

        //then
        assertSame(readOnly, intA.isReadOnly())
        assertEquals("24", intA.getValueAsText())

        //when
        intA.setReadOnly(notReadOnly)

        //then
        assertSame(notReadOnly, intA.isReadOnly())
    }

    @Test
    fun testSetValid() {
        //given
        val intA = IntegerAttribute(model,12)
        val valid = true
        val notValid = false

        //when
        intA.setValid(valid)

        //then
        assertEquals(valid, intA.isValid())

        //when
        intA.setValid(notValid)

        //then
        assertEquals(notValid, intA.isValid())
    }

    @Test
    fun testIsValid() {
        //given
        val intA = IntegerAttribute(model,24)
        val valid = true
        val notValid = false

        //when
        intA.setValid(valid)

        //then
        assertSame(valid, intA.isValid())

        //when
        intA.setValid(notValid)

        //then
        assertSame(notValid, intA.isValid())
    }

    @Test
    fun testGetValidationMessage(){
        //given
        val intA = IntegerAttribute(model,2)

        //when
        intA.setValueAsText("Hallo")

        //then
        assertSame("No Integer", intA.getValidationMessage())
    }

    @Test
    fun testIsChanged() {
        //given
        val intA = IntegerAttribute(model,3)

        //then
        assertSame(false, intA.isChanged())

        //when
        intA.setValueAsText("5")

        //then
        assertSame(true, intA.isChanged())

        //when
        intA.save()

        //then
        assertSame(false, intA.isChanged())
    }

    @Test
    fun testSetLowerBound() {
        //given
        val intA = IntegerAttribute(model,12)
        val upperBound = 10
        val lowerBoundWrong1 = 11
        val lowerBoundWrong2 = 10
        val lowerBoundCorrect = 0

        //when
        intA.setUpperBound(upperBound)

        //then
        assertThrows(IllegalArgumentException :: class.java){
            //when
            intA.setLowerBound(lowerBoundWrong1)
        }

        //then
        assertThrows(IllegalArgumentException :: class.java){
            //when
            intA.setLowerBound(lowerBoundWrong2)
        }

        //when
        intA.setLowerBound(lowerBoundCorrect)

        //then
        assertSame(lowerBoundCorrect, intA.getLowerBound())
    }

    @Test
    fun testGetLowerBound() {
        //given
        val intA = IntegerAttribute(model,24)
        val lowerBound = 3

        //when
        intA.setLowerBound(lowerBound)

        //then
        assertSame(lowerBound, intA.getLowerBound())
    }

    @Test
    fun testSetUpperBound() {
        //given
        val intA = IntegerAttribute(model,12)
        val lowerBound = 10
        val upperBoundWrong1 = 9
        val upperBoundWrong2 = 10
        val upperBoundCorrect = 14

        //when
        intA.setLowerBound(lowerBound)

        //then
        assertThrows(IllegalArgumentException :: class.java){
            //when
            intA.setUpperBound(upperBoundWrong1)
        }

        //then
        assertThrows(IllegalArgumentException :: class.java){
            //when
            intA.setUpperBound(upperBoundWrong2)
        }

        //when
        intA.setUpperBound(upperBoundCorrect)

        //then
        assertSame(upperBoundCorrect, intA.getUpperBound())
    }

    @Test
    fun testGetUpperBound() {
        //given
        val intA = IntegerAttribute(model,24)
        val upperBound = 3

        //when
        intA.setUpperBound(upperBound)

        //then
        assertSame(upperBound, intA.getUpperBound())
    }

    @Test
    fun testSetStepSize() {
        //given
        val intA = IntegerAttribute(model,12)
        val stepSize = 2

        //when
        intA.setStepSize(stepSize)

        //then
        assertSame(2,intA.getStepSize())
    }

    @Test
    fun testGetStepSize() {
        //given
        val intA = IntegerAttribute(model,24)
        val stepSize = 4

        //when
        intA.setStepSize(stepSize)

        //then
        assertSame(stepSize, intA.getStepSize())

        //when
        intA.setValueAsText("28")

        //then
        assertSame(true, intA.isValid())

        //when
        intA.setValueAsText("25")

        //then
        assertSame(false, intA.isValid())
    }

    @Test
    fun testFailForPipeline(){
        assertEquals(true, false)
    }
}
