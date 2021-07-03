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

package convertibles

import kotlinx.serialization.Serializable
import java.util.regex.Pattern

/**
 * @author Louisa Reinger
 * @author Steve Vogel
 */
@Serializable
class CustomConvertible(var replaceRegex : List<ReplacementPair>, var convertUserView : Boolean = true, var convertImmediately : Boolean = false){

    /**
     * Converts the string. Therefore replaces the string with the regex pattern given in the constructor
     * @param String: value that will be converted
     * @return ConvertibleResult
     */
    fun convertUserInput(valueAsText : String): ConvertibleResult {
        var convertiblePattern : Pattern
        replaceRegex.forEach{
            convertiblePattern = Pattern.compile(it.convertibleRegex)
            if(convertiblePattern.matcher(valueAsText).matches()){
                val convertIntoPattern = convertiblePattern.matcher(valueAsText).replaceAll(it.convertIntoRegex)
                println(convertIntoPattern)
                return ConvertibleResult(true, convertIntoPattern, convertUserView, convertImmediately)
            }
        }
        return ConvertibleResult(false, "", convertUserView, convertImmediately)
    }

}