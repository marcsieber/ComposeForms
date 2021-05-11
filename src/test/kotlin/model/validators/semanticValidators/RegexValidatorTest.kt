package model.validators.semanticValidators

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class RegexValidatorTest {

    @Test
    fun overrideRegexValidator() {
        //given
        val pattern = "AbC"
        val regexValidator = RegexValidator<String>(pattern)

        //when
        val overWritePattern = "Test"
        regexValidator.overrideRegexValidator(overWritePattern)

        //then
        assertEquals(overWritePattern, regexValidator.getRegexPattern())


        //given
        val pattern1 = "AbC"
        val pattern2 = "hallo"
        val regexValidator1 = RegexValidator<String>(pattern1, pattern2)

        //when
        val overWritePattern1 = "Test"
        regexValidator1.overrideRegexValidator(overWritePattern1)

        //then
        assertEquals(overWritePattern1, regexValidator1.getRegexPattern())
    }

    @Test
    fun validateUserInput() {
        //given
        val pattern = "AbC"
        val regexValidator = RegexValidator<String>(pattern)

        //then
        assertTrue(regexValidator.validateUserInput(null, "AbC").result)
        assertTrue(regexValidator.validateUserInput(null, "AbC").rightTrackResult)
        assertFalse(regexValidator.validateUserInput(null, "abc").result)
        assertFalse(regexValidator.validateUserInput(null, "").result)
        assertFalse(regexValidator.validateUserInput(null, "ABC").result)
        assertFalse(regexValidator.validateUserInput(null, "abcabc").result)
        assertEquals(regexValidator.validateUserInput(null, "hallo").validationMessage, "Pattern does not match to AbC")
    }

    @Test
    fun validateUserInputRightTrack(){
        val pattern = "AbC"
        val rightTrackPattern = "Ab"
        val regexValidator = RegexValidator<String>(pattern, rightTrackPattern)

        //then
        assertFalse(regexValidator.validateUserInput(null, "Ab").result)
        assertTrue(regexValidator.validateUserInput(null, "Ab").rightTrackResult)
    }

    @Test
    fun getRegexPattern() {
        //given
        val pattern = "AbC"
        val regexValidator = RegexValidator<String>(pattern)

        //then
        assertEquals(pattern, regexValidator.getRegexPattern())

        //given
        val pattern1 = "AbC"
        val pattern2 = "hallo"
        val regexValidator1 = RegexValidator<String>(pattern1, pattern2)

        //then
        assertEquals(pattern1, regexValidator1.getRegexPattern())

    }
}