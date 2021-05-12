package model.validators.semanticValidators

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class CustomValidatorTest {

    @Test
    fun overrideCustomValidator() {
        //given
        val f : (String?) -> Boolean = {it!!.length > 3}
        val f2: (String?) -> Boolean = {it!!.length > 1}
        val msg = "Lenght must be at least 4"

        //when
        val cv = CustomValidator<String>(f, validationMessage = msg)
        cv.overrideCustomValidator(f2)

        //then
        assertTrue(cv.validateUserInput("Hallo", "Hallo").result)
        assertTrue(cv.validateUserInput("Hallo", "Hallo").rightTrackResult)
        assertTrue(cv.validateUserInput("Ha", "Ha").result)
        assertTrue(cv.validateUserInput("Ha", "Ha").rightTrackResult)

    }

    @Test
    fun validateUserInput() {
        //given
        val f : (String?) -> Boolean = {it!!.length > 3}
        val msg = "Lenght must be at least 4"

        //when
        val cv = CustomValidator<String>(f, validationMessage = msg)

        //then
        assertTrue(cv.validateUserInput("Hallo", "Hallo").result)
        assertTrue(cv.validateUserInput("Hallo", "Hallo").rightTrackResult)
        assertFalse(cv.validateUserInput("Ha", "Ha").result)
        assertFalse(cv.validateUserInput("Ha", "Ha").rightTrackResult)


        //given
        val f2 : (String?) -> Boolean = {it!!.length > 1}

        //when
        val cv1 = CustomValidator<String>(f, f2,validationMessage = msg)

        //then
        assertTrue(cv1.validateUserInput("Hallo", "Hallo").result)
        assertTrue(cv1.validateUserInput("Hallo", "Hallo").rightTrackResult)
        assertFalse(cv1.validateUserInput("Ha", "Ha").result)
        assertTrue(cv1.validateUserInput("Ha", "Ha").rightTrackResult)


    }

    @Test
    fun getValidationFunction() {
        //given
        val f : (String?) -> Boolean = {it!!.length > 3}
        val msg = "Lenght must be at least 4"

        //when
        val cv = CustomValidator<String>(f, validationMessage = msg)

        //then
        assertEquals(f, cv.getValidationFunction())
        assertEquals(msg, cv.validationMessage)
    }
}