package model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import model.util.attribute.*
import java.util.*

abstract class BaseFormModel() : FormModel {

    //******************************************************************************************************
    //Properties

    private var title               = ""
    protected var allAttributes     = mutableStateListOf<Attribute<*,*,*>>()
    private var changedForAll       = mutableStateOf(false)
    private val validForAll         = mutableStateOf(true)
    private var currentLanguage     = mutableStateOf<String>("")


    //******************************************************************************************************
    //Functions called on user actions

    /**
     * This method saves all attributes, if all attributes are valid.
     * @return if the attributes where saved or not : Boolean
     */
    override fun saveAll(): Boolean {
        return if(isValidForAll()){
            allAttributes.forEach{ it.save() }
            true
        }else{
            false
        }
    }

    /**
     * This method undoes all attributes,
     * if there is at leased one change
     * @return if the attributes had changes and where undone or not : Boolean
     */
    override fun undoAll(): Boolean {
        return if(isChangedForAll()){
            allAttributes.forEach{ it.undo() }
            true
        }else{
            false
        }

    }

    /**
     * This method sets the currentLanguage for all attributes
     * @param lang : Locale
     */
    override fun setCurrentLanguageForAll(lang: String){
        currentLanguage.value = lang
        allAttributes.forEach{attribute -> attribute.setCurrentLanguage(lang) }
    }

    override fun validateAll() {
        allAttributes.forEach{it.revalidate()}
    }


    //******************************************************************************************************
    //Setter

    /**
     * This method checks if there is at least one attribute with a change.
     * If yes, changedForAll is set true. If not, changedForAll is set false.
     */
    override fun setChangedForAll(){
        changedForAll.value = allAttributes.stream().anyMatch(Attribute<*,*,*>::isChanged)
    }

    /**
     * This method checks if there is at least one attribute with a change.
     * If yes, changed is set true. If not, changed is set false.
     */
    override fun setValidForAll(){
        validForAll.value = allAttributes.stream().allMatch(Attribute<*,*,*>::isValid)
    }

    override fun setTitle(title: String){
        this.title = title
    }

    //******************************************************************************************************
    //Getter

    override fun getAttributes(): List<Attribute<*,*,*>> {
        return allAttributes
    }

    override fun getTitle(): String {
        return title
    }

    override fun isChangedForAll() : Boolean{
        return changedForAll.value
    }

    override fun isValidForAll() : Boolean{
        return validForAll.value
    }

    fun isCurrentLanguageForAll(language : String) : Boolean{
        return currentLanguage.value == language
    }

    override fun addAttribute(attr: Attribute<*,*,*>) {
        allAttributes.add(attr)
    }
}