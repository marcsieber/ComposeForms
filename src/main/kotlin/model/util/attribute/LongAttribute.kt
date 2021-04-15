package model.util.attribute

import model.FormModel
import java.lang.NumberFormatException

class LongAttribute(model: FormModel, value : Long?) : NumberAttribute<LongAttribute, Long>(model, value) {
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
}