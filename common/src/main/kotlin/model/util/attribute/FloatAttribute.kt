package model.util.attribute

import model.FormModel
import model.convertibles.CustomConvertible
import model.meanings.Default
import model.meanings.SemanticMeaning
import model.util.ILabel
import model.validators.semanticValidators.SemanticValidator

class FloatAttribute<L>(    //required parameters
    model                   : FormModel,
    label                   : L,

                            //optional parameters
    value                   : Float?                            = null,
    required                : Boolean                           = false,
    readOnly                : Boolean                           = false,
    onChangeListeners       : List<(Float?) -> Unit>            = emptyList(),
    validators              : List<SemanticValidator<Float>>    = mutableListOf(),
    convertibles            : List<CustomConvertible>           = emptyList(),
    meaning                 : SemanticMeaning<Float>            = Default(),

    decimalPlaces           : Int                               = 8

) : FloatingPointAttribute<FloatAttribute<L>, Float,L>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, onChangeListeners = onChangeListeners, validators = validators, convertibles = convertibles,
    decimalPlaces = decimalPlaces, meaning = meaning)
        where L: Enum<*>, L : ILabel{

    override val typeT: Float
        get() = 0f

}