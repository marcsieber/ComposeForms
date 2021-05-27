package model.util.attribute

import model.FormModel
import model.convertables.CustomConvertable
import model.convertables.ReplacementPair
import model.util.ILabel
import model.validators.semanticValidators.SemanticValidator

class DoubleAttribute<L>(model                   : FormModel,
                         value                   : Double? = null,
                         label                   : L,
                         required                : Boolean = false,
                         readOnly                : Boolean = false,
                         onChangeListeners       : List<(Double?) -> Unit> = emptyList(),
                         validators              : List<SemanticValidator<Double>> = mutableListOf(),
                         convertables            : List<CustomConvertable> = emptyList(),

                         decimalPlaces           : Int = 8

) : FloatingPointAttribute<DoubleAttribute<L>, Double,L>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, onChangeListeners = onChangeListeners, validators = validators, convertables = convertables, decimalPlaces = decimalPlaces)
    where L: Enum<*>, L : ILabel {

    override val typeT: Double
        get() = 0.0

}