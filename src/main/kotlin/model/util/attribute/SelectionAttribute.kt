package model.util.attribute

import model.FormModel

class SelectionAttribute(model                              : FormModel,
                         value                              : Set<String> = emptySet<String>(),
                         label                              : String = "",
                         required                           : Boolean = false,
                         readOnly                           : Boolean = false,
                         onChangeListeners                  : List<(Set<String>?) -> Unit> = emptyList(),

                         private var minNumberOfSelections  : Int = 0,
                         private var maxNumberOfSelections  : Int = Int.MAX_VALUE,
                         possibleSelections                 : Set<String>

) : Attribute<SelectionAttribute, Set<String>>(model = model, value = value, label = label, required = required, readOnly = readOnly,
    onChangeListeners = onChangeListeners) {

    //******************************************************************************************************
    //Properties

    private var possibleSelections = possibleSelections.toMutableSet()

    //******************************************************************************************************
    //Validation

    /**
     * This method checks if the new input value is valid.
     * If it is, the new value is set.
     * If it isn't, setValid(false) is called.
     *
     * @param newVal : String?
     * @param calledFromKeyEvent: Boolean
     * @throws NumberFormatException
     * @throws IllegalArgumentException
     */
    override fun checkAndSetValue(newVal: String?, calledFromKeyEvent: Boolean) {
            try{
                if(newVal == null || newVal.equals("[]")){
                    setNullValue(valueIsASet = true)
                    validatedValue(setOf<String>().toMutableSet())
                }else{
                    val setVal = convertStringToSet(newVal)
                    setValid(true)
                    setValidationMessage("Valid Input")
                    setValue(setVal)
                    validatedValue(setVal)
                }
            }  catch (e: NumberFormatException) {
                setValid(false)
                setValidationMessage(e.message.toString())
                e.printStackTrace()
            } catch (e: IllegalArgumentException) {
                setValid(false)
                setValidationMessage(e.message.toString())
                e.printStackTrace()
            }
    }

    init{
        checkAndSetValue(getValueAsText());
    }

    /**
     * This method converts a String into a MutableSet<String>.
     * If this is not possible,  a Numberformatexception is thrown
     * @param newVal : String
     * @return newVal : Double
     * @throws NumberFormatException
     */
    private fun convertStringToSet(newVal: String) : MutableSet<String>{
        val set = newVal.substring(1,newVal.length-1).split(", ")
        return set.toMutableSet()
    }

    /**
     * This method checks if the new value is greater than the minNumberOfSelections
     * and lower than the maxNumberOfSelections.
     * @param newVal : MutableSet<String>
     * @throws IllegalArgumentException
     */
    private fun validatedValue(newVal : MutableSet<String>){
            if (!(newVal.size in minNumberOfSelections..maxNumberOfSelections)) {
                throw IllegalArgumentException("Validation mismatched (min/max number of selections)") //todo message statt Exception
            }
    }


    //******************************************************************************************************
    //Functions that are called on user actions

    /**
     * This function checks if the value is in the set of possible selections.
     * If yes, it creates a new user set containing all the values already selected by the user plus the new value.
     * The newly formed set is then passed to the setValueAsText function.
     * @param value : String
     */
    fun addUserSelection(value: String){
        if(possibleSelections.contains(value)){
            var newSet = getValue() as Set<String>
            newSet = newSet.toMutableSet()
            newSet.add(value)
            setValueAsText(newSet.toString())
        }
        else {
            setValueAsText(getValue().toString())
            throw IllegalArgumentException("There was no such selection to choose") //todo: delete? because it will be overwritten with "Valid Input"
        }
    }

    /**
     * This function creates a new user set containing all the values already selected by the user minus the new value.
     * The newly formed set is then passed to the setValueAsText function.
     * @param value : String
     */
    fun removeUserSelection(value: String){
        val newSet : MutableSet<String> = getValue()!!.toMutableSet()
        newSet.remove(value)
        setValueAsText(newSet.toString())
    }

    //******************************************************************************************************
    //Setter

    /**
     * This method checks if the given value for minNumberOfSelections is positive, lower than the possible selections size
     * and not greater than the maxNumberOfSelections value
     * If yes, minNumberOfSelections is set and the current textValue is checked to see if it is still valid.
     *
     * @param minSel : Int
     * @throws IllegalArgumentException
     */
    fun setMinNumberOfSelections(minSel : Int) {
        if(minSel >= 1){
            if(minSel <= possibleSelections.size){
                if(minSel <= maxNumberOfSelections){
                    this.minNumberOfSelections = minSel
                    checkAndSetValue(getValue().toString())
                }else{
                    throw IllegalArgumentException("MinNumberOfSelections is not lower than maxNumberOfSelections")
                }
            }else{
                throw IllegalArgumentException("MinNumberOfSelections is higher than the number of possible elements to select")
            }
        }else{
            throw IllegalArgumentException("MinNumberOfSelections must be positive")
        }
    }

    /**
     * This method checks if the given value for maxNumberOfSelections is positive and not lower than the minNumberOfSelections value.
     * If yes, maxNumberOfSelections is set and the current textValue is checked to see if it is still valid.
     *
     * @param maxSel : Int
     * @throws IllegalArgumentException
     */
    fun setMaxNumberOfSelections(maxSel : Int) {
        if(maxSel >= 1){
            if(maxSel >= minNumberOfSelections){
                this.maxNumberOfSelections = maxSel
                checkAndSetValue(getValue().toString())
            }else{
                throw IllegalArgumentException("MaxNumberOfSelections is not greater than minNumberOfSelections")
            }
        }else{
            throw IllegalArgumentException("MaxNumberOfSelections must be positive")
        }
    }

    /**
     * This method checks if the set of selections is not empty and if the set of selections size is higher than minNumberOfSelections.
     * If so, the possibleSelections-set is set and the current textValue is checked to see if it is still valid.
     *
     * @param selections : Int
     * @throws IllegalArgumentException
     */
    fun setPossibleSelections(selections : Set<String>){
        if(selections.size > 0){
            if(selections.size >= minNumberOfSelections){
                this.possibleSelections = selections.toMutableSet() //todo: find out how .value can be used (change possibleSelections from var to val)
                checkAndSetValue(getValue().toString())
            }else{
                throw IllegalArgumentException("The number of possible elements to select is lower than minNumberOfSelections")
            }
        }else{
            throw IllegalArgumentException("There are no selections in the set")
        }
    }

    /**
     * This method adds a new selection to the possibleSelections-set.
     * @param selection : String
     */
    fun addANewPossibleSelection(selection: String){
        this.possibleSelections.add(selection)
    }

    /**
     * This method deletes a selection of the possibleSelections-set.
     * It is checked whether this element is already selected by the user and if the minNumberOfSelections is low enough to delete an element.
     * If so, it is removed from the user value list and checked whether the newly created user value list is still valid.
     *
     * @param selection : String
     * @throws IllegalArgumentException
     */
    fun removeAPossibleSelection(selection: String){
        if(possibleSelections.contains(selection)){
            if(possibleSelections.size > minNumberOfSelections){
                this.possibleSelections.remove(selection)
                if(getValue()!!.contains(selection)){
                    getValue()!!.toMutableSet().remove(selection)
                    checkAndSetValue(getValue().toString())
                }
            }else{
                throw IllegalArgumentException("The number of possible elements to select would be lower than minNumberOfSelections after removing an element")
            }
        }
        else{
            throw IllegalArgumentException("There was no such selection in the possibleSelections-set")
        }
    }

    //******************************************************************************************************
    //Public Getter

    fun getMinNumberOfSelections() : Int {
        return minNumberOfSelections
    }

    fun getMaxNumberOfSelections() : Int {
        return maxNumberOfSelections
    }

    fun getPossibleSelections() : Set<String> {
        return possibleSelections
    }

}