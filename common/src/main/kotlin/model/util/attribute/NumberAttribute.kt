package model.util.attribute

import model.IModel
import model.convertibles.CustomConvertible
import model.meanings.SemanticMeaning
import model.util.ILabel
import model.validators.semanticValidators.SemanticValidator

abstract class NumberAttribute <N,T,L> (//required parameters
    model                               : IModel,
    label                               : L,

                                        //optional parameters
    value                               : T?,
    required                            : Boolean,
    readOnly                            : Boolean,
    onChangeListeners                   : List<(T?) -> Unit>,
    validators                          : List<SemanticValidator<T>>,
    convertibles                        : List<CustomConvertible>,
    meaning                             : SemanticMeaning<T>

) : Attribute<N,T,L>(model = model, value = value, label = label, required = required, readOnly = readOnly,
    onChangeListeners = onChangeListeners, validators = validators, convertibles = convertibles, meaning = meaning)
        where N : NumberAttribute<N,T,L>, T : Number, T : Comparable<T>, L: Enum<*>, L : ILabel