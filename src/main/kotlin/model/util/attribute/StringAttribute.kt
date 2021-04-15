package model.util.attribute

import model.FormModel
import java.lang.NumberFormatException
import kotlin.jvm.Throws

class StringAttribute(model: FormModel, value : String?) : Attribute<StringAttribute, String>(model, value) {

    private var minLength = 0
    private var maxLength = 1_000_000

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
    override fun checkAndSetValue(newVal : String?){
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
    private fun validatedValue(newVal: String){
        if (newVal.length < minLength){
            throw IllegalArgumentException("Validation mismatched (minLength)")
        }
        if (newVal.length > maxLength){
            throw IllegalArgumentException("Validation mismatched (maxLength)")
        }
    }

    //******************************************************************************************************
    //Setter

    /**
     * This method checks if the given value for minLength is positive and below the maxLength value.
     * If yes, the minLength is set and the current textValue is checked to see if it is still valid.
     *
     * @param maxLength : Int
     * @throws IllegalArgumentException
     * @return the called instance : StringAttribute
     */
    fun setMinLength(minLength : Int) : StringAttribute {
        if(minLength >= 0){
            if(minLength < maxLength){
                this.minLength = minLength
                checkAndSetValue(getValueAsText())
                return this
            }
            else{
                throw IllegalArgumentException("minLength is not lower than maxLength")
            }
        }else{
            throw IllegalArgumentException("minLength must be positive")
        }
    }

    /**
     * This method checks if the given value for maxLength is positive and above the minLength value.
     * If yes, the maxLength is set and the current textValue is checked to see if it is still valid.
     *
     * @param maxLength : Int
     * @throws IllegalArgumentException
     * @return the called instance : StringAttribute
    */
    fun setMaxLength(maxLength : Int) : StringAttribute {
        if(maxLength >= 0){
            if(maxLength > minLength){
                this.maxLength = maxLength
                checkAndSetValue(getValueAsText())
                return this
            }
            else{
                throw IllegalArgumentException("maxLength is not greater than minLength")
            }
        }else{
            throw IllegalArgumentException("maxLength must be grater than 0")
        }
    }


    //******************************************************************************************************
    //Getter

    fun getMinLength() : Int {
        return minLength
    }

    fun getMaxLength() : Int {
        return maxLength
    }
}