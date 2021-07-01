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

internal class SelectionValidatorTest{


    @Test
    fun testSelectionValidatorDefault(){
        //given
        val selectionValidator = SelectionValidator()

        //then
        assertTrue(selectionValidator.validateUserInput(emptySet(), "").result)
        assertTrue(selectionValidator.validateUserInput(emptySet(), "").rightTrackResult)
        assertTrue(selectionValidator.validateUserInput(emptySet(), "[]").result)
        assertTrue(selectionValidator.validateUserInput(emptySet(), "[]").rightTrackResult)

        assertTrue(selectionValidator.validateUserInput(setOf("Hallo"), "[Hallo]").result)
        assertTrue(selectionValidator.validateUserInput(setOf("Hallo"), "[Hallo]").rightTrackResult)
    }

    @Test
    fun testOverrideSelectionValidator(){
        //given
        val selectionValidator = SelectionValidator()
        val set = setOf("Entry1", "Entry2", "Entry3")

        //when
        selectionValidator.overrideSelectionValidator(2)

        //then
        assertTrue(selectionValidator.validateUserInput(set, set.toString()).result)
        assertTrue(selectionValidator.validateUserInput(set, set.toString()).rightTrackResult)

        //when
        selectionValidator.overrideSelectionValidator(0, 2)

        //then
        assertFalse(selectionValidator.validateUserInput(set, set.toString()).result)
        assertFalse(selectionValidator.validateUserInput(set, set.toString()).rightTrackResult)


        //when
        selectionValidator.overrideSelectionValidator(maxNumberOfSelections = 4)

        //then
        assertTrue(selectionValidator.validateUserInput(set, set.toString()).result)
        assertTrue(selectionValidator.validateUserInput(set, set.toString()).rightTrackResult)


        //when
        selectionValidator.overrideSelectionValidator(maxNumberOfSelections = 2, validationMessage = "Own message")
        //then
        assertEquals("Own message", selectionValidator.validateUserInput(set, set.toString()).validationMessage)

    }

    @Test
    fun testOverrideSelectionValidatorNonValid(){
        //given
        val selectionValidator = SelectionValidator()

        //when
        assertThrows(IllegalArgumentException::class.java){
            selectionValidator.overrideSelectionValidator(10,2)
        }

        //then
        assertEquals(0, selectionValidator.getMinNumberOfSelections())
        assertEquals(Int.MAX_VALUE, selectionValidator.getMaxNumberOfSelections())


        //when
        assertThrows(IllegalArgumentException::class.java){
            selectionValidator.overrideSelectionValidator(-1)
        }

        //then
        assertEquals(0, selectionValidator.getMinNumberOfSelections())
        assertEquals(Int.MAX_VALUE, selectionValidator.getMaxNumberOfSelections())


        //when
        assertThrows(IllegalArgumentException::class.java){
            selectionValidator.overrideSelectionValidator(10)
            selectionValidator.overrideSelectionValidator(maxNumberOfSelections = 5)
        }

        //then
        assertEquals(10, selectionValidator.getMinNumberOfSelections())
        assertEquals(Int.MAX_VALUE, selectionValidator.getMaxNumberOfSelections())

        //reset
        selectionValidator.overrideSelectionValidator(0)

        //when
        assertThrows(IllegalArgumentException::class.java){
            selectionValidator.overrideSelectionValidator(maxNumberOfSelections = 10)
            selectionValidator.overrideSelectionValidator(minNumberOfSelections = 15)
        }

        //then
        assertEquals(0, selectionValidator.getMinNumberOfSelections())
        assertEquals(10, selectionValidator.getMaxNumberOfSelections())
    }

    @Test
    fun testEmptyOverride(){
        //given
        val selectionValidator = SelectionValidator()

        //when
        selectionValidator.overrideSelectionValidator()

        //then
        assertEquals(0, selectionValidator.getMinNumberOfSelections())
        assertEquals(Int.MAX_VALUE, selectionValidator.getMaxNumberOfSelections())
    }
}