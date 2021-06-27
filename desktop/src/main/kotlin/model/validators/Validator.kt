package model.validators

import model.util.attribute.Attribute

abstract class Validator<T>(var validationMessage : String) {

    protected val attributes : MutableList<Attribute<*, *, *>> = mutableListOf()

    /**
     * This method adds an attribute to the validator.
     */
    fun addAttribute(attr : Attribute<*, *, *>){
        attributes.add(attr)
    }

    /**
     * This method checks if the user input is valid regarding the limits in the Validator.
     *
     * @param value
     * @param valueAsText
     * @return ValidationResult
     */
    abstract fun validateUserInput(value : T?, valueAsText : String?) : ValidationResult


    /**
     * This method sets a default validation message depending on the set limits in the Validator
     * if no validation message has been set by the developer.
     */
    abstract protected fun setDefaultValidationMessage()



}