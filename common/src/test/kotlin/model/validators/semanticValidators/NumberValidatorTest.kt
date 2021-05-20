package model.validators.semanticValidators

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class NumberValidatorTest{


    @Test
    fun testDefaultValues(){
        //given
        val intValidator = NumberValidator<Int>(0)
        val doubleValidator = NumberValidator<Double>(0.0)


        //then
        assertEquals(Int.MAX_VALUE, intValidator.getUpperBound())
        assertEquals(Double.MAX_VALUE, doubleValidator.getUpperBound())


        //given
        val intValidator1 = NumberValidator<Int>(upperBound = 0)
        val doubleValidator1 = NumberValidator<Double>(upperBound = 0.0)
        assertEquals(Int.MIN_VALUE, intValidator1.getLowerBound())
        assertEquals(-Double.MAX_VALUE, doubleValidator1.getLowerBound())
    }

    @Test
    fun testBounds(){
        //given
        val intValidator = NumberValidator<Int>(3, 10)

        //then
        assertTrue(intValidator.validateUserInput(2, "2").rightTrackResult)
        assertFalse(intValidator.validateUserInput(2, "2").result)
        assertTrue(intValidator.validateUserInput(3, "3").rightTrackResult)
        assertTrue(intValidator.validateUserInput(3, "3").result)

        assertTrue(intValidator.validateUserInput(9, "9").rightTrackResult)
        assertTrue(intValidator.validateUserInput(9, "9").result)
        assertTrue(intValidator.validateUserInput(10, "10").rightTrackResult)
        assertTrue(intValidator.validateUserInput(10, "10").result)
        assertFalse(intValidator.validateUserInput(11, "11").rightTrackResult)
        assertFalse(intValidator.validateUserInput(11, "11").result)
    }


    @Test
    fun testSteps(){

        val intValidator = NumberValidator<Int>(stepStart = 0, stepSize = 2, onlyStepValuesAreValid = true)

        assertTrue(intValidator.validateUserInput(-4, "-4").result)
        assertTrue(intValidator.validateUserInput(-2, "-2").result)
        assertTrue(intValidator.validateUserInput(0, "0").result)
        assertTrue(intValidator.validateUserInput(2, "2").result)
        assertTrue(intValidator.validateUserInput(4, "4").result)

        assertFalse(intValidator.validateUserInput(3, "3").result)
        assertFalse(intValidator.validateUserInput(-3, "-3").result)

    }
}