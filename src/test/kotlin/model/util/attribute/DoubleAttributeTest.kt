package model.util.attribute

import model.BaseFormModel
import org.junit.jupiter.api.Assertions.*

internal class DoubleAttributeTest : NumberAttributeTest<Double>() {

    override fun provideAttribute(model: BaseFormModel, value: Double): Attribute<*, Any> {
        return DoubleAttribute(model, value) as Attribute<*, Any>
    }

    override fun provideNumberAttribute(model: BaseFormModel, value: Double): NumberAttribute<*, Double> {
        return DoubleAttribute(model, value)
    }

    init {
        validValue1 = 5.0
        validValue1AsText = "5"

        validValue2 = 7.0
        validValue2AsText = "7"

        validValue3 = 12.0
        validValue3AsText = "12"

        validValue4 = 14.0
        validValue4AsText = "14"

        notValidValueAsText = "a"
        validationMessage = "No Double"

        //For NumberAttribute
        upperBound = 10.0
        lowerBoundWrong1 = 11.0
        lowerBoundWrong2 = 10.0
        lowerBoundCorrect = 0.0

        lowerBound = 10.0
        upperBoundWrong1 = 9.0
        upperBoundWrong2 = 10.0
        upperBoundCorrect = 14.0

        stepSizeCorrect = 2.0
        stepSizeWrong1 = 0.0
        stepSizeWrong2 = -1.0

        notValidValueBecauseWrongStepAsText = "8"
    }
}