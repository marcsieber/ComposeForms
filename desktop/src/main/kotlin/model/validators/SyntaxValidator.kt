/*
 *
 *  * ========================LICENSE_START=================================
 *  * Compose Forms
 *  * %%
 *  * Copyright (C) 2021 FHNW Technik
 *  * %%
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *        http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  * =========================LICENSE_END==================================
 *
 */

package model.validators

import util.Utilities
import java.lang.NumberFormatException

class SyntaxValidator<T>( validationMessage   : String = "") : Validator<T>(validationMessage = validationMessage) {

    init {
        if(validationMessage.equals("")){
            setDefaultValidationMessage()
        }
    }

    //******************************************************************************************************
    //Validation

    override fun validateUserInput(value: T?, valueAsText: String?): ValidationResult {
        //value is here typeT (only used for type checks)
        var isValid : Boolean
        try {
            Utilities<T>().toDataType(valueAsText!!, value!!)
            isValid = true
        }catch(e : NumberFormatException){
            isValid = false
        }
        return ValidationResult(isValid, isValid, validationMessage)
    }

    //******************************************************************************************************
    //Protected

    override fun setDefaultValidationMessage() {
        validationMessage = "This is not the correct input type"
    }


}