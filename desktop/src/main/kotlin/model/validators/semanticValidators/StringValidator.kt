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

class StringValidator(private var minLength         : Int = 0,
                      private var maxLength         : Int = 1_000_000,
                      validationMessage             : String = "")

    : SemanticValidator<String>(validationMessage = validationMessage) {

    private var minLengthCache : Int?               = minLength
    private var maxLengthCache: Int?                = maxLength
    private var validationMessageCache : String?    = validationMessage

    init{
        init()
    }

    /**
     * This method can be used to overwrite a StringValidator that has already been set.
     * Only parameter that are not null will overwrite the old values.
     * CheckDevValues() is called to check if the parameters make sense. If yes the values are set.
     * The default validation message is adapted if no validation message has been set by the developer.
     * Finally the existing user inputs are checked again to see if they are still valid.
     *
     * @param minLength
     * @param maxLength
     * @param validationMessage
     */
    fun overrideStringValidator(minLength: Int? = null, maxLength: Int? = null, validationMessage: String? = null){
        if(minLength != null){
            this.minLengthCache = minLength
        }
        if(maxLength != null){
            this.maxLengthCache = maxLength
        }
        if(validationMessage != null){
            this.validationMessageCache = validationMessage
            validationMessageSetByDev = !validationMessage.equals("")
        }
        checkAndSetDevValues()
        setDefaultValidationMessage()
        attributes.forEach{it.revalidate()}
    }

    //******************************************************************************************************
    //Validation

    override fun validateUserInput(value: String?, valueAsText : String?): ValidationResult {
        val isValid = value!!.length in minLength..maxLength
        val rightTrackValid = value!!.length <= maxLength
        return ValidationResult(result = isValid, rightTrackResult = rightTrackValid, validationMessage = validationMessage)
    }

    override fun checkAndSetDevValues(){
        if(minLengthCache != null && minLengthCache!! < 0){
            deleteCaches()
            throw IllegalArgumentException("minLength must be positive")
        }
        if(maxLengthCache != null && maxLengthCache!! < 1){
            deleteCaches()
            throw IllegalArgumentException("maxLength must be at least 1")
        }
        if(    (minLengthCache != null && maxLengthCache != null && minLengthCache!! > maxLengthCache!!)
            || (minLengthCache != null && maxLengthCache == null && minLengthCache!! > maxLength)
            || (minLengthCache == null && maxLengthCache != null && minLength > maxLengthCache!!)){
            deleteCaches()
            throw IllegalArgumentException("minLength is greater than maxLength")
        }
        setValues()
        deleteCaches()
    }

    //******************************************************************************************************
    //Protected

    override fun setDefaultValidationMessage(){
        if(!validationMessageSetByDev){
            val ending = " characters."
            if(minLength == 0 && maxLength == 1_000_000){
                validationMessage = ""
            }
            else if(minLength == 0 && maxLength != 1_000_000){
                validationMessage = "The input must not contain more than " + maxLength + ending
            }
            else if(minLength != 0 && maxLength == 1_000_000){
                validationMessage = "The input must not contain less than " + minLength + ending
            }
            else if(minLength != 0 && maxLength != 1_000_000){
                validationMessage = "The input must contain between " + minLength + " and " + maxLength + ending
            }
        }
    }

    override fun setValues() {
        if(minLengthCache != null){
            this.minLength = minLengthCache!!
        }
        if(maxLengthCache != null){
            this.maxLength = maxLengthCache!!
        }
        if(validationMessageCache != null){
            this.validationMessage = validationMessageCache!!
        }
    }

    override fun deleteCaches() {
        this.minLengthCache = null
        this.maxLengthCache = null
        this.validationMessageCache = null
    }


    //******************************************************************************************************
    //Getter

    fun getMinLength() : Int {
        return minLength
    }

    fun getMaxLength() : Int {
        return maxLength
    }

}

