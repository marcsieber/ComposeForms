package model.validators.semanticValidators

import model.util.attribute.SelectionAttribute
import model.validators.ValidationResult
import model.validators.Validator

class SelectionValidator(   private var minNumberOfSelections  : Int = 0,
                            private var maxNumberOfSelections  : Int = Int.MAX_VALUE,
                            validationMessage                  : String = "")

    : SemanticValidator<Set<String>>(validationMessage = validationMessage){

    private var minNumberOfSelectionsCache : Int? = null
    private var maxNumberOfSelectionsCache : Int? = null
    private var validationMessageCache     : String? = null

    init{
        init()
        changeRequiredDependingOnMinNumberOfSelections()
    }

    /**
     * This method can be used to overwrite a SelectionValidator that has already been set.
     * Only values that are not null will overwrite old values.
     * CheckDevValues() is called and the existing user inputs are checked again to see if they are still valid.
     *
     * @param minNumberOfSelections
     * @param maxNumberOfSelections
     * @param validationMessage
     */
    fun overrideSelectionValidator(minNumberOfSelections: Int? = null, maxNumberOfSelections: Int? = null, validationMessage: String? = null){
        if(minNumberOfSelections != null){
            this.minNumberOfSelectionsCache = minNumberOfSelections
        }
        if(maxNumberOfSelections != null){
            this.maxNumberOfSelectionsCache = maxNumberOfSelections
        }
        if(validationMessage != null){
            this.validationMessageCache = validationMessage
        }
        checkDevValues()
        setValuesAndDeleteCaches()
        attributes.forEach{it.revalidate()}
    }

    //******************************************************************************************************
    //Validation

    override fun validateUserInput(value: Set<String>?, valueAsText: String?): ValidationResult {
        val isValid = value!!.size in minNumberOfSelections..maxNumberOfSelections
        println("SlectionValidation : " + isValid)
        return ValidationResult(isValid, validationMessage)
    }

    //******************************************************************************************************
    //Exceptions & validation messages

    override fun setDefaultValidationMessage() {
        validationMessage = "Between " + minNumberOfSelections + " and " + maxNumberOfSelections + " elements must be selected."
    }

    override fun checkDevValues() {
        if(maxNumberOfSelectionsCache != null && maxNumberOfSelectionsCache!! < 1){
            setValuesAndDeleteCaches()
            throw IllegalArgumentException("MaxNumberOfSelections must be positive")
        }
        if(minNumberOfSelectionsCache != null && minNumberOfSelectionsCache!! < 0) {
            setValuesAndDeleteCaches()
            throw IllegalArgumentException("MinNumberOfSelections must be positive")
        }
        if (minNumberOfSelectionsCache != null && minNumberOfSelectionsCache!! > maxNumberOfSelections) {
            setValuesAndDeleteCaches()
            throw IllegalArgumentException("MinNumberOfSelections is higher than maxNumberOfSelections")
        }
        for(attr in attributes) {
            attr as SelectionAttribute
            if (minNumberOfSelectionsCache != null && minNumberOfSelectionsCache!! > attr.getPossibleSelections().size) {
                setValuesAndDeleteCaches()
                throw IllegalArgumentException("MinNumberOfSelections is higher than the number of possible elements to select")
            }
        }
        if(minNumberOfSelectionsCache == null && maxNumberOfSelectionsCache == null && validationMessageCache == null){ //For the check if PossibleSelections of attribute have been changed
            for(attr in attributes) {
                attr as SelectionAttribute
                if ( minNumberOfSelections > attr.getPossibleSelections().size) {
                    throw IllegalArgumentException("MinNumberOfSelections is now higher than the number of possible elements to select! Change minNumberOfSelection or add an element to possibleSelctions again.")
                }
            }
        }
    }

    fun setValuesAndDeleteCaches(){
        if(minNumberOfSelectionsCache != null){
            this.minNumberOfSelections = minNumberOfSelectionsCache!!
            this.minNumberOfSelectionsCache = null
            changeRequiredDependingOnMinNumberOfSelections()
        }
        if(maxNumberOfSelectionsCache != null){
            this.maxNumberOfSelections = maxNumberOfSelectionsCache!!
            maxNumberOfSelectionsCache = null
        }
        if(validationMessageCache != null){
            this.validationMessage = validationMessageCache!!
            validationMessageCache = null
        }
    }

    //******************************************************************************************************
    //Extra

    fun changeRequiredDependingOnMinNumberOfSelections(){
        if(minNumberOfSelections > 0){
            attributes.forEach{it.setRequired(true)}
        }
        if(minNumberOfSelections == 0){
            attributes.forEach{it.setRequired(false)}
        }
    }

    //******************************************************************************************************
    //Getter

    fun getMinNumberOfSelections() : Int {
        return minNumberOfSelections
    }

    fun getMaxNumberOfSelections() : Int {
        return maxNumberOfSelections
    }
}