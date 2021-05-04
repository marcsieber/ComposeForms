package model

import model.util.ILabel
import model.util.attribute.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach

internal class BaseFormModelTest {

    var model = object: BaseFormModel(){}

    val ALTER = 50
    val ANZ_KINDER = 3

    lateinit var alter : Attribute<*,*,*>
    lateinit var anzKinder : Attribute<*,*,*>

    enum class Label(val test: String): ILabel{
        ALTER("Alter"),
        ANZKINDER("Anzahl Kinder"),
        TEST("")
    }

    @BeforeEach
    fun setUp(){
        //given
        alter = IntegerAttribute(model = model, value = ALTER, label = Label.ALTER)

        anzKinder = IntegerAttribute(model = model, value = ANZ_KINDER, label = Label.ANZKINDER)
    }

    @Test
    fun testSaveAll() {
        //when
        alter.setValueAsText("a")

        //then
        assertTrue(model.isChangedForAll())
        assertFalse(model.isValidForAll())

        //when
        alter.setValueAsText("61")

        //then
        assertTrue(model.isChangedForAll())
        assertTrue(model.isValidForAll())

        //when
        model.saveAll()

        //then
        assertFalse(model.isChangedForAll())
        assertTrue(model.isValidForAll())
        assertEquals("61", alter.getValueAsText())
        assertSame(61, alter.getValue())
        assertEquals(61,alter.getSavedValue())
        assertEquals(ANZ_KINDER.toString(), anzKinder.getValueAsText())
        assertSame(ANZ_KINDER, anzKinder.getValue())
        assertEquals(ANZ_KINDER,anzKinder.getSavedValue())

        //when
        alter.setValueAsText("40")

        //then
        assertEquals("40", alter.getValueAsText())
        assertSame(40, alter.getValue())
        assertEquals(61,alter.getSavedValue())


        //when
        alter.setValueAsText("a")
        assertFalse(model.saveAll())
    }

    @Test
    fun testUndoAll() {

        //when
        alter.setValueAsText("61")
        anzKinder.setValueAsText("2")
        model.undoAll()

        //then
        assertSame(ANZ_KINDER, anzKinder.getValue())
        assertSame(ALTER, alter.getValue())

        //when
        alter.setValueAsText("61")
        anzKinder.setValueAsText("2")
        model.saveAll()
        model.undoAll()

        //then
        assertSame(61, alter.getValue())
        assertSame(2, anzKinder.getValue())
    }

    @Test
    fun testCreateIntegerAttribute() {
        //when
        val attribute = IntegerAttribute(model = model, value = 5, label = Label.TEST)

        //then
        assertEquals(5,attribute.getValue())
        assertEquals("5",attribute.getValueAsText())
        assertEquals(5,attribute.getSavedValue())

        //when
        val attributeDefaultVal = IntegerAttribute(model = model, label = Label.TEST)

        //then
        assertEquals(null, attributeDefaultVal.getValue())
    }

    @Test
    fun testCreateShortAttribute() {
        //when
        val attribute = ShortAttribute(model, 5, label = Label.TEST)

        //then
        assertEquals(5,attribute.getValue())
        assertEquals("5",attribute.getValueAsText())
        assertEquals(5,attribute.getSavedValue())

        //when
        val attributeDefaultVal = ShortAttribute(model, label = Label.TEST)

        //then
        assertEquals(null, attributeDefaultVal.getValue())
    }

    @Test
    fun testCreateLongAttribute() {
        //when
        val attribute = LongAttribute(model, 5, label = Label.TEST)

        //then
        assertEquals(5,attribute.getValue())
        assertEquals("5",attribute.getValueAsText())
        assertEquals(5,attribute.getSavedValue())

        //when
        val attributeDefaultVal = LongAttribute(model, label = Label.TEST)

        //then
        assertEquals(null, attributeDefaultVal.getValue())
    }

    @Test
    fun testCreateDoubleAttribute() {
        //when
        val attribute = DoubleAttribute(model, 5.5, label = Label.TEST)

        //then
        assertEquals(5.5,attribute.getValue())
        assertEquals("5.5",attribute.getValueAsText())
        assertEquals(5.5,attribute.getSavedValue())

        //when
        val attributeDefaultVal = DoubleAttribute(model, label = Label.TEST)

        //then
        assertEquals(null, attributeDefaultVal.getValue())
    }

    @Test
    fun testCreateFloatAttribute() {
        //when
        val attribute = FloatAttribute(model,5.5f, label = Label.TEST)

        //then
        assertEquals(5.5f,attribute.getValue())
        assertEquals("5.5",attribute.getValueAsText())
        assertEquals(5.5f,attribute.getSavedValue())

        //when
        val attributeDefaultVal = FloatAttribute(model, label = Label.TEST)

        //then
        assertEquals(null, attributeDefaultVal.getValue())
    }

    @Test
    fun testCreateStringAttribute() {
        //when
        val attribute = StringAttribute(model,"a", label = Label.TEST)

        //then
        assertEquals("a", attribute.getValue())
        assertEquals("a", attribute.getValueAsText())
        assertEquals("a", attribute.getSavedValue())

        //when
        val attributeDefaultVal = StringAttribute(model, label = Label.TEST)

        //then
        assertEquals(null, attributeDefaultVal.getValue())

    }

    @Test
    fun testSetChangedForAll() {
        //when
        model.setChangedForAll()

        //then
        assertFalse(model.isChangedForAll())

        //when
        anzKinder.setValueAsText("5")

        //then
        assertTrue(model.isChangedForAll())

        //when
        model.setChangedForAll()

        //then
        assertTrue(model.isChangedForAll())
    }

    @Test
    fun testSetValidForAll() {
        //when
        model.setValidForAll()

        //then
        assertTrue(model.isValidForAll())

        //when
        anzKinder.setValueAsText("a")

        //then
        assertFalse(model.isValidForAll())

        //when
        alter.setValueAsText("5")

        //then
        assertFalse(model.isValidForAll())
    }


    // TODO: Language is not implementet atm
//    @Test
//    fun testSetCurrentLanguageForAll() {
//        //when
//        model.setCurrentLanguageForAll(Locale.GERMAN)
//
//        //then
//        assertTrue(model.isCurrentLanguageForAll(Locale.GERMAN))
//        assertEquals("...", anzKinder.getLabel())
//
//        //when
//        val attribute1 = IntegerAttribute(model = model, value = 9, label = "Name")
////                .setLabel("Name")
////                .setLabelForLanguage(Locale.GERMAN, "Nummer: ")
////                .setLabelForLanguage(Locale.ENGLISH, "number: ")
//
//        //then
//        assertEquals("Nummer: ", attribute1.getLabel())
//
//        //when
//        val attribute2 = IntegerAttribute(model = model, value = 9)
////                .setLabelForLanguage(Locale.GERMAN, "Nummer: ")
////                .setLabelForLanguage(Locale.ENGLISH, "number: ")
////                .setLabel("Name")
//
//        //then
//        assertEquals("Name", attribute2.getLabel())
//
//        //when
//        model.setCurrentLanguageForAll(Locale.ENGLISH)
//
//        //then
//        assertTrue(model.isCurrentLanguageForAll(Locale.ENGLISH))
//        assertEquals("number: ", attribute2.getLabel())
//    }

    @Test
    fun testSetTitle() {
        //when
        model.setTitle("The Application")

        //then
        assertEquals("The Application", model.getTitle())
    }

    @Test
    fun testGetAttributes() {
        //then
        assertEquals(2, model.getAttributes().size)
    }

    @Test
    fun testGetTitle() {
        //then
        assertEquals("", model.getTitle())

        //when
        model.setTitle("The Application")

        //then
        assertEquals("The Application", model.getTitle())
    }

    @Test
    fun testIsChangedForAll() {
        //then
        assertFalse(model.isChangedForAll())

        //when
        alter.setValueAsText("3")

        //then
        assertTrue(model.isChangedForAll())

        //when
        model.saveAll()

        //then
        assertFalse(model.isChangedForAll())

        //when
        alter.setValueAsText("3")

        //then
        assertFalse(model.isChangedForAll())
    }

    @Test
    fun testIsValidForAll() {
        //then
        assertTrue(model.isValidForAll())

        //when
        alter.setValueAsText("10")

        //then
        assertTrue(model.isValidForAll())

        //when
        alter.setValueAsText("b")

        //then
        assertFalse(model.isValidForAll())
    }

//    @Test
//    fun testIsCurrentLanguageForAll() {
//        //when
//        model.setCurrentLanguageForAll("eng")
//        //then
//        assertTrue(model.isCurrentLanguageForAll("eng"))
//
//    }
}