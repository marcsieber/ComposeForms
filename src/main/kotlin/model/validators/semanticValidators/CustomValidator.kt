package model.validators.semanticValidators

import model.validators.ValidationResult
import model.validators.Validator

open class CustomValidator<T>(  private var validationFunction  : (T?) -> Boolean,
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
        return ValidationResult(res, validationMessage)
    }

    //******************************************************************************************************
    //Exceptions & validation messages

    override fun setDefaultValidationMessage(){}

    override fun checkDevValues(){}

    //******************************************************************************************************
    //Getter

    fun getValidationFunction() : (T?) -> Boolean {
        return validationFunction
    }
}