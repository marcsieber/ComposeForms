package model.validators.semanticValidators

import model.validators.ValidationResult
import model.validators.Validator

class StringValidator(private var minLength         : Int = 0,
                      private var maxLength         : Int = 1_000_000,
                      validationMessage             : String = "")

    : SemanticValidator<String>(validationMessage = validationMessage) {

    init{
        init()
    }

    /**
     * This method can be used to overwrite a StringValidator that has already been set.
     * Only values that are not null will overwrite old values.
     * CheckDevValues() is called and the existing user inputs are checked again to see if they are still valid.
     *
     * @param minLength
     * @param maxLength
     * @param validationMessage
     */
    fun overrideStringValidator(minLength: Int? = null, maxLength: Int? = null, validationMessage: String? = null){
        if(minLength != null){
            this.minLength = minLength
        }
        if(maxLength != null){
            this.maxLength = maxLength
        }
        if(validationMessage != null){
            this.validationMessage = validationMessage
        }
        checkDevValues()
        attributes.forEach{it.revalidate()}
    }

    //******************************************************************************************************
    //Validation

    override fun validateUserInput(value: String?, valueAsText : String?): ValidationResult {
        val isValid = value!!.length in minLength..maxLength
        return ValidationResult(result = isValid, validationMessage = validationMessage)
    }

    //******************************************************************************************************
    //Exceptions & validation messages

    override fun setDefaultValidationMessage(){
        val ending = " characters."
        if(minLength == 0 && maxLength == 1_000_000){
            validationMessage = ""
        }
        else if(minLength == 0 && maxLength != 1_000_000){
            validationMessage = "The input must not contain more than " + maxLength + ending
        }
        else if(minLength != 0 && maxLength == 1_000_000){
            validationMessage = "The input must not contain less than " + minLength + ending
        }
        else if(minLength != 0 && maxLength != 1_000_000){
            validationMessage = "The input must contain between " + minLength + " and " + maxLength + ending
        }
    }

     override fun checkDevValues(){
        if(minLength >= 0){
            if(minLength < maxLength){
                if(maxLength >= 0){
                    if(maxLength < minLength){
                        throw IllegalArgumentException("maxLength is not greater than minLength")
                    }
                }else{
                    throw IllegalArgumentException("maxLength must be grater than 0")
                }
            }
            else{
                throw IllegalArgumentException("minLength is not lower than maxLength")
            }
        }else{
            throw IllegalArgumentException("minLength must be positive")
        }
    }

    //******************************************************************************************************
    //Getter

    fun getMinLength() : Int {
        return minLength
    }

    fun getMaxLength() : Int {
        return maxLength
    }

}

