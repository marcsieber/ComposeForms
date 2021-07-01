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

class RegexValidator<T>(private var regexPattern          : String,
                        private var rightTrackRegexPattern: String? = null,
                        validationMessage                 : String = "")

    : SemanticValidator<T>(validationMessage = validationMessage) {

    init{
        init()
    }

    /**
     * Overrides the properties with the given non null values
     *
     * @param regexPattern
     * @param rightTrackRegexPattern
     * @param validationMessage
     */
    fun overrideRegexValidator(regexPattern: String? = null, rightTrackRegexPattern: String? = null, validationMessage: String? = null){
        if(regexPattern != null){
            this.regexPattern = regexPattern
        }
        if(rightTrackRegexPattern != null){
            this.rightTrackRegexPattern = rightTrackRegexPattern
        }
        if(validationMessage != null){
            this.validationMessage = validationMessage
            validationMessageSetByDev = !validationMessage.equals("")
        }
        setDefaultValidationMessage()
        attributes.forEach{it.revalidate()}
    }

    //******************************************************************************************************
    //Validation

    override fun validateUserInput(value: T?, valueAsText : String?): ValidationResult {
        val regex = regexPattern.toRegex()
        val isValid = if(valueAsText != null) regex.matches(valueAsText) else regex.matches("")

        val isValidSoft = if(rightTrackRegexPattern != null) {
            val softRegex = rightTrackRegexPattern!!.toRegex()
            if (valueAsText != null) softRegex.matches(valueAsText) else regex.matches("")
        }else{
            regexOnRightTrackChecker(isValid, valueAsText)
        }
        return ValidationResult(result = isValid, rightTrackResult = isValidSoft, validationMessage = validationMessage)
    }

    /**
     * Check if the valueAsText is a sub element of the regex pattern, starting from the left side and increasing pattern
     * length.
     * @param isValid : Boolean if the full pattern check was valid. If true then the function immediately returns true
     * @param valueAsText : String that will be checked against the sub patterns
     */
    private fun regexOnRightTrackChecker(isValid: Boolean, valueAsText: String?): Boolean {
        if (isValid) {
            return true
        } else {
            //try to go through regexString till all values are checked
            var tempString = ""
            for (char in regexPattern) {
                tempString += char
                try {
                    val tempRegex = tempString.toRegex()
                    val tempResult = tempRegex.matches(valueAsText ?: "")

                    if (tempResult) {
                        return true
                    }
                } catch (e: Exception) {
                    println("Regex not working")
                }
            }
        }
        return false
    }

    //TODO: Do this in base class if it is always the same!
    override fun checkAndSetDevValues(){
        //There is no check for regex and therefore it will be set directly
    }

    //******************************************************************************************************
    //Protected

    override fun setDefaultValidationMessage(){
        if(!validationMessageSetByDev){
            validationMessage = "Pattern does not match to $regexPattern"
        }
    }

    override fun setValues() {
        //There is no check for regex and therefore it will be set directly
    }

    override fun deleteCaches() {
        //There is no check for regex and therefore there is no cache
    }


    //******************************************************************************************************
    //Getter

    fun getRegexPattern() : String {
        return regexPattern
    }

}

