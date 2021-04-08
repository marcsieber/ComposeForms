package model.util.attribute

import model.FormModel
import java.lang.NumberFormatException
import kotlin.jvm.Throws

class IntegerAttribute(model: FormModel, value : Int) : NumberAttribute<IntegerAttribute, Int>(model, value) {

    //******************************************************************************************************
    //Validation

    /**
     * This method checks if the new input value is valid.
     * If it is, the new value is set.
     * If it isn't, setValid(false) is called.
     *
     * @param newVal the new value as a String
     */
    override fun checkAndSetValue(newVal : String){
        try{
            validatedValue(Integer.valueOf(newVal))
            setValid(true)
            setValidationMessage("Valid Input")
            setValue(Integer.valueOf(newVal))
        } catch (e : NumberFormatException){
            setValid(false)
            setValidationMessage("No Integer")
            e.printStackTrace()
        } catch (e : IllegalArgumentException){
            setValid(false)
            setValidationMessage(e.message.toString())
            e.printStackTrace()
        }
    }
}