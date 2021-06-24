package model.util.attribute

import model.FormModel
import model.convertibles.CustomConvertible
import model.meanings.Default
import model.meanings.SemanticMeaning
import model.util.ILabel
import model.validators.semanticValidators.SemanticValidator

class IntegerAttribute<L>(//required parameters
    model                    : FormModel,
    label                    : L,

                          //optional parameters
    value                    : Int?                           = null,
    required                 : Boolean                        = false,
    readOnly                 : Boolean                        = false,
    onChangeListeners        : List<ChangeListenerPair<Any?>>    = emptyList(),
    validators               : List<SemanticValidator<Int>>   = mutableListOf(),
    convertibles             : List<CustomConvertible>        = emptyList(),
    meaning                  : SemanticMeaning<Int>           = Default()

) : NumberAttribute<IntegerAttribute<L>, Int, L>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, onChangeListeners = onChangeListeners, validators = validators, convertibles = convertibles,
    meaning = meaning)
        where L: Enum<*>, L : ILabel{

    override val typeT: Int
        get() = 0

}