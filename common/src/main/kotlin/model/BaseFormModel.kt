package model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.focus.FocusRequester
import communication.AttributeType
import model.util.attribute.*
import java.util.*

abstract class BaseFormModel() : FormModel {

    //******************************************************************************************************
    //Properties

    private var title               = ""
    protected var allAttributes     = mutableStateListOf<Attribute<*,*,*>>()
    private var changedForAll       = mutableStateOf(false)
    private val validForAll         = mutableStateOf(true)
    private var currentLanguage     = mutableStateOf<String>(if (getPossibleLanguages().size > 0) getPossibleLanguages()[0] else "")


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
        changedForAll.value = allAttributes.any(Attribute<*,*,*>::isChanged)
    }

    /**
     * This method checks if there is at least one attribute with a change.
     * If yes, changed is set true. If not, changed is set false.
     */
    override fun setValidForAll(){
        validForAll.value = allAttributes.all(Attribute<*,*,*>::isValid)
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

    override fun isCurrentLanguageForAll(language : String) : Boolean{
        return currentLanguage.value == language
    }

    override fun addAttribute(attr: Attribute<*,*,*>) {
        allAttributes.add(attr)
        if(getCurrentLanguage() != ""){
            attr.setCurrentLanguage(getCurrentLanguage())
        }
    }

    override fun getCurrentLanguage(): String {
        return currentLanguage.value
    }

    fun getAttributeType(attr: Attribute<*, *, *>): AttributeType {
        return AttributeType.OTHER
    }

    private var focusRequesters: MutableList<FocusRequester> = mutableListOf()
    private var currentFocusIndex = mutableStateOf(0)

    override fun getCurrentFocusIndex(): Int {
        return currentFocusIndex.value
    }

    override fun setCurrentFocusIndex(index: Int) {
        if(index != currentFocusIndex.value) {
            currentFocusIndex.value = index
            println("Focus index set: " + index)
            val attr: Attribute<*,*,*>? = getAttributeById(currentFocusIndex.value)
            if(attr != null) {
                attributeChanged(attr)
            }
        }

    }

    override fun addFocusRequester(fr: FocusRequester): Int {
        if(fr !in focusRequesters) {
            focusRequesters.add(fr)
            return focusRequesters.size -1
        }

        return -1
    }

    override fun focusNext() {
        currentFocusIndex.value = (currentFocusIndex.value + 1) % focusRequesters.size
        focusRequesters[currentFocusIndex.value].requestFocus()
    }

    override fun focusPrevious() {
        currentFocusIndex.value = (currentFocusIndex.value + focusRequesters.size - 1) % focusRequesters.size
        focusRequesters[currentFocusIndex.value].requestFocus()
    }

    /**
     * TODO: DO the things here instead of userdefined model
     */
    override fun validationChanged(attr: Attribute<*, *, *>) {
    }
    override fun attributeChanged(attr: Attribute<*, *, *>) {
    }
}