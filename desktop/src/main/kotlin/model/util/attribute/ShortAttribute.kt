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

package model.util.attribute

import model.IModel
import model.convertibles.CustomConvertible
import model.meanings.Default
import model.meanings.SemanticMeaning
import model.util.ILabel
import model.validators.semanticValidators.SemanticValidator

class ShortAttribute<L>(//required parameters
    model                   : IModel,
    label                   : L,

                        //optional parameters
    value                   : Short?                            = null,
    required                : Boolean                           = false,
    readOnly                : Boolean                           = false,
    onChangeListeners       : List<(Attribute<*,*,*>) -> Unit>       = emptyList(),
    validators              : List<SemanticValidator<Short>>    = mutableListOf(),
    convertibles            : List<CustomConvertible>           = emptyList(),
    meaning                 : SemanticMeaning<Short>            = Default()

                        ) : NumberAttribute<ShortAttribute<L>, Short, L>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, onChangeListeners = onChangeListeners, validators = validators, convertibles = convertibles, meaning = meaning)
        where L: Enum<*>, L : ILabel{

    override val typeT: Short
        get() = 0

}