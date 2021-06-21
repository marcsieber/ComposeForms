package model.util.attribute

import model.FormModel
import model.convertables.CustomConvertable
import model.convertables.ReplacementPair
import model.meanings.Default
import model.meanings.SemanticMeaning
import model.util.ILabel
import model.validators.semanticValidators.SemanticValidator

class ShortAttribute<L>(//required parameters
                        model                   : FormModel,
                        label                   : L,

                        //optional parameters
                        value                   : Short?                            = null,
                        required                : Boolean                           = false,
                        readOnly                : Boolean                           = false,
                        onChangeListeners       : List<(Short?) -> Unit>            = emptyList(),
                        validators              : List<SemanticValidator<Short>>    = mutableListOf(),
                        convertables            : List<CustomConvertable>           = emptyList(),
                        meaning                 : SemanticMeaning<Short>            = Default()

                        ) : NumberAttribute<ShortAttribute<L>, Short, L>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, onChangeListeners = onChangeListeners, validators = validators, convertables = convertables, meaning = meaning)
        where L: Enum<*>, L : ILabel{

    override val typeT: Short
        get() = 0

}