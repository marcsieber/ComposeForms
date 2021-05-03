package model.util.attribute

import androidx.compose.runtime.mutableStateOf
import demo.Labels
import model.FormModel
import model.util.ILabel
import model.validators.ValidationResult
import java.util.*

abstract class Attribute <A,T,L> (private val model       : FormModel,
                                private var value       : T?,
                                label                   : L,
                                required                : Boolean,
                                readOnly                : Boolean,
                                var onChangeListeners   : List<(T?) -> Unit>

) where A : Attribute<A,T,L>, T : Any?, L: Enum<*>, L : ILabel {

    init{
        model.addAttribute(this)
    }

    //******************************************************************************************************
    //Properties

    private var savedValue          = value
    private val valueAsText         = mutableStateOf(value?.toString() ?: "")

    private val label               = label
    private val labelAsText         = mutableStateOf("")
    private val required            = mutableStateOf(required)
    private val readOnly            = mutableStateOf(readOnly)
    private val valid               = mutableStateOf(true)
    private val validationMessage   = mutableStateOf("")
    private val changed             = mutableStateOf(false)
    private var currentLanguage     = ""

    protected val validationResults = mutableStateOf<List<ValidationResult>>(emptyList())


    //******************************************************************************************************
    //Public Setter

    /**
     * This method sets valueAsText to the new value, calls setChanged to look if there
     * are changes in comparison to the savedValue and it calls setValue.
     * The new values are only set if the attribute is not readonly.
     * @param valueAsText : String
     */
    fun setValueAsText(valueAsText : String){
        if("\t" in valueAsText){
            return
        }
        if(!isReadOnly()){
            this.valueAsText.value = valueAsText
            setChanged(valueAsText)
            checkAndSetValue( if(valueAsText.equals("")) null else valueAsText)
        }else{
            setValidationMessage("This is read only. The value was not set.")
        }
    }

    /**
     * This method should only be called after KeyEvents to avoid inaccuracies of double or float values.
     * This method sets valueAsText to the new value, calls setChanged to look if there
     * are changes in comparison to the savedValue and it calls setValue.
     * The new values are only set if the attribute is not readonly.
     * @param valueAsText : String
     */
    fun setValueAsTextFromKeyEvent(valueAsText : String){
        if("\t" in valueAsText){
            return
        }
        if(!isReadOnly()){
            setChanged(valueAsText)
            checkAndSetValue( if(valueAsText.equals("")) null else valueAsText, true)

            if(isValid()){
                this.valueAsText.value = getValue().toString()
            }else{
                this.valueAsText.value = valueAsText
            }
        }
    }

    protected abstract fun checkAndSetValue(newVal: String?, calledFromKeyEvent : Boolean = false)

    fun setLabel(label : String){
        this.labelAsText.value =  ""
    }

    /**
     * This method sets the value null.
     * If it is a SelectionAttribute the value is set to an emptySet<String>()
     * If it is a required field, valid is set false.
     */
    protected fun setNullValue(valueIsASet: Boolean = false){
        setValid(!isRequired())
        setValidationMessage( if(isValid()) "Valid Input" else "Input Required")
        if(valueIsASet){
            setValue(emptySet<String>() as T)
        }else{
            setValue(null)
        }
    }

    /**
     * The required value is set and the current textValue is checked to see if it is still valid.
     */
    fun setRequired(isRequired : Boolean){
        this.required.value = isRequired
        checkAndSetValue(getValueAsText())
    }

    fun setReadOnly(isReadOnly : Boolean){
        this.readOnly.value = isReadOnly
    }

    /**
     * This method sets the valid value and calls setValidForAll()
     * @param isValid : Boolean
     */
    protected fun setValid(isValid : Boolean){
        this.valid.value = isValid
        this.model.setValidForAll()
    }


    //******************************************************************************************************
    //Internal Setter //todo change internal

    /**
     * This method sets the savedValue to the current value
     * and makes the attribute "not changed" again.
     */
    internal fun save() : Boolean{
        println("save")
        setSavedValue(getValue())
        setChanged(false)
        return true
    }

    /**
     * This method resets the valAsText to the last stored value.
     * The value is indirectly adjusted as well, because the value listens to valAsText, and setValue(newVal) is executed.
     */
    internal fun undo(){
        setValueAsText(getSavedValue()?.toString() ?: "")
    }

    /**
     * This method changes the language.
     * When setCurrentLanguage(language) is called, the label is set to the label name in the language.
     * If no label name has been defined in this language yet, the label name will be set to "...".
     *
     * Default value if setLanguage is not called: @see setLabelForLanguage()
     *
     * @param language : Locale
     */
    internal fun setCurrentLanguage(language : String){
        labelAsText.value = label.getLabelInLanguage(this.label as Labels, language)
        currentLanguage = language
    }

    //******************************************************************************************************
    //Protected Setter

    protected fun setValue(value: T?) {
        this.value = value
        onChangeListeners.forEach{it(value)}
    }

    protected fun setValidationMessage(message : String){
        this.validationMessage.value = message
    }

    //******************************************************************************************************
    //Private Setter

    private fun setSavedValue(value: T?) {
        this.savedValue = value
    }

    /**
     * Changed is true, if valueAsText and savedValue are not equal.
     * If valueAsText and savedValue are equal or if valueAsText is "" and savedValue is null changed is set false.
     * If the attribute is a selectionAttribute, the order of the elements does not play a role for equality
     * This method also calls setChangedForAll()
     *
     * @param newVal : String
     */
    private fun setChanged(newVal: String) {
        if(this is SelectionAttribute){
            var set : Set<String>
            if(newVal.equals("[]")){
                set = emptySet()
            }else{
                set = newVal.substring(1,newVal.length-1).split(", ").toSet() //convert string to set
            }
            this.changed.value = !(set.equals(getSavedValue()))
            if(!isChanged()){ // set value & savedValue to the new order, otherwise the user will be irritated if the order changes when undo is clicked
                checkAndSetValue(newVal)
                save()
            }
        }else{
            this.changed.value = !newVal.equals(getSavedValue().toString()) && !(newVal.equals("") && getSavedValue() == null)
        }
        this.model.setChangedForAll()
    }

    /**
     * This method sets the changed value and calls setChangedForAll()
     * @param isChanged : Boolean
     */
    private fun setChanged(isChanged : Boolean) {
        this.changed.value = isChanged
        model.setChangedForAll()
    }

    //******************************************************************************************************
    //Public Getter

    fun getValue() : T?{
        return value
    }

    fun getSavedValue() : T?{
        return savedValue
    }

    fun getValueAsText(): String {
        return valueAsText.value
    }

    /**
     * This method checks if the language is set as the current language
     * @param language : Locale
     * @return Boolean
     */
    fun isCurrentLanguage(language : String) : Boolean{
        return currentLanguage == language
    }

    /**
     * @return the label-text: if the attribute is a required field, a "*" is added behind the labeltext
     */
    fun getLabel() : String{
        return if(isRequired()){
            labelAsText.value + "*"
        } else{
            labelAsText.value
        }
    }

    fun getErrorMessages(): List<String>{
        return validationResults.value.filter{!it.result}.map{it.validationMessage}
    }

    fun isRequired() : Boolean{
        return required.value
    }

    fun isReadOnly() : Boolean{
        return readOnly.value
    }

    fun isValid() : Boolean{
//        return validationResults.value.all{it.result} // TODO: Change when all validators are in the validator package instead of the attribute
        return valid.value
    }

    fun getValidationMessage() : String {
        return validationMessage.value
    }

    fun isChanged() : Boolean{
        return changed.value
    }

    fun revalidate(){
        checkAndSetValue(this.valueAsText.value)
    }

}