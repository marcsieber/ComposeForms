package model.util.attribute

import model.FormModel
import model.convertables.CustomConvertable
import model.convertables.ReplacementPair
import model.meanings.SemanticMeaning
import model.util.ILabel
import model.validators.semanticValidators.SemanticValidator

abstract class FloatingPointAttribute <F,T,L> (//required parameters
                                               model                       : FormModel,
                                               label                       : L,

                                               //optional parameters
                                               value                       : T?,
                                               required                    : Boolean,
                                               readOnly                    : Boolean,
                                               onChangeListeners           : List<(T?) -> Unit>,
                                               validators                  : List<SemanticValidator<T>>,
                                               convertables                : List<CustomConvertable>,
                                               meaning                     : SemanticMeaning<T>,

                                               val decimalPlaces           : Int

) : NumberAttribute<F,T,L>(model = model, value = value, label = label, required = required, readOnly = readOnly,
    onChangeListeners = onChangeListeners, validators = validators, convertables = convertables, meaning = meaning)
        where F : FloatingPointAttribute<F,T,L>, T : Number, T : Comparable<T>, L: Enum<*>, L : ILabel{

}