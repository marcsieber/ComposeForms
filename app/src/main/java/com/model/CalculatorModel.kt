package com.model

import androidx.compose.runtime.mutableStateOf

class CalculatorModel<T>(val model : Model) where T : Number {

    val calculationString   = mutableStateOf(model.text)

    fun newNumberForCalc(num: Int){
        calculationString.value += num.toString()
        calculationStringToNumber()
    }

    fun newOperatorForCalc(op: String){
        calculationString.value += " $op "
        calculationStringToNumber()
    }

    fun calculationStringToNumber(){
        val list = calculationString.value.split(" ")
        var currentResult : Double = 0.0
        var operatorCache = "+"

        list.forEach{
            if(it.isEmpty()){
                    return
                }

            if(it in operators){
                operatorCache = it
            }else{
                currentResult = calculate(currentResult, it.toDouble(), operatorCache)
            }
        }
        model.text = currentResult.toString()
    }

    val operators = listOf("+", "-", "*", "/")

    fun calculate(val1 : Double, val2: Double, operator : String): Double {

        when (operator){
            "+" -> return (val1.toDouble() + val2.toDouble())
            "-" -> return (val1.toDouble() - val2.toDouble())
            "*" -> return (val1.toDouble() * val2.toDouble())
            "/" -> return (val1.toDouble() / val2.toDouble())
            else -> throw IllegalArgumentException("Operator not implemented")
        }
    }

}