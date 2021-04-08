package model.util.attribute

import model.FormModel
import java.lang.NumberFormatException
import kotlin.jvm.Throws

abstract class NumberAttribute <N,T> (model: FormModel, value : T) : Attribute<N,T>(model,value) where N : NumberAttribute<N,T>, T : Number, T : Comparable<T> {

    //******************************************************************************************************
    //Optional extra-properties for IntegerAttribute

    lateinit var lowerBound : T //TODO: Getter!
    lateinit var upperBound : T
    lateinit var stepSize   : T
    private  val stepStart    = value


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
     * If yes, the value is set
     * If no, an exception is thrown
     *
     * @param lowerBound : Int
     * @throws IllegalArgumentException
     * @return the called instance : IntegerAttribute
     */
    fun setLowerBound(lowerBound : T) : N {
        if(lowerBound < upperBound) {
            this.lowerBound = lowerBound
            return this as N
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
    fun setUpperBound(upperBound : T) : N {
        if(upperBound > lowerBound){
            this.upperBound = upperBound
            return this as N
        }
        else {
            throw IllegalArgumentException("UpperBound is not greater than lowerBound")
        }
    }

    fun setStepSize(stepSize : T) : N {
        this.stepSize = stepSize
        return this as N
    }

    //******************************************************************************************************
    //Public Getter

//    fun getLowerBound() : T {
//        return lowerBound
//    }
//
//    fun getUpperBound() : T {
//        return upperBound
//    }
//
//    fun getStepSize() : T {
//        return stepSize
//    }
}