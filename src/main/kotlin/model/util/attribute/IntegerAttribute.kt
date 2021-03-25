import androidx.compose.runtime.mutableStateOf
import model.util.Observable
import java.lang.NumberFormatException

class IntegerAttribute(value : Int){

    private var value               = Observable(value)
    private var savedValue          = Observable(value)
    private var valueAsText         = Observable(value.toString())
    private var label               = mutableStateOf("")
    private var required            = mutableStateOf(false)
    private var readOnly            = mutableStateOf(false)
    private var valid               = mutableStateOf(false)
    private var validationMessage   = mutableStateOf("")
    private var changed             = mutableStateOf(false)

    //optional extra-properties for IntegerAttribute
    private var lowerBound          = mutableStateOf(Int.MIN_VALUE)
    private var upperBound          = mutableStateOf(Int.MAX_VALUE)
    private var stepSize            = mutableStateOf(1)
    private var stepStart           = value

    /**
     * after instantiation: call all default-listeners:
     * When valueAsText changes setValue is called
     * When value       changes setChanged is called
     * When savedValue  changes setChanged(false) is called
     */
    init {
        valueAsText.addListener { newVal -> setValue(newVal) }
        this.value.addListener  { newVal -> setChanged(newVal)}
        savedValue.addListener  { setChanged(false)}
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
     *Set the savedValue to the current value.
     * This makes the attribute "not changed" again.
     */
    fun save(){
        setSavedValue(getValue())
    }

    /**
     * This method checks if the new input value is valid.
     * If it is, the new value is set.
     * If it isn't, setValid(false) is called.
     */
    fun setValue(newVal : String) : IntegerAttribute{
        try{
            validatedValue(Integer.valueOf(newVal))
            setValid(true)
            setValidationMessage("Valid Input")
            this.value.setValue(Integer.valueOf(newVal))
        } catch (e : NumberFormatException){
            setValid(false)
            setValidationMessage("No Integer")
            e.printStackTrace()
        }
        return this
    }

    /**
     * This method checks, if the value is valid regarding the optional extra-properties.
     * If it is not valid there will be thrown an exception.
     *
     * @throws NumberFormatException
     *
     * this mehod is called in the setValue method
     */
    private fun validatedValue(newVal: Int){
        if  (    !(newVal >= lowerBound.value
                && newVal <= upperBound.value
                && (stepStart + newVal) %  stepSize.value == 0)){
            throw NumberFormatException("validation missmatched (lowerBound/upperBound/stepSize)")
        }
    }

    private fun setValue(value: Int) : IntegerAttribute {
        return setValue(value.toString())
    }
    fun getValue() : Int{
        return value.getValue()
    }

    private fun setSavedValue(value: Int) : IntegerAttribute{
        this.savedValue.setValue(value)
        return this
    }
    fun getSavedValue() : Int{
        return savedValue.getValue()
    }

    fun setValAsText(valueAsText : String) : IntegerAttribute{
        this.valueAsText.setValue(valueAsText)
        return this
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

    /**
     * isChanged = true, if value and savedValue are not equal
     * @return IntegerAttribute
     */
    private fun setChanged(newVal: Int) : IntegerAttribute{
        this.changed.value = !newVal.equals(getSavedValue())
        return this
    }
    private fun setChanged(isChanged : Boolean) : IntegerAttribute{
        this.changed.value = isChanged
        return this
    }
    fun isChanged() : Boolean{
        return changed.value
    }

    fun setLowerBound(lowerBound : Int) : IntegerAttribute{
        this.lowerBound.value = lowerBound
        return this
    }
    fun getLowerBound() : Int {
        return lowerBound.value
    }

    fun setUpperBound(upperBound : Int) : IntegerAttribute{
        this.upperBound.value = upperBound
        return this
    }
    fun getUpperBound() : Int {
        return upperBound.value
    }

    fun setStepSize(stepSize : Int) : IntegerAttribute{
        this.stepSize.value = stepSize
        return this
    }
    fun getStepSize() : Int {
        return stepSize.value
    }
}