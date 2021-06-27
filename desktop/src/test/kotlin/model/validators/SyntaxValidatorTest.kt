package model.validators

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class SyntaxValidatorTest{

    @Test
    fun testSyntaxValidatorForDifferentTypes(){
        //given
        val intSyntaxValidator = SyntaxValidator<Int>()

        //then
        assertFalse(intSyntaxValidator.validateUserInput(0, "14.3").result)
        assertFalse(intSyntaxValidator.validateUserInput(0, "").result)
        assertTrue(intSyntaxValidator.validateUserInput(0, "14").result)
        assertTrue(intSyntaxValidator.validateUserInput(0, "-14").result)


        //given
        val stringSyntaxValidator = SyntaxValidator<String>()

        //then
        assertTrue(stringSyntaxValidator.validateUserInput("", "Test").result)
        assertTrue(stringSyntaxValidator.validateUserInput("", "").result)
        assertTrue(stringSyntaxValidator.validateUserInput("", "14").result)
        assertTrue(stringSyntaxValidator.validateUserInput("", "-14").result)

        //given
        val doubleSyntaxValidator = SyntaxValidator<Double>()

        //then
        assertTrue(doubleSyntaxValidator.validateUserInput(0.0, "4.23").result)
        assertTrue(doubleSyntaxValidator.validateUserInput(0.0, "-4.12").result)
        assertTrue(doubleSyntaxValidator.validateUserInput(0.0, "+14").result)
        assertFalse(doubleSyntaxValidator.validateUserInput(0.0, "").result)
        assertFalse(doubleSyntaxValidator.validateUserInput(0.0, "True").result)

    }

    @Test
    fun testValidationMessage(){
        //when
        val text = "Falsche Eingabe getätigt"
        val syntaxValidator = SyntaxValidator<Int>(text)

        //then
        assertEquals(text, syntaxValidator.validationMessage)
        assertEquals(text, syntaxValidator.validateUserInput(0, "ABC").validationMessage)

        //when
        val defaultMsg = "This is not the correct input type"
        val defaultMsgSyntaxValidator = SyntaxValidator<Int>()

        //then
        assertEquals(defaultMsg, defaultMsgSyntaxValidator.validationMessage)
        assertEquals(defaultMsg, defaultMsgSyntaxValidator.validateUserInput(0, "ABC").validationMessage)

    }

}