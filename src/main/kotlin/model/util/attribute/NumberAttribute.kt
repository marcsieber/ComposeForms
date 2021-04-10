package model.util.attribute

import model.FormModel
import org.junit.jupiter.api.Test
import java.lang.NumberFormatException
import kotlin.jvm.Throws

abstract class NumberAttribute <N,T> (model: FormModel, value : T) : Attribute<N,T>(model,value) where N : NumberAttribute<N,T>, T : Number, T : Comparable<T> {

    //******************************************************************************************************
    //Optional extra-properties for NumberAttributes

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
        if( stepStart is Short){
            this.lowerBound = Short.MIN_VALUE as T
            this.upperBound = Short.MAX_VALUE as T
        }
        if( stepStart is Double){
            this.lowerBound = Double.MIN_VALUE as T
            this.upperBound = Double.MAX_VALUE as T
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
        if  (!(newVal in lowerBound..upperBound)){
            throw IllegalArgumentException("Validation mismatched (lowerBound/upperBound)")
        }
        var stepSizeValid = ((stepStart.toDouble() + newVal.toDouble()) %  stepSize.toDouble())

        if(!(stepSizeValid in 0.0..0.001 || stepSizeValid in (stepSize.toDouble() - 0.001)..stepSize.toDouble())){
            throw IllegalArgumentException("Validation mismatched (stepsize)")
        }
    }

    //******************************************************************************************************
    //Public Setter

    /**
     * This method checks if the given value for lowerBound is below the upperBound value
     * If yes, the lowerbound value is set and the current textValue is checked to see if it is still valid.
     * If no, an exception is thrown
     *
     * @param lowerBound : T
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
     * @param upperBound : T
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

    //******************************************************************************************************
    //Converter

    /**
     * This method converts the comma in number inputs into a point.
     *
     * @param newValueAsText : String
     * @return newValueAsText : String
     */
    fun convertComma(newValueAsText : String) : String{
        if(newValueAsText.contains(",")){
            return newValueAsText.replace(",", ".")
        }
        return newValueAsText
    }
}