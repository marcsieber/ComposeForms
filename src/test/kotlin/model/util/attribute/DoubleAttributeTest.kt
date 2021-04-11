package model.util.attribute

import model.BaseFormModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class DoubleAttributeTest : NumberAttributeTest<Double>() {

    override fun provideAttribute(model: BaseFormModel, value: Double): Attribute<*, Any> {
        return DoubleAttribute(model, value) as Attribute<*, Any>
    }

    override fun provideNumberAttribute(model: BaseFormModel, value: Double): NumberAttribute<*, Double> {
        return DoubleAttribute(model, value)
    }

    init {
        validValue1_uneven = 5.7
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

        valueWithCorrectStepSize = (validValue1_uneven-stepSizeCorrect_even)

    }

    lateinit var doubleAtr : DoubleAttribute

    @BeforeEach
    fun setUpDoubleAtr(){
        //given
        doubleAtr = DoubleAttribute(model, validValue1_uneven)
    }

    @Test
    fun testConvertComma(){
        //when
        doubleAtr.setStepSize(0.1)
        doubleAtr.setValueAsText("6.3")

        //then
        assertEquals(0.1, doubleAtr.getStepSize())

        assertEquals("6.3", doubleAtr.getValueAsText())

        // assertEquals(6.3, doubleAtr.getValue()) //TODO: Fix rounding problems for double stepSize
    }
}