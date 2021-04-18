package model.util.attribute

import model.FormModel
import java.lang.NumberFormatException

class ShortAttribute(
    model: FormModel,
    value : Short? = null,
    label: String = "",
    required: Boolean = false,
    readOnly: Boolean = false,

    lowerBound : Short? = null,
    upperBound : Short? = null,
    stepSize :   Short = 1

) : NumberAttribute<ShortAttribute, Short>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, lowerBound = lowerBound, upperBound = upperBound, stepSize = stepSize)  {

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
                validatedValue(newVal.toShort())
                setValid(true)
                setValidationMessage("Valid Input")
                setValue(newVal.toShort())
            } catch (e: NumberFormatException) {
                setValid(false)
                setValidationMessage("No Short")
                e.printStackTrace()
            } catch (e: IllegalArgumentException) {
                setValid(false)
                setValidationMessage(e.message.toString())
                e.printStackTrace()
            }
        }
    }

    override fun toDatatype(castValue: String): Short {
        return castValue.toShort()
    }
}