package model.util.attribute

import model.FormModel
import model.convertables.CustomConvertable
import model.convertables.ReplacementPair
import model.util.ILabel
import model.validators.semanticValidators.SemanticValidator

class IntegerAttribute<L>(model                    : FormModel,
                          value                    : Int? = null,
                          label                    : L,
                          required                 : Boolean = false,
                          readOnly                 : Boolean = false,
                          onChangeListeners        : List<(Int?) -> Unit> = emptyList(),
                          validators               : List<SemanticValidator<Int>> = mutableListOf(),
                          convertables             : List<CustomConvertable> = emptyList(),

                          ) : NumberAttribute<IntegerAttribute<L>, Int, L>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, onChangeListeners = onChangeListeners, validators = validators, convertables = convertables)
        where L: Enum<*>, L : ILabel{

    override val typeT: Int
        get() = 0

}