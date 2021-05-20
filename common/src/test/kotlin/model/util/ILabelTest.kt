package model.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class ILabelTest {

    enum class Testenum(val x : String, val y : String) : ILabel{
        TEST("x", "y"),
    }

    enum class Labels(val eng: String, val de: String, private val fr: String) : ILabel{
        TEST("eng", "de", "fr"),
        T2("me", "ich", "je")
    }

    @Test
    fun testGetLabel(){
        //given
        val value = Testenum.TEST

        //then
        assertEquals("x", value.getLabelInLanguage(value, "x"))
        assertEquals("y", value.getLabelInLanguage(value, "y"))
    }

    @Test
    fun testGetLanguagesDynamic(){
        //then
        assertTrue(Testenum.TEST.getLanguagesDynamic().contains("x"))
        assertTrue(Testenum.TEST.getLanguagesDynamic().contains("y"))

        //then
        assertTrue(Labels.TEST.getLanguagesDynamic().contains("eng"))
        assertTrue(Labels.TEST.getLanguagesDynamic().contains("de"))
        assertFalse(Labels.TEST.getLanguagesDynamic().contains("fr"))
        assertEquals(listOf("eng", "de"),Labels.TEST.getLanguagesDynamic(), "fr is private!")
    }
}