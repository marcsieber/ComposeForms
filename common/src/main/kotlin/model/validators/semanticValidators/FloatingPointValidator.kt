package model.validators.semanticValidators

import model.validators.ValidationResult

class FloatingPointValidator<T>(    private var decimalPlaces   : Int = 10,
                                    validationMessage           : String = ""

) : SemanticValidator<T>(validationMessage = validationMessage) where T : Number, T : Comparable<T>  {

    private var decimalPlacesCache      : Int?      = decimalPlaces
    private var validationMessageCache  : String?   = validationMessage

    init {
        init()
    }

    /**
     * This method can be used to overwrite a FloatingPointValidator that has already been set.
     * Only parameter that are not null will overwrite the old values.
     * CheckDevValues() is called to check if the parameters make sense. If yes the values are set.
     * The default validation message is adapted if no validation message has been set by the developer.
     * Finally the existing user inputs are checked again to see if they are still valid.
     *
     * @param decimalPlaces
     * @param validationMessage
     */
    fun overrideSelectionValidator(decimalPlaces: Int? = null, validationMessage: String? = null){
        if(decimalPlaces != null){
            this.decimalPlacesCache = decimalPlaces
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

    override fun validateUserInput(value: T?, valueAsText : String?): ValidationResult {
        val splittedNumber = valueAsText!!.split(".")
        val isValid = if(splittedNumber.size == 2) splittedNumber[1].length <= decimalPlaces else splittedNumber.size == 1
        val rightTrackValid = isValid

        return ValidationResult(isValid, rightTrackValid, validationMessage)
    }

    override fun checkAndSetDevValues() {
        if(decimalPlacesCache != null && decimalPlacesCache!! < 1){ //todo: define max decimal places
            deleteCaches()
            throw IllegalArgumentException("number of decimal Places must be positive")
        }
        setValues()
        deleteCaches()
    }

    //******************************************************************************************************
    //Protected

    override fun setDefaultValidationMessage() {
        if(!validationMessageSetByDev){
            validationMessage = "Too many decimal places"
        }
    }

    override fun setValues(){
        if(decimalPlacesCache != null){
            this.decimalPlaces = decimalPlacesCache!!
        }
        if(validationMessageCache != null){
            this.validationMessage = validationMessageCache!!
        }
    }

    override fun deleteCaches(){
        this.decimalPlacesCache = null
        this.validationMessageCache = null
    }

    //******************************************************************************************************
    //Extra functions

    override fun toString(): String {
        return toString().format(String.format("%." + decimalPlaces + "f"));
    }

    //******************************************************************************************************
    //Getter

    fun getDecimalPlaces() : Int {
        return decimalPlaces
    }

}