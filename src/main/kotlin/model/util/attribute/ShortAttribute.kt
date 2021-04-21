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
                val shortVal = convertStringToShort(newVal)
                validatedValue(shortVal)
                setValid(true)
                setValidationMessage("Valid Input")
                setValue(shortVal)
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

    /**
     * This method converts a String into a Short.
     * If this is not possible,  a Numberformatexception is thrown
     * @param newVal : String
     * @return newVal : Short
     * @throws NumberFormatException
     */
    private fun convertStringToShort(newVal: String) : Short{
        try{
            return newVal.toShort()
        }catch(e: NumberFormatException){
            throw NumberFormatException("No Short")
        }
    }

    override fun toDatatype(castValue: String): Short {
        return castValue.toShort()
    }
}