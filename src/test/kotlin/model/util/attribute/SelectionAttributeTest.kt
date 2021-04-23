package model.util.attribute

import model.BaseFormModel
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.lang.IllegalArgumentException
import java.util.*

class SelectionAttributeTest {

    lateinit var selAtr : SelectionAttribute
    var model = object: BaseFormModel() { }

    @BeforeEach
    fun setUpSelectionAttribute(){
        //given
        selAtr = SelectionAttribute(model = model, possibleSelections = setOf("Element1", "Element2"));
    }

    @Test
    fun checkAndSetValue() {
        //todo
    }

    @Test
    fun testAddUserSelection(){
        //then
        assertEquals("[]", selAtr.getValueAsText())

        //when
        selAtr.addUserSelection("Element1")

        //then
        assertEquals("[Element1]", selAtr.getValueAsText())

        //when
        selAtr.addUserSelection("Element2")

        //then
        assertEquals("[Element1, Element2]", selAtr.getValueAsText())

        //then
        assertThrows(IllegalArgumentException::class.java){
            //when
            selAtr.addUserSelection("NotExistingElement")
        }

        //when
        selAtr.save()
        selAtr.removeUserSelection("Element1")
        selAtr.removeUserSelection("Element2")
        selAtr.addUserSelection("Element2")
        selAtr.addUserSelection("Element1")

        //then
        assertEquals("[Element2, Element1]", selAtr.getValueAsText())
        assertEquals(setOf("Element2", "Element1"), selAtr.getValue())
        assertFalse(selAtr.isChanged(), "Same elements, different order")
    }

    @Test
    fun testRemoveUserSelection(){
        //todo
    }

    @Test
    fun setMinNumberOfSelections() {
        //todo
    }

    @Test
    fun setMaxNumberOfSelections() {
        //todo
    }

    @Test
    fun setPossibleSelections() {
        //todo
    }

    @Test
    fun addANewPossibleSelection() {
        //todo
    }

    @Test
    fun deleteAPossibleSelection() {
        //todo
    }

    @Test
    fun getMinNumberOfSelections() {
        //todo
    }

    @Test
    fun getMaxNumberOfSelections() {
        //todo
    }

    @Test
    fun getPossibleSelections() {
        //todo
    }

    //****************************************************************************************************************
    //Attribute-Tests

    @Test
    fun testSave() {
        //when
        selAtr.addUserSelection("Element1")
        selAtr.save()

        //then
        assertEquals( setOf("Element1"), selAtr.getValue())
        assertEquals( setOf("Element1"), selAtr.getSavedValue())
        assertEquals( "[Element1]", selAtr.getValueAsText())
        assertFalse(selAtr.isChanged())

        //then
        assertThrows(IllegalArgumentException::class.java){
            //when
            selAtr.addUserSelection("notValidElement")
        }
        //when
        selAtr.save()

        //then
        assertEquals( setOf("Element1"), selAtr.getValue())
        assertEquals( setOf("Element1"), selAtr.getSavedValue())
        assertEquals( "[Element1]", selAtr.getValueAsText())
        assertFalse(selAtr.isChanged())

        //when
        selAtr.addUserSelection("Element2")

        //then
        assertEquals( setOf("Element1","Element2"), selAtr.getValue())
        assertEquals( setOf("Element1"), selAtr.getSavedValue())
        assertEquals( "[Element1, Element2]", selAtr.getValueAsText())
        assertTrue(selAtr.isChanged())
    }

    @Test
    fun testUndo() {
        //given
        selAtr.addANewPossibleSelection("Element3")
        selAtr.addANewPossibleSelection("Element4")

        //when
        selAtr.addUserSelection("Element1")
        selAtr.undo()

        //then
        assertEquals( emptySet<String>(), selAtr.getValue())
        assertEquals( emptySet<String>(), selAtr.getSavedValue())
        assertEquals( "[]", selAtr.getValueAsText())

        assertFalse(selAtr.isChanged())

        //when
        selAtr.addUserSelection("Element2")
        selAtr.save()
        selAtr.addUserSelection("Element3")
        selAtr.addUserSelection("Element4")
        selAtr.undo()

        //then
        assertEquals( setOf("Element2"), selAtr.getValue())
        assertEquals( setOf("Element2"), selAtr.getSavedValue())
        assertEquals( "[Element2]", selAtr.getValueAsText())
        assertFalse(selAtr.isChanged())
    }

    @Test
    fun testSetCurrentLanguage() {
        //given
        val defaultLabel = "..."
        val label = "LABEL"
        val lang = Locale.GERMAN

        //when
        selAtr.setCurrentLanguage(lang)

        //then
        assertEquals(defaultLabel, selAtr.getLabel())
        assertTrue(selAtr.isCurrentLanguage(lang))

        //when
        selAtr.setLabelForLanguage(Locale.GERMAN, label)

        //then
        assertEquals(label, selAtr.getLabel())
        assertTrue(selAtr.isCurrentLanguage(lang))
    }

    @Test
    fun testSetValueAsText() {
        //when
        selAtr.addUserSelection("Element1") //add userSelection calls setValueAsText

        //then
        assertEquals("[Element1]", selAtr.getValueAsText())
        assertEquals(setOf("Element1"),selAtr.getValue())

        //then
        assertThrows(IllegalArgumentException::class.java){
            //when
            selAtr.addUserSelection("There was no such selection to choose")
        }

        //then
        assertTrue(selAtr.isValid()) //because setValueAsText(newVal) was not executed and there is still the old value in the inputfield
        assertEquals("[Element1]", selAtr.getValueAsText())
        assertEquals(setOf("Element1"),selAtr.getValue())
    }

    @Test
    fun testSetLabel() {
        //given
        val label = "Name: "

        //when
        selAtr.setLabel(label)

        //then
        assertEquals(label, selAtr.getLabel())
    }

    @Test
    fun setLabelForLanguage() {
        //when
        selAtr.setLabelForLanguage(Locale.GERMAN, "Hallo")
        selAtr.setLabelForLanguage(Locale.ENGLISH, "Hi")

        //then
        assertEquals("Hallo", selAtr.getLabel())

        //when
        selAtr.setCurrentLanguage(Locale.ENGLISH)

        //then
        assertEquals("Hi", selAtr.getLabel())
    }

    @Test
    fun testSetRequired() {
        val label = "Name: "
        val required = true
        val notRequired = false

        //when
        selAtr.setLabel(label)
        selAtr.setRequired(required)

        //then
        assertEquals(required, selAtr.isRequired())
        assertEquals(label + "*", selAtr.getLabel())

        //when
        selAtr.setRequired(notRequired)

        //then
        assertEquals(notRequired, selAtr.isRequired())
    }

    @Test
    fun testSetReadOnly() {
        //given
        val readOnly = true
        val notReadOnly = false

        //when
        selAtr.setReadOnly(readOnly)

        //then
        assertEquals(readOnly, selAtr.isReadOnly())

        //when
        selAtr.setReadOnly(notReadOnly)

        //then
        assertEquals(notReadOnly, selAtr.isReadOnly())
    }

    @Test
    fun testSetValue() {
        println(selAtr.getValue())
        //then
        assertEquals(emptySet<String>(), selAtr.getValue())

        //when
        selAtr.addUserSelection("Element1")

        //then
        assertEquals(setOf("Element1"), selAtr.getValue())
    }

    @Test
    fun testSetValidationMessage() {
        //when
        selAtr.addUserSelection("Element1")

        //then
        assertEquals("Valid Input", selAtr.getValidationMessage())

        //then
        assertThrows(IllegalArgumentException::class.java){
            //when
            selAtr.addUserSelection("notValidElement")
        }

        //then
        assertEquals("Valid Input", selAtr.getValidationMessage())
    }

    @Test
    fun testGetValue() {
        //then
        assertEquals(emptySet<String>(), selAtr.getValue())
    }

    @Test
    fun testGetSavedValue() {
        //when
        selAtr.addUserSelection("Element1")
        selAtr.save()

        //then
        assertEquals(setOf("Element1"),selAtr.getSavedValue())

        //when
        selAtr.addUserSelection("Element2")

        //then
        assertEquals(setOf("Element1"),selAtr.getSavedValue())
    }

    @Test
    fun testGetValueAsText() {
        //then
        assertEquals("[]", selAtr.getValueAsText())
    }

    @Test
    fun testIsCurrentLanguage() {
        //given
        val lang = Locale.GERMAN

        //when
        selAtr.setCurrentLanguage(lang)

        //then
        assertTrue(selAtr.isCurrentLanguage(lang))
        assertFalse(selAtr.isCurrentLanguage(Locale.ENGLISH))
    }

    @Test
    fun testGetLabel() {
        //given
        val label = "Name: "

        //when
        selAtr.setLabel(label)

        //then
        assertEquals(label,selAtr.getLabel())
    }

    @Test
    fun testIsRequired() {
        //given
        val required = true
        val notRequired = false

        //when
        selAtr.setRequired(required)

        //then
        assertEquals(required, selAtr.isRequired())

        //when
        selAtr.setRequired(notRequired)

        //then
        assertEquals(notRequired, selAtr.isRequired())
    }

    @Test
    fun testIsReadOnly() {
        //given
        val readOnly = true
        val notReadOnly = false

        //when
        selAtr.setReadOnly(readOnly)
        selAtr.addUserSelection("Element1")

        //then
        assertEquals(readOnly, selAtr.isReadOnly())

        //then
        assertEquals("[]", selAtr.getValueAsText())


        //when
        selAtr.setReadOnly(notReadOnly)

        //then
        assertEquals(notReadOnly, selAtr.isReadOnly())
    }

    @Test
    fun testGetValidationMessage() {
        //then
        assertThrows(IllegalArgumentException::class.java){
            //when
            selAtr.addUserSelection("notValidValueAsText")
        }

        //then
        assertEquals("Valid Input", selAtr.getValidationMessage()) // "There was no such selection to choose" will be overwritten
    }

    @Test
    fun testIsChanged() {
        //then
        assertFalse(selAtr.isChanged())

        //when
        selAtr.addUserSelection("Element1")

        //then
        assertTrue(selAtr.isChanged())

        //when
        selAtr.save()

        //then
        assertFalse(selAtr.isChanged())
    }

    @Test
    fun testNullValues() {
        //given
        val attr = SelectionAttribute(model = model, possibleSelections = emptySet());

        //then
        assertEquals(emptySet<String>(), attr.getValue())
        assertEquals(emptySet<String>(), attr.getSavedValue())
        assertEquals("[]", attr.getValueAsText())
        assertTrue(attr.isValid())

        //given
        selAtr

        //when
        selAtr.addUserSelection("Element1")
        //then
        assertThrows(IllegalArgumentException::class.java){
            //when
            selAtr.addUserSelection("")
        }

        //then
        assertEquals("Valid Input", selAtr.getValidationMessage())
        assertEquals(setOf("Element1"), selAtr.getValue())
        assertEquals(emptySet<String>(), selAtr.getSavedValue())
        assertEquals("[Element1]", selAtr.getValueAsText())
        assertTrue(selAtr.isValid())

        //when
        selAtr.removeUserSelection("Element1")

        //then
        assertEquals(emptySet<String>(), selAtr.getValue())
        assertEquals(emptySet<String>(), selAtr.getSavedValue())
        assertEquals("[]", selAtr.getValueAsText())

        //when
        selAtr.setRequired(true)

        //then
        assertEquals("Input Required", selAtr.getValidationMessage())
        assertEquals(emptySet<String>(), selAtr.getValue())
        assertEquals(emptySet<String>(), selAtr.getSavedValue())
        assertEquals("[]", selAtr.getValueAsText())
        assertFalse(selAtr.isValid())
    }
}