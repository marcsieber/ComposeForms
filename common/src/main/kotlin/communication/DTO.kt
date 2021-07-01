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

package communication


import kotlinx.serialization.Serializable

@Serializable
class DTOAttribute(val id: Int, val label: String, val attrType: AttributeType = AttributeType.OTHER,
                   val possibleSelections: Set<String> = emptySet())

@Serializable
class DTOText(val id: Int, val text: String)

@Serializable
class DTOValidation(val onRightTrack: Boolean = true, val isValid: Boolean = true,
                    val readOnly: Boolean = false, val errorMessages : List<String> = emptyList())

@Serializable
class DTOCommand(val command: Command)



enum class Command {
    NEXT,
    PREVIOUS,
    REQUEST,
    SAVE,
    RESET
}

enum class AttributeType {
    STRING,
    INTEGER,
    SHORT,
    DOUBLE,
    FLOAT,
    LONG,
    SELECTION,
    OTHER
}
