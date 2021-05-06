package model.util.attribute

import androidx.compose.runtime.mutableStateOf
import model.FormModel
import model.converters.TypeConverter
import model.util.ILabel
import model.validators.RequiredValidator
import model.validators.SyntaxValidator
import model.validators.ValidationResult
import model.validators.Validator
import model.validators.semanticValidators.SemanticValidator
import java.lang.NumberFormatException

abstract class Attribute <A,T,L> (private val model       : FormModel,
                                  private var value       : T?,
                                  label                   : L,
                                  required                : Boolean,
                                  readOnly                : Boolean,
                                  var onChangeListeners   : List<(T?) -> Unit>,
                                  var validators          : List<SemanticValidator<T>>

) where A : Attribute<A,T,L>, T : Any?, L: Enum<*>, L : ILabel {

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

    private val listOfValidationResults  = mutableStateOf<List<ValidationResult>>(emptyList())
    private val converter           = TypeConverter<T>()
    abstract val typeT        : T
    private val reqValidator        = RequiredValidator<T>(required)
    private val syntaxValidator     = SyntaxValidator<T>()


    /**
     * The attribute is passed to its model and to its validators so that they can access values of the attribute.
     */
    init{
        model.addAttribute(this)
        validators.forEach{it.addAttribute(this)}
        reqValidator.addAttribute(this)
        syntaxValidator.addAttribute(this)
        checkAndSetValue(getValueAsText())
    }

    //******************************************************************************************************
    //Public Setter

    /**
     * If the attribute is not readonly, valueAsText is set to the new value,
     * then setChanged is called to look if there are changes in comparison to the savedValue
     * and finally checkAndSetValue is called.
     *
     * If the input is a "Tab" nothing happens
     *
     * @param valueAsText : String
     */
    fun setValueAsText(valueAsText : String){
        if("\t" in valueAsText){
            return
        }
        if(!isReadOnly()){
            this.valueAsText.value = valueAsText
            setChanged(valueAsText)
            checkAndSetValue(valueAsText)
        }
    }

    /**
     * This method should only be called after KeyEvents to avoid inaccuracies of double or float values.
     *
     * If the attribute is not readonly, valueAsText is set to the new value,
     * then setChanged is called to look if there are changes in comparison to the savedValue
     * and finally checkAndSetValue is called.
     *
     * If the input is a "Tab" nothing happens
     *
     * @param valueAsText : String
     */
    fun setValueAsTextFromKeyEvent(valueAsText : String){
        if("\t" in valueAsText){
            return
        }
        if(!isReadOnly()){
            setChanged(valueAsText)
            checkAndSetValue(valueAsText, true)

            if(isValid()){
                this.valueAsText.value = getValue().toString()
            }else{
                this.valueAsText.value = valueAsText
            }
        }
    }

//    /**
//     * The required value is set and the current textValue is checked to see if it is still valid.
//     */
//    fun setRequired(isRequired : Boolean){
//        this.required.value = isRequired
//        checkAndSetValue(getValueAsText())
//    }

    /**
     * This method overwrites the ReuiredValidator with the passed Boolean and required is set.
     */
    fun setRequired(isRequired: Boolean){
        reqValidator.overrideRequiredValidator(isRequired)
        this.required.value = reqValidator.isRequired() //TODO only call required (mutable state)
    }

    /**
     *
     */
    fun isRequired(): Boolean { //private
        return required.value
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
     * @param language : String
     */
    internal fun setCurrentLanguage(language : String){
        labelAsText.value = label.getLabelInLanguage(this.label, language)
        currentLanguage = language
    }

    //******************************************************************************************************
    //Protected Setter

    /**
     * If the new value is unequal to the set value, the new value is set
     * and the changeListeners are informed about the value change.
     * @param value: T?
     */
    protected fun setValue(value: T?) {
        if(value != this.value){
            this.value = value
            onChangeListeners.forEach{it(value)}
        }
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
    //Getter

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

    fun getLabel() : String{
        return labelAsText.value
    }

    fun isReadOnly() : Boolean{
        return readOnly.value
    }

    fun isChanged() : Boolean{
        return changed.value
    }




    //******************************************************************************************************
    //Validation

    /**
     * This method checks if the new input value is valid.
     * If it is, the new value is set.
     * If it isn't, setValid(false) is called.
     *
     * @param newVal : String
     * @param calledFromKeyEvent : Boolean
     * @throws NumberFormatException
     * @throws IllegalArgumentException
     */
    protected fun checkAndSetValue(newVal: String?, calledFromKeyEvent : Boolean = false){
        if(newVal == null || newVal.equals("") || newVal.equals("[]")){
            if(this is SelectionAttribute){
                checkRequiredValidators(emptySet(), "[]")
                setValue(emptySet()) //TODO: only valid values in value (if(isValid()) ...), no solution yet for the situation with minNumberOfElements > 1 (only SelectionAttribute)
            }else{
                checkRequiredValidators(null, "")
                if(isValid()){setValue(null)}
            }
        } else {
            checkSyntaxValidators(newVal)
            if(this is SelectionAttribute){ //TODO: only valid values in value (if(isValid()) ...), no solution yet for the situation with minNumberOfElements > 1 (only SelectionAttribute)
                val typeValue = convert(newVal)
                if(!calledFromKeyEvent){ checkAllValidators(typeValue, newVal) }
                setValue(typeValue)
            }
            else{
                if(isValid()){
                    val typeValue = convert(newVal)
                    if(!calledFromKeyEvent){ checkAllValidators(typeValue, newVal) }
                    if(isValid()) setValue(typeValue)
                }
            }
        }
    }

    /**
     * Add a new validator to the attribute
     *
     * @param validator
     */
    fun addValidator(validator : SemanticValidator<T>){
        val tempList = validators.toMutableList()
        tempList.add(validator)
        validators = tempList
        validator.addAttribute(this)
    }


    /**
     * call checkAndSetValue to check if the user input is valid
     */
    fun revalidate(){
        checkAndSetValue(this.valueAsText.value)
    }

    /**
     * This method returns the validationMessages of all invalid validation results
     *
     * @return List<String>
     */
    fun getErrorMessages(): List<String>{
        return listOfValidationResults.value.filter{!it.result}.map{it.validationMessage}
    }

    fun isValid() : Boolean{
        return valid.value
    }

    /**
     * sets the listOfValidationResults checks if all results are true and calls setValid.
     */
    fun setListOfValidationResults(listOfValidationResults: List<ValidationResult>){
        this.listOfValidationResults.value = listOfValidationResults
        setValid(this.getListOfValidationResults().all { it.result })
    }

    fun getListOfValidationResults() : List<ValidationResult>{
        return listOfValidationResults.value
    }

    //Check validators:

    /**
     * This method checks, if the value is valid regarding all validators of this attribute.
     * The result is recorded in the validationResults
     */
    fun checkAllValidators(newVal: T, newValAsText : String) {
        setListOfValidationResults(validators.map { it.validateUserInput(newVal, newValAsText) })
    }

    /**
     * This method checks, if the value is valid regarding all required validators of this attribute.
     * The result is recorded in the validationResults
     */
    fun checkRequiredValidators(newVal : T?, newValAsText : String?){
        setListOfValidationResults(listOf(reqValidator.validateUserInput(newVal, newValAsText)))
        println("List: " + listOfValidationResults)
    }

    /**
     *
     */
    fun checkSyntaxValidators(newValAsText : String){
        setListOfValidationResults(listOf(syntaxValidator.validateUserInput(typeT, newValAsText)))
    }

    //Convert:

    /**
     *
     */
    fun convert(newValAsText: String) : T {
        println(typeT is Float)
        return converter.convertStringToType(newValAsText, typeT)
    }

}