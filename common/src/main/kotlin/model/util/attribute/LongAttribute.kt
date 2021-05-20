package model.util.attribute

import model.FormModel
import model.util.ILabel
import model.validators.semanticValidators.SemanticValidator

class LongAttribute<L>( model                   : FormModel,
                        value                   : Long? = null,
                        label                   : L,
                        required                : Boolean = false,
                        readOnly                : Boolean = false,
                        onChangeListeners       : List<(Long?) -> Unit> = emptyList(),
                        validators              : List<SemanticValidator<Long>> = mutableListOf()

) : NumberAttribute<LongAttribute<L>, Long, L>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, onChangeListeners = onChangeListeners, validators = validators)
        where L: Enum<*>, L : ILabel{

    override val typeT: Long
        get() = 0

}