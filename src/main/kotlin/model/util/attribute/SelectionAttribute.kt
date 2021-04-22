package model.util.attribute

import model.FormModel

class SelectionAttribute(model: FormModel,
                         value : Set<String> = emptySet<String>(),
                         label: String = "",
                         required: Boolean = false,
                         readOnly: Boolean = false,

                         private var minNumberOfSelections : Int = 1,
                         private var maxNumberOfSelections : Int = Int.MAX_VALUE,
                         private var possibleSelections : Set<String> = emptySet<String>()
)
    : Attribute<SelectionAttribute, Set<String>>(model = model, value = value, label = label, required = required, readOnly = readOnly) {

    //******************************************************************************************************
    //Validation

    override fun checkAndSetValue(newVal: String?, calledFromKeyEvent: Boolean) {
        val newValList = newVal?.toSet()
        if(newValList == emptySet<String>()){
            setNullValue(valueIsASet = true)
        } else {
            //todo validation
        }
    }

    //******************************************************************************************************
    //Setter

    /**
     * This method checks if the given value for minNumberOfSelections is positive and not greather than the maxNumberOfSelections value.
     * If yes, minNumberOfSelections is set and the current textValue is checked to see if it is still valid.
     *
     * @param minSel : Int
     * @throws IllegalArgumentException
     */
    fun setMinNumberOfSelections(minSel : Int) {
        if(minSel >= 1){
            if(minSel <= maxNumberOfSelections){
                this.minNumberOfSelections = minSel
                checkAndSetValue(getValue().toString())
            }else{
                throw IllegalArgumentException("MinNumberOfSelections is not lower than maxNumberOfSelections")
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
     * This method checks if the is not empty.
     * If yes, the possibleSelections-set is set and the current textValue is checked to see if it is still valid.
     *
     * @param selections : Int
     * @throws IllegalArgumentException
     */
    fun setPossibleSelections(selections : Set<String>){
        if(selections.size > 0){
            this.possibleSelections = selections
            checkAndSetValue(getValue().toString())
        }else{
            throw IllegalArgumentException("There are no selections in the set")
        }
    }

    /**
     * This method adds a new selection to the possibleSelections-set.
     * @param selection : String
     */
    fun addANewPossibleSelection(selection: String){
        this.possibleSelections.toMutableSet().add(selection)
    }

    /**
     * This method deletes a selection of the possibleSelections-set.
     * @param selection : String
     * @throws IllegalArgumentException
     */
    fun deleteAPossibleSelection(selection: String){
        if(possibleSelections.contains(selection)){
            this.possibleSelections.toMutableSet().remove(selection)
            if(getValue()!!.contains(selection)){
                getValue()!!.toMutableSet().remove(selection)
                checkAndSetValue(getValue().toString())
            }
        }
        else{
            throw IllegalArgumentException("There was no such selection in the possibleSelections-set")
        }
    }


    fun addSelection(value: String){
        var list = getValue() as Set<String>
        list = list.toMutableSet()
        list.add(value)
        this.setValue(list)
    }

    fun removeSelection(value: String){
        var list = getValue() as Set<String>
        list = list.toMutableSet()
        list.remove(value)
        this.setValue(list)
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