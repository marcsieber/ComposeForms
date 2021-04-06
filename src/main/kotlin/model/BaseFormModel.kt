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
    private var changed             = mutableStateOf(false)
    private val valid               = mutableStateOf(true)
    private var currentLanguage     = mutableStateOf<Locale?>(null)


    /**
     *
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

    override fun undoAll(): Boolean {
        return if(isChangedForAll()){
            allAttributes.forEach{attribute -> attribute.undo()}
            true
        }else{
            false
        }

    }

    //******************************************************************************************************
    //Methods to add attributes to model (must-do)

    fun createIntegerAttribute(value : Int = 0) : IntegerAttribute{
        val attr = IntegerAttribute(this, value)
        allAttributes.add(attr)
        return attr
    }


    //******************************************************************************************************
    //Setter

    override fun setChangedForAll(){
        changed.value = allAttributes.stream().anyMatch(Attribute<*>::isChanged)
        println("Changed: " + changed.value)
    }

    override fun setValidForAll(){
        valid.value = allAttributes.stream().allMatch(Attribute<*>::isValid)
        println("Valid: " + valid.value)
    }

    override fun setCurrentLanguageForAll(lang: Locale){
        currentLanguage.value = lang
        allAttributes.forEach{attribute -> attribute.setCurrentLanguage(lang) }
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
        return changed.value
    }

    fun isValidForAll() : Boolean{
        return valid.value
    }

    fun isCurrentLanguageForAll(language : Locale) : Boolean{
        return currentLanguage.value == language
    }

}