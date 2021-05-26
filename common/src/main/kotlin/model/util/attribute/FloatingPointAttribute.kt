package model.util.attribute

import model.FormModel
import model.convertables.CustomConvertable
import model.convertables.ReplacementPair
import model.util.ILabel
import model.validators.semanticValidators.SemanticValidator

abstract class FloatingPointAttribute <F,T,L> (model                       : FormModel,
                                               value                       : T?,
                                               label                       : L,
                                               required                    : Boolean,
                                               readOnly                    : Boolean,
                                               onChangeListeners           : List<(T?) -> Unit>,
                                               validators                  : List<SemanticValidator<T>>,
                                               convertables                : List<CustomConvertable>,

                                               val decimalPlaces           : Int

) : NumberAttribute<F,T,L>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, onChangeListeners = onChangeListeners, validators = validators, convertables = convertables)
        where F : FloatingPointAttribute<F,T,L>, T : Number, T : Comparable<T>, L: Enum<*>, L : ILabel{

}