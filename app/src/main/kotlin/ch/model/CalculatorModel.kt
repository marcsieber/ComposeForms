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

package ch.model



import androidx.compose.runtime.mutableStateOf
import communication.AttributeType

/**
 * @author Louisa Reinger
 * @author Steve Vogel
 */
class CalculatorModel<T>(val model: Model, val attrType: AttributeType) where T : Number {

    val calculationString   = mutableStateOf(model.text)
    val operators           = listOf("+", "-", "*", "/")
    val operatorsBlanked: List<String> = operators.map{ " $it " }
    val validRegex          = Regex("^[0123456789\\.\\*\\-\\+\\/ ]*$")

    val isFloatingPoint = attrType === AttributeType.DOUBLE || attrType === AttributeType.FLOAT

    var pointIsActive = mutableStateOf( false )

    init{
        recalculatePointIsActive()
    }

    /**
     * This method updates the CalculationString with a new number and makes sure that the new result is calculated.
     * @param num: Int
     */
    fun newNumberForCalc(num: Int){
        if(calculationString.value.matches(validRegex)){
            calculationString.value += num.toString()
            calculationStringToNumber()
        }
    }

    fun addSpecialCharacters(char: Char){
        if(char == '.'){
            if(pointIsActive.value) {
                calculationString.value += char
                recalculatePointIsActive()
            }
        }
    }

    fun recalculatePointIsActive(){
        pointIsActive.value = isFloatingPoint && !calculationString.value.contains(".")
    }

    /**
     * This method updates the calculationString with a new operator and calls calculationStringToNumber(false)
     * to make sure that the new operator is saved, but nothing should be published to the desktop.
     * This method also makes sure that no two operators are next to each other (only the last operator will be saved).
     * @param op: String
     */
    fun newOperatorForCalc(op: String){
        if(calculationString.value.matches(validRegex)) {
            if (calculationString.value.isNotEmpty() && calculationString.value[calculationString.value.lastIndex] == ' ') {
                calculationString.value = calculationString.value.dropLast(3)
            }
            calculationString.value += " $op "
            calculationStringToNumber(false)
        }
    }

    /**
     * This method deletes the last character that was entered and ensures that the new result is calculated
     * as long as the calculationString contains only characters that can be converted into a result.
     */
    fun deleteLastCharacter(){
        if(calculationString.value.isNotEmpty()){
            if(calculationString.value.length >= 3 && calculationString.value.substring(startIndex = calculationString.value.lastIndex - 2)
                in operatorsBlanked){ //last 3 indexes = " $op "
                calculationString.value = calculationString.value.dropLast(3)
            }else{
                calculationString.value = calculationString.value.dropLast(1)
            }
        }

        if(calculationString.value.matches(validRegex)) { //valid characters -> calculate result
            calculationStringToNumber()
        }else{ //invalid characters -> no calculation
            model.text = calculationString.value
            model.publish()
        }

        recalculatePointIsActive()
    }

    /**
     * This method ensures that the calculationString is reset to model.text.
     */
    fun reset(){
        calculationString.value = model.text
    }

    //******************************
    //Internal methods

    /**
     * This method calculates the result from the calculation given in the calculationString.
     * The publish parameter can be used to specify whether publish should be executed or not.
     * @param publish : Boolean
     */
    private fun calculationStringToNumber(publish: Boolean = true){ //TODO fix "num SPACE num" (num + num is excecuted)
        if(calculationString.value.trim().isEmpty()){
            model.text = ""
            calculationString.value = ""
        }else {
            val list = calculationString.value.split(" ")
            var currentResult = toDataType(0.0, attrType)
            var operatorCache = "+"

            list.forEach {
                if (it.isEmpty()) {
                    return@forEach
                }

                if (it in operators) {
                    operatorCache = it
                } else {
                    currentResult = calculate(currentResult.toDouble(), it.toDouble(), operatorCache)
                }
            }
            model.text = currentResult.toString()
        }

        if(publish){
            model.publish()
        }
    }

    /**
     * This method calculates the result of the given parameters.
     * @param val1 : Double
     * @param val2 : Double
     * @param operator : String
     * @return result: Double
     * @throws IllegalArgumentException
     */
    private fun calculate(val1 : Double, val2: Double, operator : String): T {

        when (operator){
            "+" -> return toDataType((val1 + val2), attrType)
            "-" -> return toDataType((val1 - val2), attrType)
            "*" -> return toDataType((val1 * val2), attrType)
            "/" -> return toDataType((val1 / val2), attrType)
            else -> throw IllegalArgumentException("Operator not implemented")
        }
    }

    /**
     * This method returns the value as attribute type.
     * @param value: Double
     * @param attrType: AttributeType
     */
    private fun toDataType(value : Double, attrType: AttributeType) : T{
        println("A-T: " + attrType +", val: " + value)
        return when (attrType){
            AttributeType.SHORT     -> value.toInt().toShort() as T
            AttributeType.INTEGER   -> value.toInt() as T

            AttributeType.LONG      -> value.toLong() as T
            AttributeType.DOUBLE    -> value.toDouble() as T
            AttributeType.FLOAT     -> value.toFloat() as T
            else -> value as T
        }
    }
}