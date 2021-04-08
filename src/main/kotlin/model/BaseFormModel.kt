package model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import model.util.attribute.Attribute
import model.util.attribute.IntegerAttribute
import java.util.*

abstract class BaseFormModel : FormModel {

    //******************************************************************************************************
    //Properties

    private var title               = ""
    private var allAttributes       = mutableStateListOf<Attribute<*>>()
    private var changedForAll       = mutableStateOf(false)
    private val validForAll         = mutableStateOf(true)
    private var currentLanguage     = mutableStateOf<Locale?>(null)


    //******************************************************************************************************
    //Functions called on user actions

    /**
     * This method saves all attributes, if all attributes are valid.
     * @return if the attributes where saved or not : Boolean
     */
    override fun saveAll(): Boolean {
        return if(isValidForAll()){
            allAttributes.forEach{attribute -> attribute.save()}
            println("savedAll")
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
            allAttributes.forEach{attribute -> attribute.undo()}
            true
        }else{
            false
        }

    }

    /**
     * This method sets the currentLanguage for all attributes
     * @param lang : Locale
     */
    override fun setCurrentLanguageForAll(lang: Locale){
        currentLanguage.value = lang
        allAttributes.forEach{attribute -> attribute.setCurrentLanguage(lang) }
    }

    //******************************************************************************************************
    //Methods to add attributes to model (must-do)

    /**
     * This method creates an integer attribute and the attribute is remembered.
     * @param value : Int
     * @return attr : IntegerAttribute
     */
    fun createIntegerAttribute(value : Int = 0) : IntegerAttribute{
        val attr = IntegerAttribute(this, value)
        allAttributes.add(attr)
        return attr
    }


    //******************************************************************************************************
    //Setter

    /**
     * This method checks if there is at least one attribute with a change.
     * If yes, changedForAll is set true. If not, changedForAll is set false.
     */
    override fun setChangedForAll(){
        changedForAll.value = allAttributes.stream().anyMatch(Attribute<*>::isChanged)
        println("Changed: " + changedForAll.value)
    }

    /**
     * This method checks if there is at least one attribute with a change.
     * If yes, changed is set true. If not, changed is set false.
     */
    override fun setValidForAll(){
        validForAll.value = allAttributes.stream().allMatch(Attribute<*>::isValid)
        println("Valid: " + validForAll.value)
    }

    override fun setTitle(title: String){
        this.title = title
    }

    //******************************************************************************************************
    //Getter

    override fun getAttributes(): List<Attribute<*>> {
        return allAttributes
    }

    override fun getTitle(): String {
        return title
    }

    fun isChangedForAll() : Boolean{
        return changedForAll.value
    }

    fun isValidForAll() : Boolean{
        return validForAll.value
    }

    fun isCurrentLanguageForAll(language : Locale) : Boolean{
        return currentLanguage.value == language
    }

}