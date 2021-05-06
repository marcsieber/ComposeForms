package model.util.attribute

import model.FormModel
import model.util.ILabel
import model.validators.Validator

class StringAttribute<L>(model                 : FormModel,
                         value                 : String? = null,
                         label                 : L,
                         required              : Boolean = false,
                         readOnly              : Boolean = false,
                         onChangeListeners     : List<(String?) -> Unit> = emptyList(),

                         private val validators            : List<Validator<String>> = emptyList()

) : Attribute<StringAttribute<L>, String, L>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, onChangeListeners = onChangeListeners)
        where L: Enum<*>, L : ILabel{


    //******************************************************************************************************
    //Validation

    init{
        checkAndSetValue(value) //only on String, all the other have to be in bounds on first creation
    }

    /**
     * This method checks if the new input value is valid.
     * If it is, the new value is set.
     * If it isn't, setValid(false) is called.
     *
     * @param newVal : String
     * @throws NumberFormatException
     * @throws IllegalArgumentException
     */
    override fun checkAndSetValue(newVal : String?, calledFromKeyEvent : Boolean){
        if(newVal == null){
            setNullValue()
        } else {
            try {
                validatedValue(newVal)
                setValid(true)
                setValidationMessage("Valid Input")
                setValue(newVal)
            } catch (e: IllegalArgumentException) {
                setValid(false)
                setValidationMessage(e.message.toString())
                e.printStackTrace()
            }
        }
    }

    /**
     * This method checks, if the value is valid regarding the optional extra-properties.
     * If it is not valid there will be thrown an exception.
     *
     * @throws IllegalArgumentException
     */
    @Throws(IllegalArgumentException :: class)
    fun validatedValue(newVal: String?) {

        if (newVal != null) {
            validationResults.value = validators.map { it.validateUserInput(newVal) }
        }
    }
}