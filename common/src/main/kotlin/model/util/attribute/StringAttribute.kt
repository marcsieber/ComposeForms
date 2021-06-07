package model.util.attribute

import model.FormModel
import model.convertables.CustomConvertable
import model.convertables.ReplacementPair
import model.util.ILabel
import model.validators.semanticValidators.SemanticValidator

class StringAttribute<L>(model               : FormModel,
                         value               : String? = null,
                         label               : L,
                         required            : Boolean = false,
                         readOnly            : Boolean = false,
                         onChangeListeners   : List<(String?) -> Unit> = emptyList(),
                         validators          : List<SemanticValidator<String>> = mutableListOf(),
                         convertables        : List<CustomConvertable> = emptyList(),

                         ) : Attribute<StringAttribute<L>, String, L>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, onChangeListeners = onChangeListeners, validators = validators, convertables = convertables)
        where L: Enum<*>, L : ILabel{

    override val typeT: String
        get() = "0"

}