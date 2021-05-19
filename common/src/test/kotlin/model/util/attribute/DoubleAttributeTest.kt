package model.util.attribute

import model.BaseFormModel
import model.util.Labels
import model.validators.semanticValidators.FloatingPointValidator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

internal class DoubleAttributeTest : NumberAttributeTest<Double>() {

    override fun provideAttribute(model: BaseFormModel, value: Double?): Attribute<*, Any,*> {
        return DoubleAttribute(model, value, Labels.TEST) as Attribute<*, Any,*>
    }

    override fun provideNumberAttribute(model: BaseFormModel, value: Double?): NumberAttribute<*, Double,*> {
        return DoubleAttribute(model, value, Labels.TEST)
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

        //For NumberAttribute
        upperBound = 10.0
        lowerBoundWrong_becauseGreaterThanUpperBound = 11.0
        lowerBoundCorrect = 0.0

        lowerBound = 10.0
        upperBoundWrong_becauseLowerThanLowerBound = 9.0
        upperBoundCorrect = 14.0

        stepSizeCorrect_even = 2.0
        stepSizeWrong_because0 = 0.0
        stepSizeWrong_becauseNegative = -1.0

        notValidValueBecauseWrongStepAsText = "8"

        valueWithCorrectStepSize = (validValue1Uneven-stepSizeCorrect_even)

    }

    lateinit var doubleAtr : DoubleAttribute<Labels>

    @BeforeEach
    fun setUpDoubleAtr(){
        //given
        doubleAtr = DoubleAttribute(model, validValue1Uneven, Labels.TEST)
    }

    @Test
    fun testFloatingPointValidator_DecimalPlaces(){

        //given
        val fpVal = FloatingPointValidator<Double>(8)
        doubleAtr.addValidator(fpVal)

        //then
        assertEquals(8, fpVal.getDecimalPlaces())

        //when
        fpVal.overrideSelectionValidator(6)

        //then
        assertEquals(6, fpVal.getDecimalPlaces())

        //then
        Assertions.assertThrows(IllegalArgumentException::class.java){
            //when
            fpVal.overrideSelectionValidator(0)
        }

        //when
        fpVal.overrideSelectionValidator(2)
        doubleAtr.setValueAsText("3.123")

        //then
        assertFalse(doubleAtr.isValid())
    }


//    @Test
//    fun testConvertComma(){
//        //when
//        doubleAtr.setStepSize(0.1)
//        doubleAtr.setValueAsText("6.3")
//
//        //then
//        assertEquals(0.1, doubleAtr.getStepSize())
//
//        assertEquals("6.3", doubleAtr.getValueAsText())
//
//        assertEquals(6.3, doubleAtr.getValue())
//    }
}