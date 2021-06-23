package model

import androidx.compose.ui.focus.FocusRequester
import communication.AttributeType
import io.mockk.mockk
import model.meanings.Default
import model.util.Group
import model.util.ILabel
import model.util.attribute.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach

internal class BaseFormModelTest {

    var model = object: BaseFormModel(){
        override fun getPossibleLanguages(): List<String> {
            return Label.getLanguages()
        }
    }

    val ALTER = 50
    val ANZ_KINDER = 3

    lateinit var alter : Attribute<*,*,*>
    lateinit var anzKinder : Attribute<*,*,*>
    lateinit var group : Group

    enum class Label(val test: String): ILabel{
        ALTER("Alter"),
        ANZKINDER("Anzahl Kinder"),
        TEST("");

        companion object {
            fun getLanguages(): List<String> {
                return (values()[0] as ILabel).getLanguagesDynamic()
            }
        }
    }

    @BeforeEach
    fun setUp(){
        //reset
        Attribute.resetId()

        //given
        alter = IntegerAttribute(model = model, value = ALTER, label = Label.ALTER)

        anzKinder = IntegerAttribute(model = model, value = ANZ_KINDER, label = Label.ANZKINDER)

        group = Group(model, "Group 1", listOf(alter, anzKinder))
        Group(model, "Group 2", listOf(alter))
        Group(model, "Group 3", listOf())
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
        val attribute = ShortAttribute(model, Label.TEST, 5)

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
        val attribute = LongAttribute(model, Label.TEST, 5)

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
        val attribute = DoubleAttribute(model, Label.TEST, 5.5)

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
        val attribute = FloatAttribute(model, Label.TEST, 5.5f)

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
        val attribute = StringAttribute(model, Label.TEST, "a")

        //then
        assertEquals("a", attribute.getValue())
        assertEquals("a", attribute.getValueAsText())
        assertEquals("a", attribute.getSavedValue())

        //when
        val attributeDefaultVal = StringAttribute(model, Label.TEST)

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

    @Test
    fun testSetCurrentLanguageForAll() {
        //when
        model.setCurrentLanguageForAll("test")

        //then
        assertTrue(model.isCurrentLanguageForAll("test"))
        assertEquals("Anzahl Kinder", anzKinder.getLabel())


        //then
        assertThrows(IllegalArgumentException::class.java) {
            model.setCurrentLanguageForAll("de")
        }
    }

    @Test
    fun testSetTitle() {
        //when
        model.setTitle("The Application")

        //then
        assertEquals("The Application", model.getTitle())
    }

    @Test
    fun testGetGroupsAndAttributes() {
        //then
        assertEquals(3, model.getGroups().flatMap{it.getAttributes()}.size)
        assertEquals(2, model.getGroups()[0].getAttributes().size)
        assertEquals(1, model.getGroups()[1].getAttributes().size)
        assertEquals(0, model.getGroups()[2].getAttributes().size)
        assertEquals(3, model.getGroups().size)
        assertEquals("Group 1", model.getGroups()[0].title)
        assertEquals("Group 2", model.getGroups()[1].title)
        assertEquals("Group 3", model.getGroups()[2].title)
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

    @Test
    fun testIsCurrentLanguageForAll() {
        //when
        model.setCurrentLanguageForAll("test")
        //then
        assertFalse(model.isCurrentLanguageForAll("eng"))
        assertTrue(model.isCurrentLanguageForAll("test"))

    }

    @Test
    fun testGetAttributeType(){
        //when
        var attr: Attribute<*,*,*> = StringAttribute(model, Label.ANZKINDER)
        //then
        assertEquals(AttributeType.STRING, model.getAttributeType(attr))

        //when
        attr = DoubleAttribute(model, Label.ANZKINDER)
        //then
        assertEquals(AttributeType.DOUBLE, model.getAttributeType(attr))

        //when
        attr = FloatAttribute(model, Label.ANZKINDER)
        //then
        assertEquals(AttributeType.FLOAT, model.getAttributeType(attr))

        //when
        attr = ShortAttribute(model, Label.ANZKINDER)
        //then
        assertEquals(AttributeType.SHORT, model.getAttributeType(attr))

        //when
        attr = IntegerAttribute(model, Label.ANZKINDER)
        //then
        assertEquals(AttributeType.INTEGER, model.getAttributeType(attr))

        //when
        attr= LongAttribute(model, Label.ANZKINDER)
        //then
        assertEquals(AttributeType.LONG, model.getAttributeType(attr))

        //when
        attr = SelectionAttribute(model, Label.ANZKINDER, emptySet())
        //then
        assertEquals(AttributeType.SELECTION, model.getAttributeType(attr))


        //when
        attr = object : Attribute<StringAttribute<Label>, String, Label>(model, Label.ANZKINDER, null, false, false, emptyList(), emptyList(),
            emptyList(), Default()){
            override val typeT: String
                get() = ""
        }
        //then
        assertEquals(AttributeType.OTHER, model.getAttributeType(attr))

    }


    @Test
    fun testSetCurrentFocusIndex(){
        //when
        model.setCurrentFocusIndex(0)
        //then
        assertEquals(0, model.getCurrentFocusIndex())


        //when
        model.setCurrentFocusIndex(1)
        //then
        assertEquals(1, model.getCurrentFocusIndex())


        //when
        model.setCurrentFocusIndex(10)
        //then
        assertEquals(10, model.getCurrentFocusIndex())


        //when
        model.setCurrentFocusIndex(-10)
        //then
        assertEquals(10, model.getCurrentFocusIndex())
    }

    @Test
    fun testFocusRequesters(){
        //given
        val fr1 : FocusRequester = mockk(relaxed = true)
        //when
        val i1 = model.addFocusRequester(fr1, alter)
        //then
        assertEquals(0, i1)

        //given
        val fr2 : FocusRequester = mockk(relaxed = true)
        //when
        val i2 = model.addFocusRequester(fr2, anzKinder)
        //then
        assertEquals(1, i2)

        //when
        val i3 = model.addFocusRequester(fr1, alter)
        //then
        assertEquals(-1, i3)


        assertEquals(null, model.getCurrentFocusIndex())
        //when
        model.focusNext()
        //then
        assertEquals(0, model.getCurrentFocusIndex())

        //when
        model.focusNext()
        //then
        assertEquals(1, model.getCurrentFocusIndex())

        //when
        model.focusNext()
        //then
        assertEquals(0, model.getCurrentFocusIndex())

        //when
        model.focusPrevious()
        //then
        assertEquals(1, model.getCurrentFocusIndex())

        //previous
        //when
        model.focusPrevious()
        //then
        assertEquals(0, model.getCurrentFocusIndex())
    }

    @Test
    fun testOnReceivedText(){
        //given
        val string = "{ \"id\":0, \"text\":\"123\" }"
        //when
        model.onReceivedText(string)
        //then
        assertEquals("123", alter.getValueAsText())


        //given
        val string2 = "{ \"id\":2, \"text\":\"123\" }"
        //when
        model.onReceivedText(string)
        //then
        assertEquals("123", alter.getValueAsText())
    }

    @Test
    fun testOnReceivedCommand(){
        //given
        val attr = StringAttribute(model, Label.ANZKINDER)
        group.addAttribute(attr)

        val fr1 : FocusRequester = mockk(relaxed = true)
        model.addFocusRequester(fr1, alter)
        val fr2 : FocusRequester = mockk(relaxed = true)
        model.addFocusRequester(fr2, anzKinder)
        val fr3 : FocusRequester = mockk(relaxed = true)
        model.addFocusRequester(fr3, attr)


        model.setCurrentFocusIndex(0)
        assertEquals(0, model.getCurrentFocusIndex())

        val start = "{ \"command\" :"
        val end = " }"
        var mid = ""

        //when
        mid = "\"NEXT\""
        val command1 = start + mid + end
        model.onReceivedCommand(command1)
        //then
        assertEquals(1, model.getCurrentFocusIndex())

        model.onReceivedCommand(command1)
        //then
        assertEquals(2, model.getCurrentFocusIndex())

        //when
        mid = "\"PREVIOUS\""
        val command2 = start + mid + end
        model.onReceivedCommand(command2)
        //then
        assertEquals(1, model.getCurrentFocusIndex())
    }

    @Test
    fun testOnReceivedCommandNull(){
        //given
        val attr = StringAttribute(model, Label.ANZKINDER)
        group.addAttribute(attr)

        val fr1 : FocusRequester = mockk(relaxed = true)
        model.addFocusRequester(fr1, alter)
        val fr2 : FocusRequester = mockk(relaxed = true)
        model.addFocusRequester(fr2, anzKinder)
        val fr3 : FocusRequester = mockk(relaxed = true)
        model.addFocusRequester(fr3, attr)

        assertEquals(null, model.getCurrentFocusIndex())

        val start = "{ \"command\" :"
        val end = " }"
        var mid = ""

        //when
        mid = "\"REQUEST\""
        val command1 = start + mid + end
        model.onReceivedCommand(command1)
        //then
        assertEquals(null, model.getCurrentFocusIndex())

        //when
        mid = "\"NEXT\""
        val command2 = start + mid + end
        model.onReceivedCommand(command2)
        //then
        assertEquals(0, model.getCurrentFocusIndex())

    }

}