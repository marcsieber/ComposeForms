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
        val msg1 = "Changed msg"

        //when
        val cv = CustomValidator<String>(f, validationMessage = msg)
        cv.overrideCustomValidator(f2)

        //then
        assertTrue(cv.validateUserInput("Hallo", "Hallo").result)
        assertTrue(cv.validateUserInput("Hallo", "Hallo").rightTrackResult)
        assertTrue(cv.validateUserInput("Ha", "Ha").result)
        assertTrue(cv.validateUserInput("Ha", "Ha").rightTrackResult)

        cv.overrideCustomValidator(null, validationMessage = msg1)

        //then
        assertTrue(cv.validateUserInput("Hallo", "Hallo").result)
        assertTrue(cv.validateUserInput("Hallo", "Hallo").rightTrackResult)
        assertTrue(cv.validateUserInput("Ha", "Ha").result)
        assertTrue(cv.validateUserInput("Ha", "Ha").rightTrackResult)
        assertEquals(msg1, cv.validateUserInput("H", "H").validationMessage)
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


        //when
        val cv2 = CustomValidator<String>(f, null, msg)

        //then
        assertTrue(cv2.validateUserInput("Hallo", "Hallo").result)
        assertTrue(cv2.validateUserInput("Hallo", "Hallo").rightTrackResult)
        assertFalse(cv2.validateUserInput("Ha", "Ha").result)
        assertFalse(cv2.validateUserInput("Ha", "Ha").rightTrackResult)

        //given
        val f3 : (String?) -> Boolean = {it!!.length > 1}

        //when
        val cv3 = CustomValidator<String>(f, f3,validationMessage = msg)

        //then
        assertTrue(cv3.validateUserInput("Hallo", "Hallo").result)
        assertTrue(cv3.validateUserInput("Hallo", "Hallo").rightTrackResult)
        assertFalse(cv3.validateUserInput("Ha", "Ha").result)
        assertTrue(cv3.validateUserInput("Ha", "Ha").rightTrackResult)


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