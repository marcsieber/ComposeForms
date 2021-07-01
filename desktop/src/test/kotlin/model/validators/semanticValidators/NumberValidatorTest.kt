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

package model.validators.semanticValidators

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class NumberValidatorTest{


    @Test
    fun testDefaultValues(){
        //given
        val intValidator = NumberValidator<Int>(0)
        val doubleValidator = NumberValidator<Double>(0.0)


        //then
        assertEquals(Int.MAX_VALUE, intValidator.getUpperBound())
        assertEquals(Double.MAX_VALUE, doubleValidator.getUpperBound())


        //given
        val intValidator1 = NumberValidator<Int>(upperBound = 0)
        val doubleValidator1 = NumberValidator<Double>(upperBound = 0.0)
        assertEquals(Int.MIN_VALUE, intValidator1.getLowerBound())
        assertEquals(-Double.MAX_VALUE, doubleValidator1.getLowerBound())
    }

    @Test
    fun testBounds(){
        //given
        val intValidator = NumberValidator<Int>(3, 10)

        //then
        assertTrue(intValidator.validateUserInput(2, "2").rightTrackResult)
        assertFalse(intValidator.validateUserInput(2, "2").result)
        assertTrue(intValidator.validateUserInput(3, "3").rightTrackResult)
        assertTrue(intValidator.validateUserInput(3, "3").result)

        assertTrue(intValidator.validateUserInput(9, "9").rightTrackResult)
        assertTrue(intValidator.validateUserInput(9, "9").result)
        assertTrue(intValidator.validateUserInput(10, "10").rightTrackResult)
        assertTrue(intValidator.validateUserInput(10, "10").result)
        assertFalse(intValidator.validateUserInput(11, "11").rightTrackResult)
        assertFalse(intValidator.validateUserInput(11, "11").result)
    }


    @Test
    fun testSteps(){

        val intValidator = NumberValidator<Int>(stepStart = 0, stepSize = 2, onlyStepValuesAreValid = true)

        assertTrue(intValidator.validateUserInput(-4, "-4").result)
        assertTrue(intValidator.validateUserInput(-2, "-2").result)
        assertTrue(intValidator.validateUserInput(0, "0").result)
        assertTrue(intValidator.validateUserInput(2, "2").result)
        assertTrue(intValidator.validateUserInput(4, "4").result)

        assertFalse(intValidator.validateUserInput(3, "3").result)
        assertFalse(intValidator.validateUserInput(-3, "-3").result)

    }
}