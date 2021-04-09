package model.util.attribute

import model.FormModel
import kotlin.jvm.Throws

class StringAttribute(model: FormModel, value : String) : Attribute<StringAttribute, String>(model, value) {

    private var minLength = 0
    private var maxLength = Int.MAX_VALUE

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
            validatedValue(newVal)
            setValid(true)
            setValidationMessage("Valid Input")
            setValue(newVal)
        } catch (e : IllegalArgumentException){
            setValid(false)
            setValidationMessage(e.message.toString())
            e.printStackTrace()
        }
    }

    /**
     * This method checks, if the value is valid regarding the optional extra-properties.
     * If it is not valid there will be thrown an exception.
     *
     * @throws IllegalArgumentException
     */
    @Throws(IllegalArgumentException :: class)
    private fun validatedValue(newVal: String){
        if (newVal.length < minLength){
            throw IllegalArgumentException("Validation mismatched (minLength)")
        }
        if (newVal.length > maxLength){
            throw IllegalArgumentException("Validation mismatched (maxLength)")
        }
    }

    //******************************************************************************************************
    //Setter

    fun setMinLength(minLength : Int) : StringAttribute {
        this.minLength = minLength
        return this
    }

    fun setMaxLength(maxLength : Int) : StringAttribute {
        this.maxLength = maxLength
        return this
    }


    //******************************************************************************************************
    //Getter

    fun getMinLength() : Int {
        return minLength
    }

    fun getMaxLength() : Int {
        return maxLength
    }
}