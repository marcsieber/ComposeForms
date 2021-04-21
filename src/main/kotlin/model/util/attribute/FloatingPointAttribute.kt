package model.util.attribute

import model.FormModel
import java.lang.NumberFormatException

abstract class FloatingPointAttribute <F,T> (
    model: FormModel,
    value : T? = null,
    label: String = "",
    required: Boolean = false,
    readOnly: Boolean = false,

    lowerBound : T? = null,
    upperBound : T? = null,
    stepSize :   T = 1.0 as T,
    onlyStepValuesAreValid : Boolean = false,

    private var decimalPlaces : Int = 8
) : NumberAttribute<F,T>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, lowerBound = lowerBound, upperBound = upperBound, stepSize = stepSize, onlyStepValuesAreValid = onlyStepValuesAreValid) where F : FloatingPointAttribute<F,T>, T : Number, T : Comparable<T>{

    /**
     * This method rounds double numbers to the desired decimal places.
     * @param value : Double
     * @return rounded value : Double
     */
    protected fun roundToDecimalPlaces(value: T) : T {
        val decimalPlaceShifter = Math.pow(10.0,decimalPlaces.toDouble())
        val interimNum = value.toDouble() * decimalPlaceShifter

        val roundedValue =
            if(interimNum - interimNum.toInt() < 0.5){
                (interimNum.toInt() / decimalPlaceShifter)
            }else{
                ((interimNum.toInt()+1) / decimalPlaceShifter)
            }

        return roundedValue as T
    }

    /**
     * This method checks if the new value has too many decimalPlaces.
     * @param newVal : String
     * @throws NumberFormatException
     */
    protected fun checkDecimalPlaces(newVal : String){
        val splittedNumber = newVal.split(".")
        if(splittedNumber[1].length > getDecimalPlaces()){
            throw NumberFormatException("Too many decimal places")
        }
    }


    override fun toString(): String {
        return toString().format(String.format("%." + getDecimalPlaces() + "f"));
    }

    /**
     * This method converts the comma in number inputs into a point.
     *
     * @param newValueAsText : String
     * @return newValueAsText : String
     */
    fun convertComma(newValueAsText : String) : String{
        if(newValueAsText.contains(",")){
            return newValueAsText.replace(",", ".")
        }
        return newValueAsText
    }

    //******************************************************************************************************
    //Getter & Setter

    fun setDecimalPlaces(decimalPlaces : Int){
        if(decimalPlaces >= 1){ //todo: define max decimal places
            this.decimalPlaces = decimalPlaces
            checkAndSetValue(getValueAsText())
        }
        else {
            throw IllegalArgumentException("number of decimal Places must be positive")
        }
    }

    fun getDecimalPlaces() : Int {
        return decimalPlaces
    }

}