package model.validators

class RequiredValidator<T>(private var isRequired           : Boolean = false,
                           validationMessage                : String = "")
    : Validator<T>(validationMessage = validationMessage) {


    init {
        init()
    }

    /**
     * This method can be used to overwrite a RequiredValidator that has already been set.
     * Only values that are not null will overwrite old values.
     * The existing user inputs are checked again to see if they are still valid.
     *
     * @param isRequired
     * @param validationMessage
     */
    fun overrideRequiredValidator(isRequired: Boolean? = null, validationMessage: String? = null){
        if(isRequired != null){
            this.isRequired = isRequired
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
        println("ValidateInput")
        val isValid : Boolean
        if(isRequired){
            isValid = (value != null && value != emptySet<String>() && value != "")
        }else{
            isValid = true
        }
        return ValidationResult(result = isValid, validationMessage = validationMessage)
    }

    //******************************************************************************************************
    //Exceptions & validation messages

    override fun setDefaultValidationMessage() {
        validationMessage = "Input required"
    }

    override fun checkDevValues(){}


    //******************************************************************************************************
    //Getter

    fun isRequired() : Boolean{
        return isRequired
    }

}