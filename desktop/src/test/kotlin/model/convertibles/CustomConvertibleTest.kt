package model.convertibles

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class CustomConvertibleTest {

    @Test
    fun convertUserInput_noGivenConvertibles() {
        //when
        val convertible = CustomConvertible(listOf())
        val valAsText = "Hallo"

        //then
        assertEquals(false, convertible.convertUserInput(valAsText).isConvertible)
        assertEquals("",    convertible.convertUserInput(valAsText).convertedValueAsText)
        assertEquals(true,  convertible.convertUserInput(valAsText).convertUserView)
        assertEquals(false, convertible.convertUserInput(valAsText).convertImmediately)

    }

    @Test
    fun convertUserInput_convertOnUnfocussing() {
        //when
        val convertible = CustomConvertible(listOf(
            ReplacementPair("(\\d*)(,)(\\d*)", "$1.$3")
        ))

        val valAsText = "1,3"

        //then
        assertEquals(true,  convertible.convertUserInput(valAsText).isConvertible)
        assertEquals("1.3", convertible.convertUserInput(valAsText).convertedValueAsText)
        assertEquals(true,  convertible.convertUserInput(valAsText).convertUserView)
        assertEquals(false, convertible.convertUserInput(valAsText).convertImmediately)

        assertEquals(false, convertible.convertUserInput("1,2,3").isConvertible)
    }

    @Test
    fun convertUserInput_convertImmediately() {
        //when
        val convertible = CustomConvertible(listOf(
            ReplacementPair("(\\d*)(,)(\\d*)", "$1.$3")
        ), convertImmediately = true)

        val valAsText = "1,3"

        //then
        assertEquals(true,  convertible.convertUserInput(valAsText).isConvertible)
        assertEquals("1.3", convertible.convertUserInput(valAsText).convertedValueAsText)
        assertEquals(true,  convertible.convertUserInput(valAsText).convertUserView)
        assertEquals(true,  convertible.convertUserInput(valAsText).convertImmediately)

        assertEquals(false, convertible.convertUserInput("1,2,3").isConvertible)
    }

    @Test
    fun convertUserInput_DontConvertUserView() {
        //when
        val convertible = CustomConvertible(listOf(
            ReplacementPair("(\\d*)(,)(\\d*)", "$1.$3")
        ), false)

        val valAsText = "1,3"

        //then
        assertEquals(true,  convertible.convertUserInput(valAsText).isConvertible)
        assertEquals("1.3", convertible.convertUserInput(valAsText).convertedValueAsText)
        assertEquals(false, convertible.convertUserInput(valAsText).convertUserView)
        assertEquals(false, convertible.convertUserInput(valAsText).convertImmediately)

        assertEquals(false, convertible.convertUserInput("1,2,3").isConvertible)
    }

}