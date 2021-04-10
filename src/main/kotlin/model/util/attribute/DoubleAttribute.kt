package model.util.attribute

import model.FormModel
import java.lang.NumberFormatException

class DoubleAttribute(model: FormModel, value : Double) : NumberAttribute<DoubleAttribute, Double>(model, value) {
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
    override fun checkAndSetValue(newVal: String) {
        try{
            validatedValue(newVal.toDouble())
            setValid(true)
            setValidationMessage("Valid Input")
            setValue(newVal.toDouble())
        } catch (e : NumberFormatException){
            setValid(false)
            setValidationMessage("No Double")
            e.printStackTrace()
        } catch (e : IllegalArgumentException){
            setValid(false)
            setValidationMessage(e.message.toString())
            e.printStackTrace()
        }
    }
}