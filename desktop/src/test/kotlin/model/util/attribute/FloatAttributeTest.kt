/*
 *
 *   ========================LICENSE_START=================================
 *   Compose Forms
 *   %%
 *   Copyright (C) 2021 FHNW Technik
 *   %%
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *   =========================LICENSE_END==================================
 *
 */

package model.util.attribute

import model.BaseModel
import model.util.Labels
import model.validators.semanticValidators.FloatingPointValidator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

internal class FloatAttributeTest : NumberAttributeTest<Float>() {

    override fun provideAttribute(model: BaseModel, value: Float?): Attribute<*, Any,*> {
        return FloatAttribute(model, Labels.TEST, value) as Attribute<*, Any,*>
    }

    override fun provideNumberAttribute(model: BaseModel, value: Float?): NumberAttribute<*, Float,*> {
        return FloatAttribute(model, Labels.TEST, value)
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


        //For NumberAttribute
        upperBound = 10.0f
        lowerBoundWrong_becauseGreaterThanUpperBound = 11.0f
        lowerBoundCorrect = 0.0f

        lowerBound = 10.0f
        upperBoundWrong_becauseLowerThanLowerBound = 9.0f
        upperBoundCorrect = 14.0f

        stepSizeCorrect_even = 2.0f
        stepSizeWrong_because0 = 0.0f
        stepSizeWrong_becauseNegative = -1.0f

        notValidValueBecauseWrongStepAsText = "8"

        valueWithCorrectStepSize = (validValue1Uneven-stepSizeCorrect_even)

    }

    lateinit var floatAtr : FloatAttribute<Labels>

    @BeforeEach
    fun setUpFloatAtr(){
        //given
        floatAtr = FloatAttribute(model, Labels.TEST, validValue1Uneven)
    }

    @Test
    fun testFloatingPointValidator_DecimalPlaces(){

        //given
        val fpVal = FloatingPointValidator<Float>(8)
        floatAtr.addValidator(fpVal)

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
        floatAtr.setValueAsText("3.123")

        //then
        Assertions.assertFalse(floatAtr.isValid())
    }


//    @Test
//    fun testConvertComma(){
//        //when
//        floatAtr.setStepSize(0.1f)
//        floatAtr.setValueAsText("6.3")
//
//        //then
//        assertEquals(0.1f, floatAtr.getStepSize())
//
//        assertEquals("6.3", floatAtr.getValueAsText())
//
//         assertEquals(6.3f, floatAtr.getValue())
//    }

}