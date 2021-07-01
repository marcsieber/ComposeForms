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

import model.validators.ValidationResult

/**
 * @author Louisa Reinger
 * @author Steve Vogel
 */
open class CustomValidator<T>(  private var validationFunction  : (T?) -> Boolean,
                                private var rightTrackFunction  : ((T?) -> Boolean) ? = null,
                                validationMessage               : String)

    : SemanticValidator<T>(validationMessage = validationMessage) {


    /**
     * This method can be used to overwrite a CustomValidator that has already been set.
     * Only values that are not null will overwrite old values.
     * The existing user inputs are checked again to see if they are still valid.
     *
     * @param decimalPlaces
     * @param validationMessage
     */
    fun overrideCustomValidator(validationFunction: ((T?) -> Boolean)? = null, validationMessage: String? = null){
        if(validationFunction != null){
            this.validationFunction = validationFunction
        }
        if(validationMessage != null){
            this.validationMessage = validationMessage
        }
        attributes.forEach{it.revalidate()}
    }

    //******************************************************************************************************
    //Validation

    override fun validateUserInput(value: T?, valueAsText : String?): ValidationResult {
        val res = validationFunction(value)
        val rightTrackValid = if(rightTrackFunction != null) rightTrackFunction!!(value) else res
        return ValidationResult(res, rightTrackValid, validationMessage)
    }


    override fun checkAndSetDevValues(){
        //no implementation in CustomValidator because we can't find out what the developer wants
    }

    //******************************************************************************************************
    //Exceptions & validation messages

    override fun setDefaultValidationMessage(){
        //no implementation in CustomValidator because we can't find out what the developer wants
    }

    override fun setValues() {
        //no implementation in CustomValidator because we do not use cache on CustomValidator
    }

    override fun deleteCaches() {
        //no implementation in CustomValidator because we do not use cache on CustomValidator
    }


    //******************************************************************************************************
    //Getter

    fun getValidationFunction() : (T?) -> Boolean {
        return validationFunction
    }

}