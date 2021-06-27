package model.validators.semanticValidators

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class StringValidatorTest{

    @Test
    fun testStringValidation(){
        //given
        val validator = StringValidator(minLength = 3, maxLength = 5)
        //then
        assertFalse(validator.validateUserInput("", "").result)
        assertFalse(validator.validateUserInput("12", "12").result)
        assertTrue(validator.validateUserInput("123", "123").result)
        assertTrue(validator.validateUserInput("1234", "1234").result)
        assertTrue(validator.validateUserInput("12345", "12345").result)
        assertFalse(validator.validateUserInput("123456", "123456").result)
    }


    @Test
    fun testOverrideValidator(){
        //given
        val validator = StringValidator()

        //when
        validator.overrideStringValidator(minLength = 3)
        //then
        assertFalse(validator.validateUserInput("12", "12").result)
        assertTrue(validator.validateUserInput("123", "123").result)
        assertTrue(validator.validateUserInput("1234", "1234").result)

        //when
        validator.overrideStringValidator(maxLength = 5)

        //then
        assertFalse(validator.validateUserInput("", "").result)
        assertFalse(validator.validateUserInput("12", "12").result)
        assertTrue(validator.validateUserInput("123", "123").result)
        assertTrue(validator.validateUserInput("1234", "1234").result)
        assertTrue(validator.validateUserInput("12345", "12345").result)
        assertFalse(validator.validateUserInput("123456", "123456").result)

    }


    @Test
    fun testGetter(){
        //given
        val validator = StringValidator()

        //then
        assertEquals(0, validator.getMinLength())
        assertEquals(1_000_000, validator.getMaxLength())


        //when
        validator.overrideStringValidator(2,4)

        //then
        assertEquals(2, validator.getMinLength())
        assertEquals(4, validator.getMaxLength())
    }


}