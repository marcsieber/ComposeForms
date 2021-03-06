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

import model.IModel
import convertibles.CustomConvertible
import model.meanings.Default
import model.meanings.SemanticMeaning
import model.util.ILabel
import util.Utilities
import model.validators.semanticValidators.SemanticValidator

/**
 * @author Louisa Reinger
 * @author Steve Vogel
 */
class SelectionAttribute<L>(//required parameters
    model                              : IModel,
    label                              : L,
    possibleSelections                 : Set<String>,

                            //required parameters
    value                              : Set<String>                            = emptySet<String>(),
    required                           : Boolean                                = false,
    readOnly                           : Boolean                                = false,
    onChangeListeners                  : List<(Attribute<*,*,*>) -> Unit>           = emptyList(),
    validators                         : List<SemanticValidator<Set<String>>>   = mutableListOf(),
    convertibles                       : List<CustomConvertible>                = emptyList(),
    meaning                            : SemanticMeaning<Set<String>>           = Default()

) : Attribute<SelectionAttribute<L>, Set<String>, L>(model = model, value = value, label = label, required = required, readOnly = readOnly,
    onChangeListeners = onChangeListeners, validators = validators, convertibles = convertibles, meaning = meaning)
        where L: Enum<*>, L : ILabel{

    override val typeT: Set<String>
        get() = setOf("0")

    //******************************************************************************************************
    //Properties

    private val possibleSelections : MutableSet<String> = possibleSelections.toMutableSet()

    //******************************************************************************************************
    //Functions that are called on user actions

    /**
     * This function checks if the value is in the set of possible selections.
     * If yes, it creates a new user set containing all the values already selected by the user plus the new value.
     * The newly formed set is then passed to the setValueAsText function.
     * @param value : String
     */
    fun addUserSelection(value: String){
        if(possibleSelections.contains(value)){
            var newSet = Utilities<Set<String>>().stringToSetConverter(getValueAsText())
            newSet = newSet.toMutableSet()
            newSet.add(value)
            setValueAsText(newSet.toString())
        }
        else {
            setValueAsText(getValue().toString())
            throw IllegalArgumentException("There was no such selection to choose")
        }
    }


    /**
     * This function creates a new user set containing all the values already selected by the user minus the new value.
     * The newly formed set is then passed to the setValueAsText function.
     * @param value : String
     */
    fun removeUserSelection(value: String){
        val newSet : MutableSet<String> = Utilities<Set<String>>().stringToSetConverter(getValueAsText()).toMutableSet()
        newSet.remove(value)
        setValueAsText(newSet.toString())
    }

    //******************************************************************************************************
    //Setter

    /**
     * This method checks if the set of selections is not empty.
     * Further the attribute's validators are checked if there are limits like minNumberOfSelections that make no sense anymore.
     * If so, the possibleSelections-set is set and the current textValue is checked to see if it is still valid.
     *
     * @param selections : Int
     * @throws IllegalArgumentException
     */
    fun setPossibleSelections(selections : Set<String>){
        if(selections.isNotEmpty()){
            this.possibleSelections.clear()
            this.possibleSelections.addAll(selections)
            validators.forEach{it.checkAndSetDevValues()}
            checkAndSetValue(getValue().toString())
        }else{
            throw IllegalArgumentException("There are no selections in the set")
        }
    }

    /**
     * This method adds a new selection to the possibleSelections-set.
     * @param selection : String
     */
    fun addANewPossibleSelection(selection: String){
        this.possibleSelections.add(selection)
    }

    /**
     * This method deletes a selection of the possibleSelections-set.
     * The attribute's validators are checked if there are limits like minNumberOfSelections that make no sense anymore.
     * It is also checked whether this element is already selected by the user.
     * If so, it is removed from the user value list and checked whether the newly created user value list is still valid.
     *
     * @param selection : String
     */
    fun removeAPossibleSelection(selection: String){
        if(possibleSelections.contains(selection)){
            this.possibleSelections.remove(selection)
            validators.forEach{it.checkAndSetDevValues()}
            if(getValue()!!.contains(selection)){
                removeUserSelection(selection)
                checkAndSetValue(getValue().toString())
            }
        }
    }

    //******************************************************************************************************
    //Public Getter

    override fun getPossibleSelections() : Set<String> {
        return possibleSelections
    }

}