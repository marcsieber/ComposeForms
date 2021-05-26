package model.util.attribute

import model.FormModel
import model.convertables.CustomConvertable
import model.convertables.ReplacementPair
import model.util.ILabel
import model.validators.semanticValidators.SemanticValidator

abstract class NumberAttribute <N,T,L> (model                               : FormModel,
                                        value                               : T?,
                                        label                               : L,
                                        required                            : Boolean,
                                        readOnly                            : Boolean,
                                        onChangeListeners                   : List<(T?) -> Unit>,
                                        validators                          : List<SemanticValidator<T>>,
                                        convertables                        : List<CustomConvertable>

) : Attribute<N,T,L>(model = model, value = value, label = label, required = required, readOnly = readOnly,
    onChangeListeners = onChangeListeners, validators = validators, convertables = convertables)
        where N : NumberAttribute<N,T,L>, T : Number, T : Comparable<T>, L: Enum<*>, L : ILabel{


}