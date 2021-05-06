package model.validators.semanticValidators

import model.validators.ValidationResult
import model.validators.Validator

class FloatingPointValidator<T>(    private var decimalPlaces   : Int = 10,
                                    validationMessage           : String = ""

) : SemanticValidator<T>(validationMessage = validationMessage) where T : Number, T : Comparable<T>  {

    init {
        init()
    }

    /**
     * This method can be used to overwrite a FloatingPointValidator that has already been set.
     * Only values that are not null will overwrite old values.
     * CheckDevValues() is called and the existing user inputs are checked again to see if they are still valid.
     *
     * @param decimalPlaces
     * @param validationMessage
     */
    fun overrideSelectionValidator(decimalPlaces: Int? = null, validationMessage: String? = null){
        if(decimalPlaces != null){
            this.decimalPlaces = decimalPlaces
        }
        if(validationMessage != null){
            this.validationMessage = validationMessage
        }
        checkDevValues()
        attributes.forEach{it.revalidate()}
    }

    //******************************************************************************************************
    //Validation

    override fun validateUserInput(value: T?, valueAsText : String?): ValidationResult {
        val splittedNumber = valueAsText!!.split(".")
        val isValid = if(splittedNumber.size == 2) splittedNumber[1].length <= decimalPlaces else true

        return ValidationResult(isValid, validationMessage)
    }

    //******************************************************************************************************
    //Exceptions & validation messages

    override fun setDefaultValidationMessage() {
        validationMessage = "Too many decimal places"
    }

    override fun checkDevValues() {
        if(decimalPlaces < 1){ //todo: define max decimal places
            throw IllegalArgumentException("number of decimal Places must be positive")
        }
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