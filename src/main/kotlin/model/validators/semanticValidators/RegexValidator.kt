package model.validators.semanticValidators

import model.validators.ValidationResult

class RegexValidator<T>(private var regexPattern          : String,
                        private var rightTrackRegexPattern: String? = null,
                        validationMessage                 : String = "")

    : SemanticValidator<T>(validationMessage = validationMessage) {

    init{
        init()
    }

    /**

     */
    fun overrideRegexValidator(regexPattern: String? = null, rightTrackRegexPattern: String? = null ,validationMessage: String? = null){
        if(regexPattern != null){
            this.regexPattern = regexPattern
        }
        if(rightTrackRegexPattern != null){
            this.rightTrackRegexPattern = rightTrackRegexPattern
        }
        if(validationMessage != null){
            this.validationMessage = validationMessage
            validationMessageSetByDev = !validationMessage.equals("")
        }
        setDefaultValidationMessage()
        attributes.forEach{it.revalidate()}
    }

    //******************************************************************************************************
    //Validation

    override fun validateUserInput(value: T?, valueAsText : String?): ValidationResult {
        val regex = regexPattern.toRegex()
        val isValid = if(valueAsText != null) regex.matches(valueAsText) else regex.matches("")

        var isValidSoft: Boolean
        if(rightTrackRegexPattern != null) {
            val softRegex = rightTrackRegexPattern!!.toRegex()
            isValidSoft = if (valueAsText != null) softRegex.matches(valueAsText) else regex.matches("")
        }else{
            isValidSoft = isValid
        }
        return ValidationResult(result = isValid, rightTrackResult = isValidSoft, validationMessage = validationMessage)
    }

    //TODO: Do this in base class if it is always the same!
    override fun checkAndSetDevValues(){
        //There is no check for regex and therefore it will be set directly
    }

    //******************************************************************************************************
    //Protected

    override fun setDefaultValidationMessage(){
        if(!validationMessageSetByDev){
            validationMessage = "Pattern does not match to $regexPattern"
        }
    }

    override fun setValues() {
        //There is no check for regex and therefore it will be set directly
    }

    override fun deleteCaches() {
        //There is no check for regex and therefore there is no cache
    }


    //******************************************************************************************************
    //Getter

    fun getRegexPattern() : String {
        return regexPattern
    }

}

