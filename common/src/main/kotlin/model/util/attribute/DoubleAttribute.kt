package model.util.attribute

import model.IModel
import model.convertibles.CustomConvertible
import model.meanings.Default
import model.meanings.SemanticMeaning
import model.util.ILabel
import model.validators.semanticValidators.SemanticValidator

class DoubleAttribute<L>(   //required parameters
    model                   : IModel,
    label                   : L,

                            //optional parameters
    value                   : Double?                           = null,
    required                : Boolean                           = false,
    readOnly                : Boolean                           = false,
    onChangeListeners       : List<(Double?) -> Unit>           = emptyList(),
    validators              : List<SemanticValidator<Double>>   = mutableListOf(),
    convertibles            : List<CustomConvertible>           = emptyList(),
    meaning                 : SemanticMeaning<Double>           = Default(),

    decimalPlaces           : Int                               = 8

) : FloatingPointAttribute<DoubleAttribute<L>, Double,L>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, onChangeListeners = onChangeListeners, validators = validators, convertibles = convertibles,
    decimalPlaces = decimalPlaces, meaning = meaning)
    where L: Enum<*>, L : ILabel {

    override val typeT: Double
        get() = 0.0

}