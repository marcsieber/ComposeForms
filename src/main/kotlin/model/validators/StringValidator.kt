package model.validators

class StringValidator(private var minLength: Int = 0, private var maxLength: Int = 1_000_000,
                      private var validationMessage: String = "", val onChange: () -> Unit = {}): Validator<String> {

    init{
        checkDevValues()
        if(validationMessage.equals("")){
            if(minLength == 0 && maxLength == 1_000_000){
                validationMessage = ""
            }
            else if(minLength == 0 && maxLength != 1_000_000){
                validationMessage = "The input must not contain more than " + maxLength + " characters."
            }
            else if(minLength != 0 && maxLength == 1_000_000){
                validationMessage = "The input must not contain less than " + minLength + " characters."
            }
            else if(minLength != 0 && maxLength != 1_000_000){
                validationMessage = "The input must contain between " + minLength + " and " + maxLength + " characters."
            }
        }
    }

    override fun validateUserInput(value: String): ValidationResult {
        val validation = value.length in minLength..maxLength
        return ValidationResult(result = validation, validationMessage = validationMessage)
    }

    private fun checkDevValues(){
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
        onChange()
    }

    fun setMinLength(min: Int){
        minLength = min
        checkDevValues()
        onChange()
    }

    fun setMaxLength(max: Int){
        maxLength = max
        checkDevValues()
        onChange()
    }

    fun setValidationMessage(msg: String){
        validationMessage = msg
        onChange()
    }
}