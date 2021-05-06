package model.util.attribute

import model.FormModel
import model.util.ILabel
import model.validators.ValidationResult
import model.validators.Validator
import model.validators.semanticValidators.SemanticValidator
import java.lang.NumberFormatException

class IntegerAttribute<L>(model                    : FormModel,
                          value                    : Int? = null,
                          label                    : L,
                          required                 : Boolean = false,
                          readOnly                 : Boolean = false,
                          onChangeListeners        : List<(Int?) -> Unit> = emptyList(),
                          validators               : List<SemanticValidator<Int>> = mutableListOf(),

                          ) : NumberAttribute<IntegerAttribute<L>, Int, L>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, onChangeListeners = onChangeListeners, validators = validators)
        where L: Enum<*>, L : ILabel{

    override val typeT: Int
        get() = 0

}