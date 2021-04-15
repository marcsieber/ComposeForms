package model.util.attribute

import androidx.compose.runtime.mutableStateOf
import model.FormModel
import java.util.*
import kotlin.collections.HashMap

abstract class Attribute <A,T> (private val model : FormModel, private var value : T?) where A : Attribute<A,T>, T : Any?{

    //******************************************************************************************************
    //Properties

    private var savedValue          = value
    private val valueAsText         = mutableStateOf(value?.toString() ?: "")

    private val label               = mutableStateOf("")
    private val required            = mutableStateOf(false)
    private val readOnly            = mutableStateOf(false)
    private val valid               = mutableStateOf(true)
    private val validationMessage   = mutableStateOf("")
    private val changed             = mutableStateOf(false)
    private val labels              = HashMap<Locale,String>()
    private val currentLanguage     = mutableStateOf<Locale?>(null)


    //******************************************************************************************************
    //Public Setter

    /**
     * This method sets valueAsText to the new value, calls setChanged to look if there
     * are changes in comparison to the savedValue and it calls setValue.
     * The new values are only set if the attribute is not readonly.
     * @param valueAsText : String
     * @return the called instance : Attribute
     */
    fun setValueAsText(valueAsText : String) : A{
        if(!isReadOnly()){
            this.valueAsText.value = valueAsText
            setChanged(valueAsText)
            checkAndSetValue( if(valueAsText.equals("")) null else valueAsText)
        }
        return this as A
    }

    protected abstract fun checkAndSetValue(newVal: String?)

    fun setLabel(label : String) : A{
        this.label.value = label
        return this as A
    }

    /**
     * setLabelForLanguage is needed if the app should support multiple languages.
     * The language and the corresponding label name for this attribute is stored.
     *
     * If (at the beginning) no language is selected (call of setCurrentLanguage),
     * the label of the first language that is set will be used.
     *
     * @param language : Locale
     * @param label : String
     * @return the called instance : Attribute
     */
    fun setLabelForLanguage(language : Locale, label : String) : A{
        labels[language] = label
        if(labels.size == 1){
            setLabel(label)
            setCurrentLanguage(language)
        }
        return this as A
    }

    /**
     * This method sets the value null.
     * If it is a required field, valid is set false.
     */
    protected fun setNullValue(){
        setValid(!isRequired())
        setValidationMessage( if(isValid()) "Valid Input" else "Input Required")
        setValue(null)
    }

    fun setRequired(isRequired : Boolean) : A{
        this.required.value = isRequired
        return this as A
    }

    fun setReadOnly(isReadOnly : Boolean) : A{
        this.readOnly.value = isReadOnly
        return this as A
    }

    /**
     * This method sets the valid value and calls setValidForAll()
     * @param isvalid : Boolean
     * @return the called instance : Attribute
     */
    fun setValid(isValid : Boolean) : A{
        this.valid.value = isValid
        this.model.setValidForAll()
        return this as A
    }
    //******************************************************************************************************
    //Internal Setter

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
     * @return the called instance : Attribute
     */
    internal fun setCurrentLanguage(language : Locale) : A{
        label.value = labels.getOrDefault(language, "...")
        currentLanguage.value = language
        return this as A
    }

    //******************************************************************************************************
    //Protected Setter

    protected fun setValue(value: T?) {
        this.value = value
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
     * This method also calls setChangedForAll()
     *
     * @param newVal : String
     * @return the called instance : Attribute
     */
    private fun setChanged(newVal: String) {
        this.changed.value = !newVal.equals(getSavedValue().toString()) && !(newVal.equals("") && getSavedValue() == null)
        this.model.setChangedForAll()
    }

    /**
     * This method sets the changed value and calls setChangedForAll()
     * @param isChanged : Boolean
     * @return the called instance : Attribute
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
    fun isCurrentLanguage(language : Locale) : Boolean{
        return currentLanguage.value == language
    }

    /**
     * @return the label-text: if the attribute is a required field, a "*" is added behind the labeltext
     */
    fun getLabel() : String{
        return if(isRequired()){
            label.value + "*"
        } else{
            label.value
        }
    }

    fun isRequired() : Boolean{
        return required.value
    }

    fun isReadOnly() : Boolean{
        return readOnly.value
    }

    fun isValid() : Boolean{
        return valid.value
    }

    fun getValidationMessage() : String {
        return validationMessage.value
    }

    fun isChanged() : Boolean{
        return changed.value
    }

}