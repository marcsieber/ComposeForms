package model.util.attribute

import model.BaseFormModel

internal class ShortAttributeTest : NumberAttributeTest<Short>(){

    override fun provideAttribute(model: BaseFormModel, value: Short): Attribute<*, Any> {
        return ShortAttribute(model, value) as Attribute<*, Any>
    }
    override fun provideNumberAttribute(model: BaseFormModel, value: Short): NumberAttribute<*, Short> {
        return ShortAttribute(model, value)
    }

    init{
        validValue1        = 5
        validValue1AsText  = "5"

        validValue2        = 7
        validValue2AsText  = "7"

        validValue3        = 12
        validValue3AsText  = "12"

        validValue4        = 14
        validValue4AsText  = "14"

        notValidValueAsText     = "a"
        validationMessage       = "No Short"

        //For NumberAttribute
        upperBound          = 10
        lowerBoundWrong1    = 11
        lowerBoundWrong2    = 10
        lowerBoundCorrect   = 0

        lowerBound          = 10
        upperBoundWrong1    = 9
        upperBoundWrong2    = 10
        upperBoundCorrect   = 14

        stepSizeCorrect = 2
        stepSizeWrong1   = 0
        stepSizeWrong2   = -1

        notValidValueBecauseWrongStepAsText = "8"
    }
}