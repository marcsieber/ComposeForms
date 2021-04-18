package model.util.attribute

import model.FormModel
import java.lang.NumberFormatException

class FloatAttribute(
    model: FormModel,
    value : Float? = null,
    label: String = "",
    required: Boolean = false,
    readOnly: Boolean = false,

    lowerBound : Float? = null,
    upperBound : Float? = null,
    stepSize :   Float = 1f
) : NumberAttribute<FloatAttribute, Float>(model = model, value = value, label = label, required = required,
    readOnly = readOnly, lowerBound = lowerBound, upperBound = upperBound, stepSize = stepSize) {

    private var decimalPlaces = 1


    //TODO: use decimalPlaces

    //******************************************************************************************************
    //Validation

    /**
     * This method checks if the new input value is valid.
     * If it is, the new value is set.
     * If it isn't, setValid(false) is called.
     *
     * @param newVal : String
     * @throws NumberFormatException
     * @throws IllegalArgumentException
     */
    override fun checkAndSetValue(newVal: String?) {
        if(newVal == null){
            setNullValue()
        } else {
            try {
                validatedValue(newVal.toFloat())
                setValid(true)
                setValidationMessage("Valid Input")
                setValue(newVal.toFloat())
            } catch (e: NumberFormatException) {
                setValid(false)
                setValidationMessage("No Float")
                e.printStackTrace()
            } catch (e: IllegalArgumentException) {
                setValid(false)
                setValidationMessage(e.message.toString())
                e.printStackTrace()
            }
        }
    }

    //******************************************************************************************************
    //Getter & Setter

    fun setDecimalPlaces(decimalPlaces : Int) : FloatAttribute{
        this.decimalPlaces = decimalPlaces
        return this
    }

    fun getDecimalPlaces() : Int {
        return decimalPlaces
    }

    override fun toDatatype(castValue: String): Float {
        return castValue.toFloat()
    }
}