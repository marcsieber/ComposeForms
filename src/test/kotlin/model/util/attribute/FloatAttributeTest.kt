package model.util.attribute

import model.BaseFormModel
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class FloatAttributeTest : NumberAttributeTest<Float>() {

    override fun provideAttribute(model: BaseFormModel, value: Float): Attribute<*, Any> {
        return FloatAttribute(model, value) as Attribute<*, Any>
    }

    override fun provideNumberAttribute(model: BaseFormModel, value: Float): NumberAttribute<*, Float> {
        return FloatAttribute(model, value)
    }

    init {
        validValue1_uneven = 5.7f
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

        valueWithCorrectStepSize = (validValue1_uneven-stepSizeCorrect_even)

    }

    lateinit var floatAtr : FloatAttribute

    @BeforeEach
    fun setUpFloatAtr(){
        //given
        floatAtr = FloatAttribute(model, validValue1_uneven)
    }

    @Test
    fun testConvertComma(){
        //when
        floatAtr.setStepSize(0.1f)
        floatAtr.setValueAsText("6.3")

        //then
        assertEquals(0.1f, floatAtr.getStepSize())

        assertEquals("6.3", floatAtr.getValueAsText())

        // assertEquals(6.3, floatAtr.getValue()) //TODO: Fix rounding problems for float stepSize
    }
}