package model.validators.semanticValidators

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

internal class FloatingPointValidatorTest{

    @Test
    fun testDecimalPlaces(){
        //given
        val floatingPointValidator = FloatingPointValidator<Double>(3)

        //then
        assertTrue(floatingPointValidator.validateUserInput(0.0, "0.0").result)
        assertTrue(floatingPointValidator.validateUserInput(0.1, "0.1").result)
        assertTrue(floatingPointValidator.validateUserInput(0.01, "0.01").result)
        assertTrue(floatingPointValidator.validateUserInput(0.001, "0.001").result)
        assertFalse(floatingPointValidator.validateUserInput(0.0001, "0.0001").result)

        assertTrue(floatingPointValidator.validateUserInput(0.0, "0.0").rightTrackResult)
        assertTrue(floatingPointValidator.validateUserInput(0.1, "0.1").rightTrackResult)
        assertTrue(floatingPointValidator.validateUserInput(0.01, "0.01").rightTrackResult)
        assertTrue(floatingPointValidator.validateUserInput(0.001, "0.001").rightTrackResult)
        assertFalse(floatingPointValidator.validateUserInput(0.0001, "0.0001").rightTrackResult)
    }


    @Test
    fun testDefaultValues(){
        //given
        val floatingPointValidator = FloatingPointValidator<Double>()

        //then
        assertEquals(10, floatingPointValidator.getDecimalPlaces())
        assertEquals("Too many decimal places", floatingPointValidator.validationMessage)

    }


    @Test
    fun testCheckAndSetDevValues(){
        //given
        val floatingPointValidator = FloatingPointValidator<Double>()

        //when
        assertThrows(IllegalArgumentException::class.java){
            floatingPointValidator.overrideSelectionValidator(-1)
        }

        assertThrows(IllegalArgumentException::class.java){
            floatingPointValidator.overrideSelectionValidator(-0)
        }
    }
}