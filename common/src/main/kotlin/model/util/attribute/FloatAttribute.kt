package model.util.attribute

import model.FormModel
import model.convertables.CustomConvertable
import model.convertables.ReplacementPair
import model.util.ILabel
import model.validators.semanticValidators.SemanticValidator

class FloatAttribute<L>(model                   : FormModel,
                        value                   : Float? = null,
                        label                   : L,
                        required                : Boolean = false,
                        readOnly                : Boolean = false,
                        onChangeListeners       : List<(Float?) -> Unit> = emptyList(),
                        validators              : List<SemanticValidator<Float>> = mutableListOf(),
                        convertables            : List<CustomConvertable> = emptyList(),

                        decimalPlaces           : Int = 8

) : FloatingPointAttribute<FloatAttribute<L>, Float,L>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, onChangeListeners = onChangeListeners, validators = validators, convertables = convertables, decimalPlaces = decimalPlaces)
        where L: Enum<*>, L : ILabel{

    override val typeT: Float
        get() = 0f

}