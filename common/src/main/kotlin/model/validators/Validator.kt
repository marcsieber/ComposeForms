package model.validators

interface Validator<T> {
    fun validateUserInput(value : T) : ValidationResult
}