package model.util.attribute

import model.FormModel
import model.util.ILabel

class LongAttribute<L>(model                   : FormModel,
                    value                   : Long? = null,
                    label                   : L,
                    required                : Boolean = false,
                    readOnly                : Boolean = false,
                    onChangeListeners       : List<(Long?) -> Unit> = emptyList(),

                    lowerBound              : Long? = null,
                    upperBound              : Long? = null,
                    stepSize                : Long = 1,
                    onlyStepValuesAreValid  : Boolean = false

) : NumberAttribute<LongAttribute<L>, Long, L>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, lowerBound = lowerBound, upperBound = upperBound, stepSize = stepSize, onlyStepValuesAreValid = onlyStepValuesAreValid,
    onChangeListeners = onChangeListeners)
        where L: Enum<*>, L : ILabel{
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
                val longVal = convertStringToLong(newVal)
                validatedValue(longVal)
                setValid(true)
                setValidationMessage("Valid Input")
                setValue(longVal)
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

    /**
     * This method converts a String into a Long.
     * If this is not possible, a NumberFormatException is thrown
     * @param newVal : String
     * @return newVal : Long
     * @throws NumberFormatException
     */
    private fun convertStringToLong(newVal: String) : Long{
        try{
            return newVal.toLong()
        }catch(e: NumberFormatException){
            throw NumberFormatException("No Long")
        }
    }

    override fun toDatatype(castValue: String): Long {
        return castValue.toLong()
    }
}