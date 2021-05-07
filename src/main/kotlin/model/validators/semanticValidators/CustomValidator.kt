package model.validators.semanticValidators

import model.validators.ValidationResult
import model.validators.Validator

open class CustomValidator<T>(  private var validationFunction  : (T?) -> Boolean,
                                private var rightTrackFunction  : (T?) -> Boolean = {validationFunction(it)},
                                validationMessage               : String)

    : SemanticValidator<T>(validationMessage = validationMessage) {


    /**
     * This method can be used to overwrite a CustomValidator that has already been set.
     * Only values that are not null will overwrite old values.
     * The existing user inputs are checked again to see if they are still valid.
     *
     * @param decimalPlaces
     * @param validationMessage
     */
    fun overrideCustomValidator(validationFunction: ((T?) -> Boolean)? = null, validationMessage: String? = null){
        if(validationFunction != null){
            this.validationFunction = validationFunction
        }
        if(validationMessage != null){
            this.validationMessage = validationMessage
        }
        attributes.forEach{it.revalidate()}
    }

    //******************************************************************************************************
    //Validation

    override fun validateUserInput(value: T?, valueAsText : String?): ValidationResult {
        val res = validationFunction(value)
        val rightTrackValid = rightTrackFunction(value)
        return ValidationResult(res, rightTrackValid, validationMessage)
    }

    //not needed
    override fun checkAndSetDevValues(){}

    //******************************************************************************************************
    //Exceptions & validation messages

    //not needed
    override fun setDefaultValidationMessage(){}

    //not needed
    override fun setValues() {}

    //not needed
    override fun deleteCaches() {}


    //******************************************************************************************************
    //Getter

    fun getValidationFunction() : (T?) -> Boolean {
        return validationFunction
    }

}