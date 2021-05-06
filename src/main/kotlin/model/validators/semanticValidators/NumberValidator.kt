package model.validators.semanticValidators

import model.util.Utilities
import model.validators.ValidationResult

class NumberValidator<T>(private var lowerBound              : T? = null,
                         private var upperBound              : T? = null,
                         private var stepSize                : T? = null,
                         private var stepStart               : T? = null,
                         private var onlyStepValuesAreValid  : Boolean = false,
                         validationMessage                   : String = ""

    ) : SemanticValidator<T>(validationMessage = validationMessage) where T : Number, T : Comparable<T>{

    private lateinit var typeT : T

    init {
        initializeTypeT()
        initializeLowerAndUpperBound(lowerBound = lowerBound, upperBound = upperBound)
        initializeStepStartAndSize(stepSize = stepSize, stepStart = stepStart)
        init()
    }

    /**
     * This method can be used to overwrite a NumberValidator that has already been set.
     * Only values that are not null will overwrite old values.
     * The existing user inputs are checked again to see if they are still valid.
     *
     * @param lowerBound
     * @param upperBound
     * @param stepSize
     * @param stepStart
     * @param onlyStepValuesAreValid
     * @param validationMessage
     */
    fun overrideNumberValidator(lowerBound: T? = null, upperBound: T? = null, stepSize: T? = null,
                                stepStart: T? = null, onlyStepValuesAreValid: Boolean? = null, validationMessage: String? = null){
        if(lowerBound != null){
            this.lowerBound = lowerBound
        }
        if(upperBound != null){
            this.upperBound = upperBound
        }
        if(stepSize != null){
            this.stepSize = stepSize
        }
        if(stepStart != null){
            this.stepStart = stepStart
        }
        if(onlyStepValuesAreValid != null){
            this.onlyStepValuesAreValid = onlyStepValuesAreValid
        }
        if(validationMessage != null){
            this.validationMessage = validationMessage
        }
        checkDevValues()
        attributes.forEach{it.revalidate()}
    }

    //******************************************************************************************************
    //Validation

    override fun validateUserInput(value: T?, valueAsText : String?): ValidationResult {
        var isValid = value!! in lowerBound!!..upperBound!!

        if(onlyStepValuesAreValid){
            val epsilon = 0.00000001
            val moduloOfNewVal      = ((value.toDouble()+(epsilon/10)) %  stepSize!!.toDouble())
            val moduloOfStepStart   = ((stepStart!!.toDouble()+(epsilon/10)) % stepSize!!.toDouble())

            if( moduloOfNewVal !in moduloOfStepStart-epsilon..moduloOfStepStart+epsilon){
                isValid = false
            }
        }

        return ValidationResult(isValid, validationMessage)
    }

    //******************************************************************************************************
    //Exceptions & validation messages

    override fun setDefaultValidationMessage() {
        var firstPart = ""
        var secondPart = ""
        if(lowerBound != Utilities<T>().getMinValueOfT(typeT) && upperBound != Utilities<T>().getMaxValueOfT(typeT)){
             firstPart = "The number must be between " + lowerBound + " and " + upperBound + ". "
        }
        else if(lowerBound != Utilities<T>().getMinValueOfT(typeT)){
            firstPart = "The number must be at least " + lowerBound + ". "
        }
        else if(upperBound != Utilities<T>().getMaxValueOfT(typeT)){
            firstPart = "The number must not be more than " + upperBound + ". "
        }

        if(onlyStepValuesAreValid){
            secondPart = "Only steps of " + stepSize + " are permitted, starting at " + stepStart + ". "
        }

        validationMessage = firstPart + secondPart
    }

    override fun checkDevValues() {
        if(lowerBound!! >= upperBound!!) {
            throw IllegalArgumentException("LowerBound is greater than upperBound")
        }
        if(stepSize!! <= Utilities<T>().toDataType("0", typeT)){
            throw IllegalArgumentException("stepSize must be positive")
        }
    }

    //******************************************************************************************************
    //Extra functions to initialize lower/upperBound

    /**
     *
     */
    private fun initializeTypeT(){
        if(lowerBound != null){
            typeT = lowerBound!!
        }
        else if(upperBound != null){
            typeT = upperBound!!
        }
        else if(stepStart != null){
            typeT = stepStart!!
        }
        else if(stepSize != null){
            typeT = stepSize!!
        }
    }

    /**
     *
     */
    private fun initializeStepStartAndSize(stepSize : T?, stepStart : T?){
        if(stepSize === null){
            this.stepSize = Utilities<T>().toDataType("1", typeT)
        }
        if(stepStart === null){
            this.stepStart = Utilities<T>().toDataType("0", typeT)
        }
    }


    /**
     * If no values have been passed for lower/upperBound,
     * default values are set for them depending on the type of the NumberAttribute.
     */
    private fun initializeLowerAndUpperBound(lowerBound: T?, upperBound : T?){
        if(lowerBound === null){
            this.lowerBound = Utilities<T>().getMinValueOfT(typeT)
        }else{
            this.lowerBound = lowerBound
        }

        if(upperBound === null){
            this.upperBound = Utilities<T>().getMaxValueOfT(typeT)
        }else{
            this.upperBound = upperBound
        }
    }

    //******************************************************************************************************
    //Getter

    fun getLowerBound() : T {
        return lowerBound!!
    }

    fun getUpperBound() : T {
        return upperBound!!
    }

    fun getStepSize() : T {
        return stepSize!!
    }

    fun getStepStart() : T {
        return stepStart!!
    }

    fun isOnlyStepValuesAreValid() : Boolean {
        return onlyStepValuesAreValid
    }
}