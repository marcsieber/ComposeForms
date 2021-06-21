package model.util.attribute

import model.FormModel
import model.convertables.CustomConvertable
import model.convertables.ReplacementPair
import model.meanings.Default
import model.meanings.SemanticMeaning
import model.util.ILabel
import model.validators.semanticValidators.SemanticValidator

class StringAttribute<L>(//required parameters
                         model               : FormModel,
                         label               : L,

                         //optional parameters
                         value               : String?                          = null,
                         required            : Boolean                          = false,
                         readOnly            : Boolean                          = false,
                         onChangeListeners   : List<(String?) -> Unit>          = emptyList(),
                         validators          : List<SemanticValidator<String>>  = mutableListOf(),
                         convertables        : List<CustomConvertable>          = emptyList(),
                         meaning             : SemanticMeaning<String>          = Default()

) : Attribute<StringAttribute<L>, String, L>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, onChangeListeners = onChangeListeners, validators = validators,
    convertables = convertables, meaning = meaning)
        where L: Enum<*>, L : ILabel{

    override val typeT: String
        get() = "0"

}