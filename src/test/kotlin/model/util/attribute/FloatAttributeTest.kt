package model.util.attribute

import model.BaseFormModel
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class FloatAttributeTest : NumberAttributeTest<Float>() {

    override fun provideAttribute(model: BaseFormModel, value: Float?): Attribute<*, Any> {
        return FloatAttribute(model, value) as Attribute<*, Any>
    }

    override fun provideNumberAttribute(model: BaseFormModel, value: Float?): NumberAttribute<*, Float> {
        return FloatAttribute(model, value)
    }

    init {
        validValue1Uneven = 5.7f
        validValue1AsText = "5.7"

        validValue2 = 7.7f
        validValue2AsText = "7.7"

        validValue3 = 12.7f
        validValue3AsText = "12.7"

        validValue4 = 14.7f
        validValue4AsText = "14.7"

        notValidValueAsText = "a"
        validationMessage = "No Float"

        //For NumberAttribute
        upperBound = 10.0f
        lowerBoundWrong_becauseGreaterThanUpperBound = 11.0f
        lowerBoundWrong_becauseSameAsUpperBound = 10.0f
        lowerBoundCorrect = 0.0f

        lowerBound = 10.0f
        upperBoundWrong_becauseLowerThanLowerBound = 9.0f
        upperBoundWrong_becauseSameAsLowerBound = 10.0f
        upperBoundCorrect = 14.0f

        stepSizeCorrect_even = 2.0f
        stepSizeWrong_because0 = 0.0f
        stepSizeWrong_becauseNegative = -1.0f

        notValidValueBecauseWrongStepAsText = "8"

        valueWithCorrectStepSize = (validValue1Uneven-stepSizeCorrect_even)

    }

    lateinit var floatAtr : FloatAttribute

    @BeforeEach
    fun setUpFloatAtr(){
        //given
        floatAtr = FloatAttribute(model, validValue1Uneven)
    }

    @Test
    fun testConvertComma(){
        //when
        floatAtr.setStepSize(0.1f)
        floatAtr.setValueAsText("6.3")

        //then
        assertEquals(0.1f, floatAtr.getStepSize())

        assertEquals("6.3", floatAtr.getValueAsText())

         assertEquals(6.3f, floatAtr.getValue())
    }


    @Test
    fun testSetDecimalPlaces(){
        //then
        assertEquals(8, floatAtr.getDecimalPlaces())

        //when
        floatAtr.setDecimalPlaces(6)

        //then
        assertEquals(6, floatAtr.getDecimalPlaces())

        //then
        Assertions.assertThrows(IllegalArgumentException::class.java){
            //when
            floatAtr.setDecimalPlaces(0)
        }
    }

    @Test
    fun testGetDecimalPlaces(){
        //when
        floatAtr.setDecimalPlaces(4)

        //then
        assertEquals(floatAtr.getDecimalPlaces(), 4)

        //then
        Assertions.assertThrows(IllegalArgumentException::class.java){
            //when
            floatAtr.setDecimalPlaces(-1)
        }

        //then
        assertEquals(floatAtr.getDecimalPlaces(), 4)
    }

    @Test
    fun testCheckDecimalPlaces(){
        //when
        floatAtr.setValueAsText("4.1234567")

        //then
        assertEquals(4.1234567f, floatAtr.getValue())

        //when
        floatAtr.setDecimalPlaces(6)

        //then
        Assertions.assertFalse(floatAtr.isValid())
        assertEquals("Too many decimal places", floatAtr.getValidationMessage())
    }
}