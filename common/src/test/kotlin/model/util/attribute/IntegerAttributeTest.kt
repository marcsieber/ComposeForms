package model.util.attribute

import model.BaseFormModel
import model.util.Labels

internal class IntegerAttributeTest: NumberAttributeTest<Int>() {

    override fun provideAttribute(model: BaseFormModel, value: Int?): Attribute<*, Any,*> {
        return IntegerAttribute(model, value, Labels.TEST) as Attribute<*, Any,*>
    }
    override fun provideNumberAttribute(model: BaseFormModel, value: Int?): NumberAttribute<*, Int,*> {
        return IntegerAttribute(model, value, Labels.TEST)
    }

    init{
        validValue1Uneven        = 5
        validValue1AsText  = "5"

        validValue2        = 7
        validValue2AsText  = "7"

        validValue3        = 12
        validValue3AsText  = "12"

        validValue4        = 14
        validValue4AsText  = "14"

        notValidValueAsText     = "a"


        //For NumberAttribute
        upperBound                                      = 10
        lowerBoundWrong_becauseGreaterThanUpperBound    = 11
        lowerBoundCorrect   = 0

        lowerBound                                    = 10
        upperBoundWrong_becauseLowerThanLowerBound    = 9
        upperBoundCorrect                             = 14

        stepSizeCorrect_even            = 2
        stepSizeWrong_because0          = 0
        stepSizeWrong_becauseNegative   = -1

        notValidValueBecauseWrongStepAsText = "8"

        valueWithCorrectStepSize = (validValue1Uneven-stepSizeCorrect_even)
    }
}