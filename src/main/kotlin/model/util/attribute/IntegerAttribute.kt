package model.util.attribute

import model.FormModel
import java.lang.NumberFormatException
import kotlin.jvm.Throws

class IntegerAttribute(model: FormModel, value : Int) : Attribute<IntegerAttribute>(model, value) {

    //******************************************************************************************************
    //Optional extra-properties for IntegerAttribute

    private var lowerBound          = Int.MIN_VALUE
    private var upperBound          = Int.MAX_VALUE
    private var stepSize            = 1
    private val stepStart           = value


    //******************************************************************************************************
    //Validation

    /**
     * This method checks if the new input value is valid.
     * If it is, the new value is set.
     * If it isn't, setValid(false) is called.
     *
     * @param newVal the new value as a String
     */
    override fun setValue(newVal : String){
        try{
            validatedValue(Integer.valueOf(newVal))
            setValid(true)
            setValidationMessage("Valid Input")
            setValue(Integer.valueOf(newVal))
        } catch (e : NumberFormatException){
            setValid(false)
            setValidationMessage("No Integer")
            e.printStackTrace()
        } catch (e : IllegalArgumentException){
            setValid(false)
            setValidationMessage(e.message.toString())
            e.printStackTrace()
        }
    }

    /**
     * This method checks, if the value is valid regarding the optional extra-properties.
     * If it is not valid there will be thrown an exception.
     *
     * @throws NumberFormatException
     */
    @Throws(IllegalArgumentException :: class)
    private fun validatedValue(newVal: Int){
        if  (    !(newVal in lowerBound..upperBound
                && (stepStart + newVal) %  stepSize == 0)){
            throw IllegalArgumentException("Validation mismatched (lowerBound/upperBound/stepSize)")
        }
    }


    //******************************************************************************************************
    //Public Setter

    /**
     * This method checks if the given value for lowerBound is below the upperBound value
     * If yes, the value is set
     * If no, an exception is thrown
     *
     * @param lowerBound : Int
     * @throws IllegalArgumentException
     * @return the called instance : IntegerAttribute
     */
    fun setLowerBound(lowerBound : Int) : IntegerAttribute{
        if(lowerBound < upperBound) {
            this.lowerBound = lowerBound
            return this
        }
        else {
            throw IllegalArgumentException("LowerBound is not lower than upperBound")
        }
    }

    /**
     * This method checks if the given value for upperBound is above the lowerBound value
     * If yes, the value is set
     * If no, an exception is thrown
     *
     * @param upperBound : Int
     * @throws IllegalArgumentException
     * @return the called instance : IntegerAttribute
     */
    fun setUpperBound(upperBound : Int) : IntegerAttribute{
        if(upperBound > lowerBound){
            this.upperBound = upperBound
            return this
        }
        else {
            throw IllegalArgumentException("UpperBound is not greater than lowerBound")
        }
    }

    fun setStepSize(stepSize : Int) : IntegerAttribute{
        this.stepSize = stepSize
        return this
    }

    //******************************************************************************************************
    //Public Getter

    fun getLowerBound() : Int {
        return lowerBound
    }

    fun getUpperBound() : Int {
        return upperBound
    }

    fun getStepSize() : Int {
        return stepSize
    }
}