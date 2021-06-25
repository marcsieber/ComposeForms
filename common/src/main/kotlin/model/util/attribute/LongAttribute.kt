package model.util.attribute

import model.IModel
import model.convertibles.CustomConvertible
import model.meanings.Default
import model.meanings.SemanticMeaning
import model.util.ILabel
import model.validators.semanticValidators.SemanticValidator

class LongAttribute<L>(//required parameters
    model                   : IModel,
    label                   : L,

                       //optional parameters
    value                   : Long?                          = null,
    required                : Boolean                        = false,
    readOnly                : Boolean                        = false,
    onChangeListeners       : List<ChangeListenerPair<Any?>>    = emptyList(),
    validators              : List<SemanticValidator<Long>>  = mutableListOf(),
    convertibles            : List<CustomConvertible>        = emptyList(),
    meaning                 : SemanticMeaning<Long>          = Default()

                       ) : NumberAttribute<LongAttribute<L>, Long, L>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, onChangeListeners = onChangeListeners, validators = validators, convertibles = convertibles, meaning = meaning)
        where L: Enum<*>, L : ILabel{

    override val typeT: Long
        get() = 0

}