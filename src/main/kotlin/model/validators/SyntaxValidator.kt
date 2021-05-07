package model.validators

import model.util.Utilities
import java.lang.NumberFormatException

class SyntaxValidator<T>( validationMessage   : String = ""

) : Validator<T>(validationMessage = validationMessage) {

    init {
        if(validationMessage.equals("")){
            setDefaultValidationMessage()
        }
    }

    //******************************************************************************************************
    //Validation

    override fun validateUserInput(value: T?, valueAsText: String?): ValidationResult {
        //value is here typeT (only used for type checks)
        var isValid : Boolean
        try {
            Utilities<T>().toDataType(valueAsText!!, value!!)
            isValid = true
        }catch(e : NumberFormatException){
            isValid = false
        }
        return ValidationResult(isValid, validationMessage)
    }

    //******************************************************************************************************
    //Protected

    override fun setDefaultValidationMessage() {
        validationMessage = "This is not the correct input type"
    }


}