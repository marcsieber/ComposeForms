package com.model

import androidx.compose.runtime.mutableStateOf

class CalculatorModel<T>(val model : Model) where T : Number {

    val calculationString   = mutableStateOf(model.text)
    val operators           = listOf("+", "-", "*", "/")

    /**
     * This method updates the CalculationString with a new number and makes sure that the new result is calculated.
     * @param num: Int
     */
    fun newNumberForCalc(num: Int){
        calculationString.value += num.toString()
        calculationStringToNumber()
    }

    /**
     * This method updates the CalculationString with a new operator and makes sure that the new result is calculated.
     * @param op: String
     */
    fun newOperatorForCalc(op: String){
        calculationString.value += " $op "
        calculationStringToNumber()
    }

    /**
     * This method deletes the last character that was entered and makes sure that the new result is calculated.
     */
    fun deleteLastCharacter(){
        if(calculationString.value[calculationString.value.lastIndex] == ' '){
            calculationString.value = calculationString.value.dropLast(3)
        }else{
            calculationString.value = calculationString.value.dropLast(1)
        }
        calculationStringToNumber()
    }

    /**
     * This method ensures that the calculationString and the currentResult are reset to model.text.
     */
    fun reset(){
        calculationString.value = model.text
        calculationStringToNumber(false)
    }

    //******************************
    //Internal methods

    /**
     * This method calculates the result from the calculation given in the calculationString.
     * The Publish parameter can be used to specify whether publish should be executed or not.
     * @param publish : Boolean
     */
    private fun calculationStringToNumber(publish: Boolean = true){
        val list = calculationString.value.split(" ")
        var currentResult : Double = 0.0
        var operatorCache = "+"

        list.forEach{
            if(it.isEmpty()){
                return@forEach
            }

            if(it in operators){
                operatorCache = it
            }else{
                currentResult = calculate(currentResult, it.toDouble(), operatorCache)
            }
        }

        model.text = currentResult.toString()

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
    private fun calculate(val1 : Double, val2: Double, operator : String): Double {

        when (operator){
            "+" -> return (val1.toDouble() + val2.toDouble())
            "-" -> return (val1.toDouble() - val2.toDouble())
            "*" -> return (val1.toDouble() * val2.toDouble())
            "/" -> return (val1.toDouble() / val2.toDouble())
            else -> throw IllegalArgumentException("Operator not implemented")
        }
    }
}