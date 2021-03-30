import androidx.compose.runtime.mutableStateOf
import model.util.Observable
import java.lang.NumberFormatException
import java.util.*
import kotlin.collections.HashMap
import kotlin.jvm.Throws

class IntegerAttribute(value : Int){

    private var value               = value
    private var savedValue          = value
    private val valueAsText         = mutableStateOf(value.toString())
    private val label               = mutableStateOf("")
    private val required            = mutableStateOf(false)
    private val readOnly            = mutableStateOf(false)
    private val valid               = mutableStateOf(true)
    private val validationMessage   = mutableStateOf("")
    private val changed             = mutableStateOf(false)
    private val labels              = HashMap<Locale,String>()
    private val currentLanguage     = mutableStateOf<Locale?>(null)

    //optional extra-properties for IntegerAttribute
    private var lowerBound          = Int.MIN_VALUE
    private var upperBound          = Int.MAX_VALUE
    private var stepSize            = 1
    private val stepStart           = value


    //******************************************************************************************************
    //Validation

    /**
     * This method checks if the new input value is valid.
     * If it is, the new value is set.
     * If it isn't, setValid(false) is called.
     */
    private fun setValue(newVal : String) : IntegerAttribute{
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
        return this
    }

    /**
     * This method checks, if the value is valid regarding the optional extra-properties.
     * If it is not valid there will be thrown an exception.
     *
     * @throws NumberFormatException
     *
     * this method is called in the setValue method
     */
    @Throws(IllegalArgumentException :: class)
    private fun validatedValue(newVal: Int){
        if  (    !(newVal in lowerBound..upperBound
                && (stepStart + newVal) %  stepSize == 0)){
            throw IllegalArgumentException("Validation missmatched (lowerBound/upperBound/stepSize)")
        }
    }

    //******************************************************************************************************
    //Functions called on user actions

    /**
     * This method sets the savedValue to the current value
     * and makes the attribute "not changed" again.
     */
    fun save(){
        setSavedValue(getValue())
        setChanged(false)
    }

    /**
     * This method resets the valAsText to the last stored value.
     * The value is indirectly adjusted as well, because the value listens to valAsText, and setValue(newVal) is executed.
     */
    fun undo(){
        setValAsText(getSavedValue().toString())
    }

    /**
     * This method changes the language.
     * When setCurrentLanguage(language) is called, the label is set to the label name in the language.
     * If no label name has been defined in this language yet, the label name will be set to "...".
     *
     * Default value if setLanguage is not called: @see setLabelForLanguage()
     *
     * @param language : Locale
     * @return the called instance : IntegerAttribute
     */
    fun setCurrentLanguage(language : Locale) : IntegerAttribute{
        label.value = labels.getOrDefault(language, "...")
        currentLanguage.value = language
        return this
    }

    //******************************************************************************************************
    //Getter & Setter

    /**
     * This method checks if the language is set as the current language
     * @param language : Locale
     * @return Boolean
     */
    fun isCurrentLanguage(language : Locale) : Boolean{
        return currentLanguage.value == language
    }

    private fun setValue(value: Int) : IntegerAttribute {
        this.value = value
        return this
    }
    fun getValue() : Int{
        return value
    }

    private fun setSavedValue(value: Int) : IntegerAttribute{
        this.savedValue = value
        return this
    }
    fun getSavedValue() : Int{
        return savedValue
    }

    /**
     * This method sets valueAsText to the new value, calls setChanged to look if there
     * are changes in comparison to the savedValue and it calls setValue.
     * @param valueAsText : String
     * @return the called instance : IntegerAttribute
     */
    fun setValAsText(valueAsText : String) : IntegerAttribute{
        this.valueAsText.value = valueAsText
        setChanged(valueAsText)
        setValue(valueAsText)
        return this
    }
    fun getValAsText(): String {
        return valueAsText.value
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

    /**
     * setLabelForLanguage is needed if the app should support multiple languages.
     * The language and the corresponding label name for this attribute is stored in a hashmap.
     *
     * If (at the beginning) no language is selected (call of setCurrentLanguage),
     * the first entry in the hashmap will be used.
     *
     * @param language : Locale
     * @param label : String
     * @return the called instance : IntegerAttribute
     */
    fun setLabelForLanguage(language : Locale, label : String) : IntegerAttribute{
        labels[language] = label
        if(labels.size == 1){
            setLabel(label)
            setCurrentLanguage(language)
        }
        return this
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

    private fun setValidationMessage(message : String) : IntegerAttribute{
        this.validationMessage.value = message
        return this
    }
    fun getValidationMessage() : String {
        return validationMessage.value
    }

    /**
     * isChanged = true, if value and savedValue are not equal
     *
     * @param newVal : String
     * @return the called instance : IntegerAttribute
     */
    private fun setChanged(newVal: String) : IntegerAttribute{
        this.changed.value = !newVal.equals(getSavedValue().toString())
        return this
    }
    private fun setChanged(isChanged : Boolean) : IntegerAttribute{
        this.changed.value = isChanged
        return this
    }
    fun isChanged() : Boolean{
        return changed.value
    }

    /**
     * This method checks if the given value for lowerBound is below the upperBound value
     * If yes, the value is set
     * If no, an exception is thrown
     *
     * @param lowerBound : Int
     * @throws IllegalArgumentException
     * @return the called instance : IntegerAttribute
     */
    fun setLowerBound(lowerBound : Int) : IntegerAttribute{
        if(lowerBound < upperBound) {
            this.lowerBound = lowerBound
            return this
        }
        else {
            throw IllegalArgumentException("LowerBound is not lower than upperBound")
        }
    }
    fun getLowerBound() : Int {
        return lowerBound
    }

    /**
     * This method checks if the given value for upperBound is above the lowerBound value
     * If yes, the value is set
     * If no, an exception is thrown
     *
     * @param upperBound : Int
     * @throws IllegalArgumentException
     * @return the called instance : IntegerAttribute
     */
    fun setUpperBound(upperBound : Int) : IntegerAttribute{
        if(upperBound > lowerBound){
            this.upperBound = upperBound
            return this
        }
        else {
            throw IllegalArgumentException("UpperBound is not greater than lowerBound")
        }
    }
    fun getUpperBound() : Int {
        return upperBound
    }

    fun setStepSize(stepSize : Int) : IntegerAttribute{
        this.stepSize = stepSize
        return this
    }
    fun getStepSize() : Int {
        return stepSize
    }
}