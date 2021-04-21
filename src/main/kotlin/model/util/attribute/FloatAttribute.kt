package model.util.attribute

import model.FormModel
import java.lang.NumberFormatException

class FloatAttribute(
    model: FormModel,
    value : Float? = null,
    label: String = "",
    required: Boolean = false,
    readOnly: Boolean = false,

    lowerBound : Float? = null,
    upperBound : Float? = null,
    stepSize :   Float = 1f,
    onlyStepValuesAreValid : Boolean = false,

    decimalPlaces : Int = 8
) : FloatingPointAttribute<FloatAttribute, Float>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, lowerBound = lowerBound, upperBound = upperBound, stepSize = stepSize,onlyStepValuesAreValid = onlyStepValuesAreValid, decimalPlaces = decimalPlaces) {

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
                val floatVal = convertStringToFloat(newVal)
                val roundedVal = roundToDecimalPlaces(floatVal)
                if(!calledFromKeyEvent){
                    checkDecimalPlaces(floatVal.toString())
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
     * This method converts a String into a Float.
     * If this is not possible,  a Numberformatexception is thrown
     * @param newVal : String
     * @return newVal : Float
     * @throws NumberFormatException
     */
    private fun convertStringToFloat(newVal: String) : Float{
        try{
            return newVal.toFloat()
        }catch(e: NumberFormatException){
            throw NumberFormatException("No Float")
        }
    }

    override fun toDatatype(castValue: String): Float {
        return castValue.toFloat()
    }
}