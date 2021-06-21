package model.util.attribute

import model.FormModel
import model.convertables.CustomConvertable
import model.convertables.ReplacementPair
import model.meanings.Default
import model.meanings.SemanticMeaning
import model.util.ILabel
import model.validators.semanticValidators.SemanticValidator

class DoubleAttribute<L>(   //required parameters
                            model                   : FormModel,
                            label                   : L,

                            //optional parameters
                            value                   : Double?                           = null,
                            required                : Boolean                           = false,
                            readOnly                : Boolean                           = false,
                            onChangeListeners       : List<(Double?) -> Unit>           = emptyList(),
                            validators              : List<SemanticValidator<Double>>   = mutableListOf(),
                            convertables            : List<CustomConvertable>           = emptyList(),
                            meaning                 : SemanticMeaning<Double>           = Default(),

                            decimalPlaces           : Int                               = 8

) : FloatingPointAttribute<DoubleAttribute<L>, Double,L>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, onChangeListeners = onChangeListeners, validators = validators, convertables = convertables,
    decimalPlaces = decimalPlaces, meaning = meaning)
    where L: Enum<*>, L : ILabel {

    override val typeT: Double
        get() = 0.0

}