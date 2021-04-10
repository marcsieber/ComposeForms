package model.util.attribute

import model.FormModel
import java.lang.NumberFormatException
import kotlin.jvm.Throws

abstract class NumberAttribute <N,T> (model: FormModel, value : T) : Attribute<N,T>(model,value) where N : NumberAttribute<N,T>, T : Number, T : Comparable<T> {

    //******************************************************************************************************
    //Optional extra-properties for IntegerAttribute

    private lateinit var lowerBound : T
    private lateinit var upperBound : T
    private  var stepSize  = 1 as T
    private  val stepStart = value

    /**
     *
     */
    init {
        if (stepStart is Int){
            this.lowerBound = Int.MIN_VALUE as T
            this.upperBound = Int.MAX_VALUE as T
        }
    }

    //******************************************************************************************************
    //Validation

    /**
     * This method checks, if the value is valid regarding the optional extra-properties.
     * If it is not valid there will be thrown an exception.
     *
     * @throws IllegalArgumentException
     */
    @Throws(IllegalArgumentException :: class)
    protected fun validatedValue(newVal: T){
        if  (    !(newVal in lowerBound..upperBound
                    && (stepStart.toDouble() + newVal.toDouble()) %  stepSize.toDouble() == 0.0)){
            throw IllegalArgumentException("Validation mismatched (lowerBound/upperBound/stepSize)")
        }
    }

    //******************************************************************************************************
    //Public Setter

    /**
     * This method checks if the given value for lowerBound is below the upperBound value
     * If yes, the lowerbound value is set and the current textValue is checked to see if it is still valid.
     * If no, an exception is thrown
     *
     * @param lowerBound : Int
     * @throws IllegalArgumentException
     * @return the called instance : NumberAttribute
     */
    fun setLowerBound(lowerBound : T) : N {

        if(lowerBound < upperBound) {
            this.lowerBound = lowerBound
            checkAndSetValue(getValueAsText())
            return this as N
        }
        else {
            throw IllegalArgumentException("LowerBound is not lower than upperBound")
        }
    }

    /**
     * This method checks if the given value for upperBound is above the lowerBound value
     * If yes, the upperbound value is set and the current textValue is checked to see if it is still valid.
     * If no, an exception is thrown
     *
     * @param upperBound : Int
     * @throws IllegalArgumentException
     * @return the called instance : NumberAttribute
     */
    fun setUpperBound(upperBound : T) : N {
        if(upperBound > lowerBound){
            this.upperBound = upperBound
            checkAndSetValue(getValueAsText())
            return this as N
        }
        else {
            throw IllegalArgumentException("UpperBound is not greater than lowerBound")
        }
    }

    /**
     * The stepsize is set and the current textValue is checked to see if it is still valid.
     * @return the called instance : NumberAttribute
     */
    fun setStepSize(stepSize : T) : N {
        this.stepSize = stepSize
        checkAndSetValue(getValueAsText())
        return this as N
    }

    //******************************************************************************************************
    //Public Getter

    fun getLowerBound() : T {
        return lowerBound
    }

    fun getUpperBound() : T {
        return upperBound
    }

    fun getStepSize() : T {
        return stepSize
    }
}