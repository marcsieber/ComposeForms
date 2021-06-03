package model.convertables

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class CustomConvertableTest {

    @Test
    fun convertUserInput_noGivenConvertables() {
        //when
        val convertable = CustomConvertable(listOf())
        val valAsText = "Hallo"

        //then
        assertEquals(false, convertable.convertUserInput(valAsText).isConvertable)
        assertEquals("",    convertable.convertUserInput(valAsText).convertedValueAsText)
        assertEquals(true,  convertable.convertUserInput(valAsText).convertUserView)
        assertEquals(false, convertable.convertUserInput(valAsText).convertImmediately)

    }

    @Test
    fun convertUserInput_convertOnUnfocussing() {
        //when
        val convertable = CustomConvertable(listOf(
            ReplacementPair("(\\d*)(,)(\\d*)", "$1.$3")
        ))

        val valAsText = "1,3"

        //then
        assertEquals(true,  convertable.convertUserInput(valAsText).isConvertable)
        assertEquals("1.3", convertable.convertUserInput(valAsText).convertedValueAsText)
        assertEquals(true,  convertable.convertUserInput(valAsText).convertUserView)
        assertEquals(false, convertable.convertUserInput(valAsText).convertImmediately)

        assertEquals(false, convertable.convertUserInput("1,2,3").isConvertable)
    }

    @Test
    fun convertUserInput_convertImmediately() {
        //when
        val convertable = CustomConvertable(listOf(
            ReplacementPair("(\\d*)(,)(\\d*)", "$1.$3")
        ), convertImmediately = true)

        val valAsText = "1,3"

        //then
        assertEquals(true,  convertable.convertUserInput(valAsText).isConvertable)
        assertEquals("1.3", convertable.convertUserInput(valAsText).convertedValueAsText)
        assertEquals(true,  convertable.convertUserInput(valAsText).convertUserView)
        assertEquals(true,  convertable.convertUserInput(valAsText).convertImmediately)

        assertEquals(false, convertable.convertUserInput("1,2,3").isConvertable)
    }

    @Test
    fun convertUserInput_DontConvertUserView() {
        //when
        val convertable = CustomConvertable(listOf(
            ReplacementPair("(\\d*)(,)(\\d*)", "$1.$3")
        ), false)

        val valAsText = "1,3"

        //then
        assertEquals(true,  convertable.convertUserInput(valAsText).isConvertable)
        assertEquals("1.3", convertable.convertUserInput(valAsText).convertedValueAsText)
        assertEquals(false, convertable.convertUserInput(valAsText).convertUserView)
        assertEquals(false, convertable.convertUserInput(valAsText).convertImmediately)

        assertEquals(false, convertable.convertUserInput("1,2,3").isConvertable)
    }

}