package model.validators.semanticValidators

import model.validators.Validator

abstract class SemanticValidator<T>(validationMessage : String) : Validator<T>(validationMessage = validationMessage) {

    protected var validationMessageSetByDev : Boolean = !validationMessage.equals("")

    /**
     * The values to be set are first checked to see if they make sense. Then a default message is set if none was passed.
     */
    fun init(){
        checkAndSetDevValues()
        if(validationMessage.equals("")){
            setDefaultValidationMessage()
        }
    }

    /**
     * This method checks whether the values to set make sense or not.
     * If not, an IllegalException is thrown.
     * If yes, setValues is called.
     * Finally the temporary cache values are deleted again.
     *
     * @throws IllegalArgumentException
     */
    abstract fun checkAndSetDevValues()

    /**
     * This method writes the temporary set caches into the values.
     */
    abstract protected fun setValues()

    /**
     * This method sets all cache values to null.
     */
    abstract protected fun deleteCaches()
}