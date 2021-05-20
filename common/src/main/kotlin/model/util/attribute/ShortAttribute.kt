package model.util.attribute

import model.FormModel
import model.util.ILabel
import model.validators.semanticValidators.SemanticValidator

class ShortAttribute<L>(model                   : FormModel,
                        value                   : Short? = null,
                        label                   : L,
                        required                : Boolean = false,
                        readOnly                : Boolean = false,
                        onChangeListeners       : List<(Short?) -> Unit> = emptyList(),
                        validators              : List<SemanticValidator<Short>> = mutableListOf()

) : NumberAttribute<ShortAttribute<L>, Short, L>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, onChangeListeners = onChangeListeners, validators = validators)
        where L: Enum<*>, L : ILabel{

    override val typeT: Short
        get() = 0

}