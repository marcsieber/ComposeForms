package model.util.attribute

import model.FormModel
import java.lang.NumberFormatException

class LongAttribute(
    model: FormModel,
    value : Long? = null,
    label: String = "",
    required: Boolean = false,
    readOnly: Boolean = false,

    lowerBound : Long? = null,
    upperBound : Long? = null,
    stepSize :   Long = 1

) : NumberAttribute<LongAttribute, Long>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, lowerBound = lowerBound, upperBound = upperBound, stepSize = stepSize) {
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
                validatedValue(newVal.toLong())
                setValid(true)
                setValidationMessage("Valid Input")
                setValue(newVal.toLong())
            } catch (e: NumberFormatException) {
                setValid(false)
                setValidationMessage("No Long")
                e.printStackTrace()
            } catch (e: IllegalArgumentException) {
                setValid(false)
                setValidationMessage(e.message.toString())
                e.printStackTrace()
            }
        }
    }

    override fun toDatatype(castValue: String): Long {
        return castValue.toLong()
    }
}