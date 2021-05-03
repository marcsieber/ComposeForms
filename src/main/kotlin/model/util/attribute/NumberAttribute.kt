package model.util.attribute

import model.FormModel
import model.util.ILabel
import kotlin.jvm.Throws

abstract class NumberAttribute <N,T,L> (model                               : FormModel,
                                        value                               : T?,
                                        label                               : L,
                                        required                            : Boolean,
                                        readOnly                            : Boolean,
                                        onChangeListeners                   : List<(T?) -> Unit>,

                                        lowerBound                          : T?,
                                        upperBound                          : T?,
                                        private var stepSize                : T,
                                        private var onlyStepValuesAreValid  : Boolean

) : Attribute<N,T,L>(model = model, value = value, label = label, required = required, readOnly = readOnly,
    onChangeListeners = onChangeListeners) where N : NumberAttribute<N,T,L>, T : Number, T : Comparable<T>, L: Enum<*>, L : ILabel{

    //******************************************************************************************************
    //Optional extra-properties for NumberAttributes

    private lateinit var lowerBound : T
    private lateinit var upperBound : T
    private val stepStart: T = value ?: toDatatype("0")

    /**
     * Initialize LowerBound and UpperBound
     */
    init {
        initializeLowerAndUpperBound(lowerBound = lowerBound, upperBound = upperBound)
    }

    //******************************************************************************************************
    //Validation

    /**
     * This method checks, if the value is valid regarding the optional extra-properties.
     * If it is not valid there will be thrown an exception.
     *
     * @throws IllegalArgumentException
     */
    protected fun validatedValue(newVal: T){
        if  (!(newVal in lowerBound..upperBound)){
            throw IllegalArgumentException("Validation mismatched (lowerBound/upperBound)")
        }

        if(isOnlyStepValuesAreValid()){
            val epsilon = 0.00000001
            val moduloOfNewVal      = ((newVal.toDouble()+(epsilon/10)) %  stepSize.toDouble())
            val moduloOfStepStart   = ((stepStart.toDouble()+(epsilon/10)) % stepSize.toDouble())

            if( moduloOfNewVal !in moduloOfStepStart-epsilon..moduloOfStepStart+epsilon){
                throw IllegalArgumentException("Validation mismatched (stepsize)")
            }
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
     */
    fun setLowerBound(lowerBound : T){

        if(lowerBound < upperBound) {
            this.lowerBound = lowerBound
            checkAndSetValue(getValueAsText())
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
     */
    fun setUpperBound(upperBound : T){
        if(upperBound > lowerBound){
            this.upperBound = upperBound
            checkAndSetValue(getValueAsText())
        }
        else {
            throw IllegalArgumentException("UpperBound is not greater than lowerBound")
        }
    }

    /**
     * This method checks if the stepSize is greater than 0.
     * If yes, the stepsize is set and the current textValue is checked to see if it is still valid.
     * If no, an exception is thrown
     *
     * @param stepSize : T
     * @throws IllegalArgumentException
     */
    fun setStepSize(stepSize: T){
        if(stepSize.toDouble() > 0.0){
            this.stepSize = stepSize
            checkAndSetValue(getValueAsText())
        }else{
            throw IllegalArgumentException("stepSize must be positive")
        }
    }

    /**
     * The onlyStepValuesAreValid value is set and the current textValue is checked to see if it is still valid.
     * @param onlyStepValuesAreValid: Boolean
     */
    fun setOnlyStepValuesAreValid(onlyStepValuesAreValid: Boolean){
        this.onlyStepValuesAreValid = onlyStepValuesAreValid
        checkAndSetValue(getValueAsText())
    }

    //******************************************************************************************************
    //Private Setter

    /**
     * This method sets the default values for the lowerBound and the upperBound depending on the
     * type of the NumberAttribute.
     */
    private fun initializeLowerAndUpperBound(lowerBound: T?, upperBound : T?){
        if(lowerBound === null){
            if (stepStart is Int){
                this.lowerBound = Int.MIN_VALUE as T
            }
            if( stepStart is Short){
                this.lowerBound = Short.MIN_VALUE as T
            }
            if( stepStart is Long){
                this.lowerBound = Long.MIN_VALUE as T
            }
            if( stepStart is Double){
                this.lowerBound = Double.MIN_VALUE as T
            }
            if( stepStart is Float){
                this.lowerBound = Float.MIN_VALUE as T
            }
        }else{
            this.lowerBound = lowerBound
        }

        if(upperBound === null){
            if (stepStart is Int){
                this.upperBound = Int.MAX_VALUE as T
            }
            if( stepStart is Short){
                this.upperBound = Short.MAX_VALUE as T
            }
            if( stepStart is Long){
                this.upperBound = Long.MAX_VALUE as T
            }
            if( stepStart is Double){
                this.upperBound = Double.MAX_VALUE as T
            }
            if( stepStart is Float){
                this.upperBound = Float.MAX_VALUE as T
            }
        }else{
            this.upperBound = upperBound
        }

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

    fun getStepStart() : T {
        return stepStart
    }

    fun isOnlyStepValuesAreValid() : Boolean{
        return onlyStepValuesAreValid
    }

    /**
     * This method casts the String castValue into the wished number type
     * @return castValue : wished number type
     */
    abstract fun toDatatype(castValue: String): T
}