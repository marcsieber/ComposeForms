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

package model.validators.semanticValidators

import model.validators.Validator

abstract class SemanticValidator<T>(validationMessage : String) : Validator<T>(validationMessage = validationMessage) {

    protected var validationMessageSetByDev : Boolean = !validationMessage.equals("")

    /**
     * The values to be set are first checked to see if they make sense. Then a default message is set if none was passed.
     */
    fun init(){
        checkAndSetDevValues()
        if(validationMessage.equals("")){
            setDefaultValidationMessage()
        }
    }

    /**
     * This method checks whether the values to set make sense or not.
     * If not, an IllegalException is thrown.
     * If yes, setValues is called.
     * Finally the temporary cache values are deleted again.
     *
     * @throws IllegalArgumentException
     */
    abstract fun checkAndSetDevValues()

    /**
     * This method writes the temporary set caches into the values.
     */
    protected abstract fun setValues()

    /**
     * This method sets all cache values to null.
     */
    protected abstract fun deleteCaches()
}