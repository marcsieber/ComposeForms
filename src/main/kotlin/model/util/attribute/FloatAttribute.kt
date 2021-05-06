package model.util.attribute

import model.FormModel
import model.util.ILabel
import model.validators.ValidationResult
import model.validators.Validator
import model.validators.semanticValidators.SemanticValidator
import java.lang.NumberFormatException

class FloatAttribute<L>(model                   : FormModel,
                        value                   : Float? = null,
                        label                   : L,
                        required                : Boolean = false,
                        readOnly                : Boolean = false,
                        onChangeListeners       : List<(Float?) -> Unit> = emptyList(),
                        validators              : List<SemanticValidator<Float>> = mutableListOf(),

                        decimalPlaces           : Int = 8

) : FloatingPointAttribute<FloatAttribute<L>, Float,L>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, onChangeListeners = onChangeListeners, validators = validators, decimalPlaces = decimalPlaces)
        where L: Enum<*>, L : ILabel{

    override val typeT: Float
        get() = 0f

}