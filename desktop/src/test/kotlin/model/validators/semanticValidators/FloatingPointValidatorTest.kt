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
import java.lang.IllegalArgumentException

/**
 * @author Louisa Reinger
 * @author Steve Vogel
 */
internal class FloatingPointValidatorTest{

    @Test
    fun testDecimalPlaces(){
        //given
        val floatingPointValidator = FloatingPointValidator<Double>(3)

        //then
        assertTrue(floatingPointValidator.validateUserInput(0.0, "0.0").result)
        assertTrue(floatingPointValidator.validateUserInput(0.1, "0.1").result)
        assertTrue(floatingPointValidator.validateUserInput(0.01, "0.01").result)
        assertTrue(floatingPointValidator.validateUserInput(0.001, "0.001").result)
        assertFalse(floatingPointValidator.validateUserInput(0.0001, "0.0001").result)

        assertTrue(floatingPointValidator.validateUserInput(0.0, "0.0").rightTrackResult)
        assertTrue(floatingPointValidator.validateUserInput(0.1, "0.1").rightTrackResult)
        assertTrue(floatingPointValidator.validateUserInput(0.01, "0.01").rightTrackResult)
        assertTrue(floatingPointValidator.validateUserInput(0.001, "0.001").rightTrackResult)
        assertFalse(floatingPointValidator.validateUserInput(0.0001, "0.0001").rightTrackResult)
    }


    @Test
    fun testDefaultValues(){
        //given
        val floatingPointValidator = FloatingPointValidator<Double>()

        //then
        assertEquals(10, floatingPointValidator.getDecimalPlaces())
        assertEquals("Too many decimal places", floatingPointValidator.validationMessage)

    }


    @Test
    fun testCheckAndSetDevValues(){
        //given
        val floatingPointValidator = FloatingPointValidator<Double>()

        //when
        assertThrows(IllegalArgumentException::class.java){
            floatingPointValidator.overrideSelectionValidator(-1)
        }

        assertThrows(IllegalArgumentException::class.java){
            floatingPointValidator.overrideSelectionValidator(-0)
        }
    }
}