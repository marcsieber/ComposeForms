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

    decimalPlaces : Int = 2
) : FloatingPointAttribute<FloatAttribute, Float>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, lowerBound = lowerBound, upperBound = upperBound, stepSize = stepSize, decimalPlaces = decimalPlaces) {

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
    override fun checkAndSetValue(newVal: String?) {
        if(newVal == null){
            setNullValue()
        } else {
            try {
                val roundedVal = roundToDecimalPlaces(newVal.toFloat())
                validatedValue(roundedVal)
                setValid(true)
                setValidationMessage("Valid Input")
                try {
                    setValue(roundedVal)
                }catch(e: NumberFormatException){
                    setValid(false)
                    setValidationMessage("No Float")
                    e.printStackTrace()
                }
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

    override fun toDatatype(castValue: String): Float {
        return castValue.toFloat()
    }
}