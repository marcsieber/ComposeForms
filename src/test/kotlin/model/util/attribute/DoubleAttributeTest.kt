package model.util.attribute

import model.BaseFormModel
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.NumberFormatException

internal class DoubleAttributeTest : NumberAttributeTest<Double>() {

    override fun provideAttribute(model: BaseFormModel, value: Double?): Attribute<*, Any> {
        return DoubleAttribute(model, value) as Attribute<*, Any>
    }

    override fun provideNumberAttribute(model: BaseFormModel, value: Double?): NumberAttribute<*, Double> {
        return DoubleAttribute(model, value)
    }

    init {
        validValue1Uneven = 5.7
        validValue1AsText = "5.7"

        validValue2 = 7.7
        validValue2AsText = "7.7"

        validValue3 = 12.7
        validValue3AsText = "12.7"

        validValue4 = 14.7
        validValue4AsText = "14.7"

        notValidValueAsText = "a"
        validationMessage = "No Double"

        //For NumberAttribute
        upperBound = 10.0
        lowerBoundWrong_becauseGreaterThanUpperBound = 11.0
        lowerBoundWrong_becauseSameAsUpperBound = 10.0
        lowerBoundCorrect = 0.0

        lowerBound = 10.0
        upperBoundWrong_becauseLowerThanLowerBound = 9.0
        upperBoundWrong_becauseSameAsLowerBound = 10.0
        upperBoundCorrect = 14.0

        stepSizeCorrect_even = 2.0
        stepSizeWrong_because0 = 0.0
        stepSizeWrong_becauseNegative = -1.0

        notValidValueBecauseWrongStepAsText = "8"

        valueWithCorrectStepSize = (validValue1Uneven-stepSizeCorrect_even)

    }

    lateinit var doubleAtr : DoubleAttribute

    @BeforeEach
    fun setUpDoubleAtr(){
        //given
        doubleAtr = DoubleAttribute(model, validValue1Uneven)
    }

    @Test
    fun testConvertComma(){
        //when
        doubleAtr.setStepSize(0.1)
        doubleAtr.setValueAsText("6.3")

        //then
        assertEquals(0.1, doubleAtr.getStepSize())

        assertEquals("6.3", doubleAtr.getValueAsText())

        assertEquals(6.3, doubleAtr.getValue())
    }

    @Test
    fun testSetDecimalPlaces(){
        //then
        assertEquals(8, doubleAtr.getDecimalPlaces())

        //when
        doubleAtr.setDecimalPlaces(6)

        //then
        assertEquals(6, doubleAtr.getDecimalPlaces())

        //then
        Assertions.assertThrows(IllegalArgumentException::class.java){
            //when
            doubleAtr.setDecimalPlaces(0)
        }
    }

    @Test
    fun testGetDecimalPlaces(){
        //when
        doubleAtr.setDecimalPlaces(4)

        //then
        assertEquals(doubleAtr.getDecimalPlaces(), 4)

        //then
        Assertions.assertThrows(IllegalArgumentException::class.java){
            //when
            doubleAtr.setDecimalPlaces(-1)
        }

        //then
        assertEquals(doubleAtr.getDecimalPlaces(), 4)
    }

    @Test
    fun testCheckDecimalPlaces(){
        //when
        doubleAtr.setValueAsText("4.1234567")

        //then
        assertEquals(4.1234567, doubleAtr.getValue())

        //when
        doubleAtr.setDecimalPlaces(6)

        //then
        assertFalse(doubleAtr.isValid())
        assertEquals("Too many decimal places", doubleAtr.getValidationMessage())
    }
}