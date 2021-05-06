package model.util.attribute

import androidx.compose.runtime.mutableStateOf
import model.FormModel
import model.util.ILabel
import model.validators.Validator
import model.validators.semanticValidators.SemanticValidator
import java.lang.NumberFormatException
import kotlin.math.pow
import kotlin.math.roundToInt

abstract class FloatingPointAttribute <F,T,L> (model                       : FormModel,
                                               value                       : T?,
                                               label                       : L,
                                               required                    : Boolean,
                                               readOnly                    : Boolean,
                                               onChangeListeners           : List<(T?) -> Unit>,
                                               validators                  : List<SemanticValidator<T>>,

                                               val decimalPlaces           : Int

) : NumberAttribute<F,T,L>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, onChangeListeners = onChangeListeners, validators = validators)
        where F : FloatingPointAttribute<F,T,L>, T : Number, T : Comparable<T>, L: Enum<*>, L : ILabel{

}