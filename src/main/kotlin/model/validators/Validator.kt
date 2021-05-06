package model.validators

import model.util.attribute.Attribute

abstract class Validator<T>(var validationMessage : String) {

    protected val attributes : MutableList<Attribute<*, *, *>> = mutableListOf()

    /**
     * The values to be set are first checked to see if they make sense. Then a default message is set if none was passed.
     */
    fun init(){
        checkDevValues()
        if(validationMessage.equals("")){
            setDefaultValidationMessage()
        }
    }

    /**
     * This method adds an attribute to the validator
     */
    fun addAttribute(attr : Attribute<*, *, *>){
        attributes.add(attr)
    }

    /**
     * This method checks if the user input is valid regarding the limits in the Validator.
     *
     * @param value
     * @param valueAsText
     * @return ValidationResult(isValid, validationMesssage)
     */
    abstract fun validateUserInput(value : T?, valueAsText : String?) : ValidationResult

    /**
     * This method sets a default validation message depending on the set limits in the Validator.
     */
    abstract protected fun setDefaultValidationMessage()

    /**
     * This method checks whether the values set make sense or not.
     * If not, an IllegalException is thrown.
     *
     * @throws IllegalArgumentException
     */
    abstract fun checkDevValues()

}