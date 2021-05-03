package model.util.attribute

import model.BaseFormModel
import model.util.Labels
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.lang.IllegalArgumentException
import java.util.*

class SelectionAttributeTest {

    lateinit var selAtr : SelectionAttribute<Labels>
    var model = object: BaseFormModel() { }

    @BeforeEach
    fun setUpSelectionAttribute(){
        //given
        selAtr = SelectionAttribute(model = model, possibleSelections = setOf("Element1", "Element2"), label = Labels.TEST);
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
        //then
        assertEquals("[]", selAtr.getValueAsText())

        //when
        selAtr.removeUserSelection("ElementNotInList")

        //then
        assertEquals("[]", selAtr.getValueAsText())

        //when
        selAtr.addUserSelection("Element1")
        selAtr.addUserSelection("Element2")

        //then
        assertEquals("[Element1, Element2]", selAtr.getValueAsText())

        //when
        selAtr.removeUserSelection("Element1")

        //then
        assertEquals("[Element2]", selAtr.getValueAsText())
    }

    @Test
    fun testSetMinNumberOfSelections() {
        //then
        assertEquals(0, selAtr.getMinNumberOfSelections())
        assertTrue(selAtr.isValid())

        //when
        selAtr.setMinNumberOfSelections(1)

        //then
        assertEquals(1, selAtr.getMinNumberOfSelections())
        assertFalse(selAtr.isValid())

        //when
        selAtr.addUserSelection("Element1")

        //then
        assertTrue(selAtr.isValid())

        //when
        selAtr.setMaxNumberOfSelections(1)

        //then
        assertThrows(IllegalArgumentException ::class.java){
            //when
            selAtr.setMinNumberOfSelections(2)
        }

        //then
        assertThrows(IllegalArgumentException ::class.java){
            //when
            selAtr.setMinNumberOfSelections(-1)
        }

        //when
        selAtr.addUserSelection("Element2")

        //then
        assertFalse(selAtr.isValid())

        //when
        selAtr.setMaxNumberOfSelections(10)

        //then
        assertThrows(IllegalArgumentException ::class.java){
            //when
            selAtr.setMinNumberOfSelections(4)  //minSel > Number of possible elements
        }
    }

    @Test
    fun testSetMaxNumberOfSelections() {
        //then
        assertEquals(Int.MAX_VALUE, selAtr.getMaxNumberOfSelections())
        assertTrue(selAtr.isValid())

        //when
        selAtr.addUserSelection("Element1")
        selAtr.addUserSelection("Element2")
        selAtr.addANewPossibleSelection("Element3")
        selAtr.addUserSelection("Element3")
        selAtr.setMaxNumberOfSelections(2)

        //then
        assertEquals(2, selAtr.getMaxNumberOfSelections())
        assertFalse(selAtr.isValid())

        //when
        selAtr.removeUserSelection("Element3")

        //then
        assertTrue(selAtr.isValid())

        //when
        selAtr.setMinNumberOfSelections(2)

        //then
        assertThrows(IllegalArgumentException ::class.java){
            //when
            selAtr.setMaxNumberOfSelections(1)
        }

        //then
        assertThrows(IllegalArgumentException ::class.java){
            //when
            selAtr.setMinNumberOfSelections(-1)
        }

        //when
        selAtr.addUserSelection("Element3")

        //then
        assertFalse(selAtr.isValid())
    }

    @Test
    fun testSetPossibleSelections() {
        //then
        assertEquals(setOf("Element1", "Element2"), selAtr.getPossibleSelections())

        //when
        selAtr.setPossibleSelections(setOf("A", "B", "C", "D"))

        //then
        assertEquals(setOf("A", "B", "C", "D"), selAtr.getPossibleSelections())

        //when
        selAtr.addANewPossibleSelection("E")

        //then
        assertEquals(setOf("A", "B", "C", "D", "E"), selAtr.getPossibleSelections())

        //when
        selAtr.setMinNumberOfSelections(5)
        selAtr.addUserSelection("A")
        selAtr.addUserSelection("B")
        selAtr.addUserSelection("C")
        selAtr.addUserSelection("D")
        selAtr.addUserSelection("E")

        //then
        assertEquals(5, selAtr.getMinNumberOfSelections())
        assertTrue(selAtr.isValid())

        //then
        assertThrows(IllegalArgumentException ::class.java){
            //when
            selAtr.setPossibleSelections(setOf("A", "B", "C", "D")) //minSel > Number of possible elements
        }
    }

    @Test
    fun testAddANewPossibleSelection() {
        //then
        assertEquals(setOf("Element1", "Element2"), selAtr.getPossibleSelections())

        //when
        selAtr.addANewPossibleSelection("Element1")

        //then
        assertEquals(setOf("Element1", "Element2"), selAtr.getPossibleSelections())

        //when
        selAtr.addANewPossibleSelection("Element3")

        //then
        assertEquals(setOf("Element1", "Element2", "Element3"), selAtr.getPossibleSelections())
    }

    @Test
    fun testRemoveAPossibleSelection() {
        //then
        assertEquals(setOf("Element1", "Element2"), selAtr.getPossibleSelections())

        //then
        assertThrows(IllegalArgumentException :: class.java){
            //when
            selAtr.removeAPossibleSelection("Element3")
        }

        //then
        assertEquals(setOf("Element1", "Element2"), selAtr.getPossibleSelections())

        //when
        selAtr.removeAPossibleSelection("Element2")

        //then
        assertEquals(setOf("Element1"), selAtr.getPossibleSelections())

        //when
        selAtr.addANewPossibleSelection("Element2")
        selAtr.setMinNumberOfSelections(2)

        //then
        assertEquals(setOf("Element1", "Element2"), selAtr.getPossibleSelections())
        assertEquals(2, selAtr.getMinNumberOfSelections())

        //then
        assertThrows(IllegalArgumentException :: class.java){
            //when
            selAtr.removeAPossibleSelection("Element2") //minSel > Number of possible elements
        }

    }

    @Test
    fun testGetMinNumberOfSelections() {
        //then
        assertEquals(0, selAtr.getMinNumberOfSelections())

        //when
        selAtr.setMinNumberOfSelections(1)

        //then
        assertEquals(1, selAtr.getMinNumberOfSelections())
    }

    @Test
    fun testGetMaxNumberOfSelections() {
        //then
        assertEquals(Int.MAX_VALUE, selAtr.getMaxNumberOfSelections())

        //when
        selAtr.setMaxNumberOfSelections(100)

        //then
        assertEquals(100, selAtr.getMaxNumberOfSelections())
    }

    @Test
    fun testGetPossibleSelections() {
        //then
        assertEquals(setOf("Element1", "Element2"), selAtr.getPossibleSelections())

        //when
        selAtr.addANewPossibleSelection("Element3")

        //then
        assertEquals(setOf("Element1", "Element2", "Element3"), selAtr.getPossibleSelections())

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
        //when
        selAtr.setCurrentLanguage("test")

        //then
        assertEquals(Labels.TEST.test, selAtr.getLabel())
        assertTrue(selAtr.isCurrentLanguage("test"))

        //when
        selAtr.setCurrentLanguage("eng")

        //then
        assertEquals(Labels.TEST.eng, selAtr.getLabel())
        assertTrue(selAtr.isCurrentLanguage("eng"))
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
    fun testSetRequired() {
        val label = Labels.TEST.test
        val required = true
        val notRequired = false

        //when
        selAtr.setRequired(required)
        selAtr.setCurrentLanguage("test")

        //then
        assertEquals(required, selAtr.isRequired())
        assertEquals(label + "*", selAtr.getLabel())

        //when
        selAtr.setRequired(notRequired)

        //then
        assertEquals(notRequired, selAtr.isRequired())
        assertEquals(label, selAtr.getLabel())
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
        val lang = "eng"

        //when
        selAtr.setCurrentLanguage(lang)

        //then
        assertTrue(selAtr.isCurrentLanguage(lang))
        assertFalse(selAtr.isCurrentLanguage("test"))
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
        val attr = SelectionAttribute(model = model, possibleSelections = emptySet(), label = Labels.TEST);

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