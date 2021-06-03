package model.util.attribute

import androidx.compose.runtime.mutableStateOf
import model.FormModel
import model.convertables.ConvertableResult
import model.convertables.CustomConvertable
import model.convertables.ReplacementPair
import model.util.ILabel
import model.util.Utilities
import model.validators.RequiredValidator
import model.validators.SyntaxValidator
import model.validators.ValidationResult
import model.validators.semanticValidators.SemanticValidator
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Convert
import java.lang.NumberFormatException
import java.util.*

abstract class Attribute <A,T,L> (private val model       : FormModel,
                                  private var value       : T?,
                                  label                   : L,
                                  required                : Boolean,
                                  readOnly                : Boolean,
                                  var onChangeListeners   : List<(T?) -> Unit>,
                                  var validators          : List<SemanticValidator<T>>,
                                  var convertables        : List<CustomConvertable>
//                                  var formatter           : Formatter

) where A : Attribute<A,T,L>, T : Any?, L: Enum<*>, L : ILabel {

    //******************************************************************************************************
    //Properties

    private var savedValue          = value
    private val valueAsText         = mutableStateOf(value?.toString() ?: "")
    private val rightTrackValue     = mutableStateOf(value)

    private val label               = label
    private val labelAsText         = mutableStateOf("")
    private val required            = mutableStateOf(required)
    private val readOnly            = mutableStateOf(readOnly)
    private val valid               = mutableStateOf(true)
    private val rightTrackValid     = mutableStateOf(true)
    private val convertable         = mutableStateOf(false)
    private val changed             = mutableStateOf(false)
    private var currentLanguage     = ""

    private val listOfValidationResults  = mutableStateOf<List<ValidationResult>>(emptyList())
    abstract val typeT        : T
    private val reqValidator        = RequiredValidator<T>(required)
    private val syntaxValidator     = SyntaxValidator<T>()

    private val listOfConvertableResults = mutableStateOf<List<ConvertableResult>>(emptyList())


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

    /**
     * This method overwrites the RequiredValidator with the passed Boolean and required is set.
     */
    fun setRequired(isRequired: Boolean){
        reqValidator.overrideRequiredValidator(isRequired)
        this.required.value = reqValidator.isRequired()
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

    fun setRightTrackValue(newVal : T?){
        this.rightTrackValue.value = newVal
    }

    fun getRightTrackValue() : T? {
        return rightTrackValue.value
    }

    fun isRightTrackValid() : Boolean {
        return rightTrackValid.value
    }

    //******************************************************************************************************
    //Internal Setter //todo change internal

    /**
     * This method sets the savedValue to the current value
     * and makes the attribute "not changed" again.
     */
    internal fun save() : Boolean{
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
     * This method checks if the new input value is valid and rightTrackValid.
     * If it is valid, the value is set to the new input value.
     * If it is rightTrackValid, the rightTrackValue is set to the new input value.
     *
     * @param newVal : String
     * @param calledFromKeyEvent : Boolean
     * @throws NumberFormatException
     * @throws IllegalArgumentException
     */
    protected fun checkAndSetValue(newVal: String?, calledFromKeyEvent : Boolean = false, convertBecauseUnfocussed : Boolean = false){
        if(newVal == null || newVal.equals("") || newVal.equals("[]")){
            checkAndSetNullValue()
        }
        else {
            checkAndSetNonNullValue(newVal, calledFromKeyEvent, convertBecauseUnfocussed)
        }
    }

    /**
     * Check and sets the non null values
     */
    private fun checkAndSetNonNullValue(newVal: String, calledFromKeyEvent: Boolean, convertBecauseUnfocussed : Boolean) {
        val convertedValueAsText : String
        checkAllConvertables(newVal)
        if(convertable.value){
            convertedValueAsText = getConvertedValueAsText()[0]
        }else{
            convertedValueAsText = newVal
        }
        checkSyntaxValidators(convertedValueAsText)
        if (isValid()) {
            val typeValue = convertStringToType(convertedValueAsText)
            if (!calledFromKeyEvent) {
                checkAllValidators(typeValue, convertedValueAsText)
            }
            if (isValid()) {
                setValue(typeValue)
                if(!getConvertUserView().isNullOrEmpty()){
                    println("Hallo")
                    println(getConvertUserView())
                    if(getConvertUserView()[0] && (getConvertImmediately()[0] || convertBecauseUnfocussed)){
                        setValueAsText(getValue().toString())
                    }
                }
            }
            if (isRightTrackValid()) {
                setRightTrackValue(typeValue)
            }
        }
    }

    /**
     * Checks and sets the value to null
     */
    private fun checkAndSetNullValue() {
        var nullValue: T? = null
        var nullValueAsText = ""
        if (this is SelectionAttribute) {
            nullValue = emptySet<String>() as T
            nullValueAsText = "[]"
        }
        checkRequiredValidators(nullValue, nullValueAsText)
        if (isValid()) {
            setValue(nullValue)
        }
        if (isRightTrackValid()) {
            setRightTrackValue(nullValue)
        }
    }

    //******************************************
    //Functions in which vaidators are involved:

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
     * This method sets the listOfValidationResults, checks if all results are true and calls setValid.
     * @param listOfValidationResults
     */
    fun setListOfValidationResults(listOfValidationResults: List<ValidationResult>){
        this.listOfValidationResults.value = listOfValidationResults
        setValid(this.getListOfValidationResults().all { it.result })
        rightTrackValid.value = this.getListOfValidationResults().all{ it.rightTrackResult }
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
    }

    /**
     *
     */
    fun checkSyntaxValidators(newValAsText : String){
        setListOfValidationResults(listOf(syntaxValidator.validateUserInput(typeT, newValAsText)))
    }


    //******************************************
    //Functions in which convertables are involved:

    /**
     * This method sets the listOfConvertableResults, checks if any result is convertable (true) and sets convertable.
     * @param listOfConvertableResults
     */
    fun setListOfConvertableResults(listOfConvertableResults: List<ConvertableResult>){
        this.listOfConvertableResults.value = listOfConvertableResults
        this.convertable.value = (this.listOfConvertableResults.value.any { it.isConvertable })
    }

    /**
     * This method checks, if the value is convertable regarding all convertables of this attribute.
     * The result is recorded in the convertableResult
     */
    fun checkAllConvertables(newValAsText: String){
        setListOfConvertableResults(convertables.map { it.convertUserInput(newValAsText)})
    }

    /**
     * This method returns the convertedValueAsText of all convertable convertable results
     *
     * @return List<String>
     */
    fun getConvertedValueAsText(): List<String>{
        return listOfConvertableResults.value.filter{it.isConvertable}.map{it.convertedValueAsText}
    }

    fun getConvertUserView() : List<Boolean>{
        return listOfConvertableResults.value.filter{it.isConvertable}.map{it.convertUserView}
    }

    fun getConvertImmediately() : List<Boolean>{
        return listOfConvertableResults.value.filter{it.isConvertable}.map {it.convertImmediately}
    }

    /**
     * This method calls setAndCheckValue with the parameter convertBecauseUnfocussed = true.
     */
    fun checkAndSetConvertableBecauseUnfocussedAttribute(){
        checkAndSetValue(newVal = getValueAsText(), convertBecauseUnfocussed = true)
    }

    //******************************************
    //Convert:

    /**
     * This method converts a string into the type of the attribute.
     * @param newValAsText : String
     * @return typeT : T
     */
    fun convertStringToType(newValAsText: String) : T {
        return Utilities<T>().toDataType(newValAsText, typeT)
    }


}