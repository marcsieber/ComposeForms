package model.util.attribute

import model.FormModel
import model.convertables.CustomConvertable
import model.convertables.ReplacementPair
import model.meanings.Default
import model.meanings.SemanticMeaning
import model.util.ILabel
import model.validators.semanticValidators.SemanticValidator

class LongAttribute<L>(//required parameters
                       model                   : FormModel,
                       label                   : L,

                       //optional parameters
                       value                   : Long?                          = null,
                       required                : Boolean                        = false,
                       readOnly                : Boolean                        = false,
                       onChangeListeners       : List<(Long?) -> Unit>          = emptyList(),
                       validators              : List<SemanticValidator<Long>>  = mutableListOf(),
                       convertables            : List<CustomConvertable>        = emptyList(),
                       meaning                 : SemanticMeaning<Long>          = Default()

                       ) : NumberAttribute<LongAttribute<L>, Long, L>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, onChangeListeners = onChangeListeners, validators = validators, convertables = convertables, meaning = meaning)
        where L: Enum<*>, L : ILabel{

    override val typeT: Long
        get() = 0

}