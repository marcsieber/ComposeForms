package model.util.attribute

import model.FormModel
import java.lang.NumberFormatException

class IntegerAttribute(model: FormModel,
                       value : Int? = null,
                       label: String = "",
                       required: Boolean = false,
                       readOnly: Boolean = false,

                       lowerBound : Int? = null,
                       upperBound : Int? = null,
                       stepSize :   Int = 1
) : NumberAttribute<IntegerAttribute, Int>(model = model, value = value, label = label, required = required,
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
    override fun checkAndSetValue(newVal : String?){
        if(newVal == null){
            setNullValue()
        } else {
            try {
                val intVal = convertStringToInt(newVal)
                validatedValue(intVal)
                setValid(true)
                setValidationMessage("Valid Input")
                setValue(intVal)
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
     * This method converts a String into a Int.
     * If this is not possible,  a Numberformatexception is thrown
     * @param newVal : String
     * @return newVal : Int
     * @throws NumberFormatException
     */
    private fun convertStringToInt(newVal: String) : Int{
        try{
            return newVal.toInt()
        }catch(e: NumberFormatException){
            throw NumberFormatException("No Integer")
        }
    }

    override fun toDatatype(castValue: String): Int {
        return castValue.toInt()
    }
}