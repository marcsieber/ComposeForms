package model.validators.semanticValidators

import model.validators.Validator

abstract class SemanticValidator<T>(validationMessage : String) : Validator<T>(validationMessage = validationMessage) {
}