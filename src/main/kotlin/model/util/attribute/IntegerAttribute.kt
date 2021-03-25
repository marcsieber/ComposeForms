import androidx.compose.runtime.mutableStateOf
import model.util.Observable
import java.lang.NumberFormatException

class IntegerAttribute(value : Int){

    private var value               = mutableStateOf(value)
    private var valueAsText         = Observable(value.toString())
    private var label               = mutableStateOf("")
    private var required            = mutableStateOf(false)
    private var readOnly            = mutableStateOf(false)
    private var valid               = mutableStateOf(false)
    private var validationMessage   = mutableStateOf("")

    init {
        valueAsText.addListener{ s -> setNewValue(s) }
    }


    /**
     * Adds the parameter to the valueAsText listeners
     * @param func: a function with a String parameter returning Unit
     * @return the called instance
     */
    fun addValueAsTextListener(func: (String) -> Unit) : IntegerAttribute{
        valueAsText.addListener(func)
        return this
    }

    /**
     * <p> This method checks if the new input value is valid.
     * If it is, the new value is set.
     * If it isn't, setValid(false) is called.</p>
     */
    fun setNewValue(newVal : String){
        try{
            setValid(true)
            setValidationMessage("Valid Input")
            setValue(Integer.valueOf(newVal))
        } catch (e : NumberFormatException){
            setValid(false)
            setValidationMessage("No Integer")
            e.printStackTrace()
        }
    }

    private fun setValue(value: Int) : IntegerAttribute {
        this.value.value = value
        return this
    }
    private fun getValue() : Int{
        return value.value
    }

    fun setValAsText(valueAsText : String){
        this.valueAsText.setValue(valueAsText)
    }
    fun getValAsText(): String {
        return valueAsText.getValue()
    }

    fun setLabel(label : String) : IntegerAttribute{
        this.label.value = label
        return this
    }

    /**
     * @return the label-text: if the attribute is a required field, a "*" is added behind the labeltext
     */
    fun getLabel() : String{
        if(isRequired()){
            return label.value + "*"
        }
        else{
            return label.value
        }
    }

    fun setRequired(isRequired : Boolean) : IntegerAttribute{
        this.required.value = isRequired
        return this
    }
    fun isRequired() : Boolean{
        return required.value
    }

    fun setReadOnly(isReadOnly : Boolean) : IntegerAttribute{
        this.readOnly.value = isReadOnly
        return this
    }
    fun isReadOnly() : Boolean{
        return readOnly.value
    }

    fun setValid(isValid : Boolean) : IntegerAttribute{
        this.valid.value = isValid
        return this
    }
    fun isValid() : Boolean{
        return valid.value
    }

    fun setValidationMessage(message : String) : IntegerAttribute{
        this.validationMessage.value = message
        return this
    }
    fun getValidationMessage() : String {
        return validationMessage.value
    }
}