package model.validators.semanticValidators

import model.util.attribute.SelectionAttribute
import model.validators.ValidationResult

class SelectionValidator(   private var minNumberOfSelections  : Int = 0,
                            private var maxNumberOfSelections  : Int = Int.MAX_VALUE,
                            validationMessage                  : String = "")

    : SemanticValidator<Set<String>>(validationMessage = validationMessage){

    private var minNumberOfSelectionsCache : Int?       = minNumberOfSelections
    private var maxNumberOfSelectionsCache : Int?       = maxNumberOfSelections
    private var validationMessageCache     : String?    = validationMessage

    init{
        init()
        changeRequiredDependingOnMinNumberOfSelections()
    }

    /**
     * This method can be used to overwrite a SelectionValidator that has already been set.
     * Only parameter that are not null will overwrite the old values.
     * CheckDevValues() is called to check if the parameters make sense. If yes the values are set.
     * The default validation message is adapted if no validation message has been set by the developer.
     * Finally the existing user inputs are checked again to see if they are still valid.
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
            validationMessageSetByDev = !validationMessage.equals("")
        }
        checkAndSetDevValues()
        setDefaultValidationMessage()
        attributes.forEach{it.revalidate()}
    }

    //******************************************************************************************************
    //Validation

    override fun validateUserInput(value: Set<String>?, valueAsText: String?): ValidationResult {
        val isValid = value!!.size in minNumberOfSelections..maxNumberOfSelections
        return ValidationResult(isValid, validationMessage)
    }

    override fun checkAndSetDevValues() {
        if(maxNumberOfSelectionsCache != null && maxNumberOfSelectionsCache!! < 1){
            deleteCaches()
            throw IllegalArgumentException("MaxNumberOfSelections must at least 1")
        }
        if(minNumberOfSelectionsCache != null && minNumberOfSelectionsCache!! < 0) {
            deleteCaches()
            throw IllegalArgumentException("MinNumberOfSelections must be positive")
        }
        if(    (minNumberOfSelectionsCache != null && maxNumberOfSelectionsCache != null && minNumberOfSelectionsCache!! > maxNumberOfSelectionsCache!!)
            || (minNumberOfSelectionsCache != null && maxNumberOfSelectionsCache == null && minNumberOfSelectionsCache!! > maxNumberOfSelections)
            || (minNumberOfSelectionsCache == null && maxNumberOfSelectionsCache != null && minNumberOfSelections > maxNumberOfSelectionsCache!!)){
            deleteCaches()
            throw IllegalArgumentException("MinNumberOfSelections is higher than MaxNumberOfSelections")
        }
        for(attr in attributes) {
            attr as SelectionAttribute
            if (minNumberOfSelectionsCache != null && minNumberOfSelectionsCache!! > attr.getPossibleSelections().size) {
                deleteCaches()
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
        setValues()
        deleteCaches()
    }

    //******************************************************************************************************
    //Protected

    override fun setDefaultValidationMessage() {
        if(!validationMessageSetByDev){
            validationMessage = "Between " + minNumberOfSelections + " and " + maxNumberOfSelections + " elements must be selected."
        }
    }

    override fun setValues(){
        if(minNumberOfSelectionsCache != null){
            this.minNumberOfSelections = minNumberOfSelectionsCache!!
            changeRequiredDependingOnMinNumberOfSelections()            //TODO: Good decision?
        }
        if(maxNumberOfSelectionsCache != null){
            this.maxNumberOfSelections = maxNumberOfSelectionsCache!!
        }
        if(validationMessageCache != null){
            this.validationMessage = validationMessageCache!!
        }
    }

    override fun deleteCaches(){
        this.minNumberOfSelectionsCache = null
        this.maxNumberOfSelectionsCache = null
        this.validationMessageCache = null
    }

    //******************************************************************************************************
    //Extra

    /**
     * This method sets required to true or false depending on the value of minNumberOfSelections.
     */
    private fun changeRequiredDependingOnMinNumberOfSelections(){
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