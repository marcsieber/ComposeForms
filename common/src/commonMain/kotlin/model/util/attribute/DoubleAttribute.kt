package model.util.attribute

import model.FormModel
import model.util.ILabel
import java.lang.NumberFormatException

class DoubleAttribute<L>(  model                   : FormModel,
                        value                   : Double? = null,
                        label                   : L,
                        required                : Boolean = false,
                        readOnly                : Boolean = false,
                        onChangeListeners       : List<(Double?) -> Unit> = emptyList(),

                        lowerBound              : Double? = null,
                        upperBound              : Double? = null,
                        stepSize                : Double = 1.0,
                        onlyStepValuesAreValid  : Boolean = false,

                        decimalPlaces           : Int = 8

) : FloatingPointAttribute<DoubleAttribute<L>, Double,L>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, lowerBound = lowerBound, upperBound = upperBound, stepSize = stepSize, onlyStepValuesAreValid = onlyStepValuesAreValid,
    decimalPlaces = decimalPlaces, onChangeListeners = onChangeListeners)
    where L: Enum<*>, L : ILabel {

    //******************************************************************************************************
    //Validation

    /**
     * This method checks if the new input value is valid.
     * If it is, the new value is set.
     * If it isn't, setValid(false) is called.
     *
     * @param newVal : String
     * @throws NumberFormatException
     * @throws IllegalArgumentException
     */
    override fun checkAndSetValue(newVal: String?, calledFromKeyEvent : Boolean) {
        if(newVal == null){
            setNullValue()
        } else {
            try {
                val doubleVal = convertStringToDouble(newVal)
                val roundedVal = roundToDecimalPlaces(doubleVal)
                if(!calledFromKeyEvent){
                    println(doubleVal.toString())
                    checkDecimalPlaces(doubleVal.toString())
                }
                validatedValue(roundedVal)
                setValid(true)
                setValidationMessage("Valid Input")
                setValue(roundedVal)
            } catch (e: NumberFormatException) {
                setValid(false)
                setValidationMessage(e.message.toString())
                e.printStackTrace()
            } catch (e: IllegalArgumentException) {
                setValid(false)
                setValidationMessage(e.message.toString())
                e.printStackTrace()
            }
        }
    }

    /**
     * This method converts a String into a Double.
     * If this is not possible, a NumberFormatException is thrown
     * @param newVal : String
     * @return newVal : Double
     * @throws NumberFormatException
     */
    private fun convertStringToDouble(newVal: String) : Double{
        try{
            return newVal.toDouble()
        }catch(e: NumberFormatException){
            throw NumberFormatException("No Double")
        }
    }

    override fun toDatatype(castValue: String): Double {
        return castValue.toDouble()
    }
}