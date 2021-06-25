package model.util.attribute

import model.IModel
import model.convertibles.CustomConvertible
import model.meanings.Default
import model.meanings.SemanticMeaning
import model.util.ILabel
import model.validators.semanticValidators.SemanticValidator

class ShortAttribute<L>(//required parameters
    model                   : IModel,
    label                   : L,

                        //optional parameters
    value                   : Short?                            = null,
    required                : Boolean                           = false,
    readOnly                : Boolean                           = false,
    onChangeListeners       : List<ChangeListenerPair<Any?>>       = emptyList(),
    validators              : List<SemanticValidator<Short>>    = mutableListOf(),
    convertibles            : List<CustomConvertible>           = emptyList(),
    meaning                 : SemanticMeaning<Short>            = Default()

                        ) : NumberAttribute<ShortAttribute<L>, Short, L>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, onChangeListeners = onChangeListeners, validators = validators, convertibles = convertibles, meaning = meaning)
        where L: Enum<*>, L : ILabel{

    override val typeT: Short
        get() = 0

}