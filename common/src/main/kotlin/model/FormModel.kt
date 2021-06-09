package model

import androidx.compose.ui.focus.FocusRequester
import model.util.attribute.Attribute

interface FormModel {
    fun getAttributes() : List<Attribute<*,*,*>>
    fun getTitle() : String
    fun setTitle(title: String)
    fun saveAll() : Boolean
    fun undoAll() : Boolean
    fun validateAll()
    fun setCurrentLanguageForAll(lang : String)
    fun isCurrentLanguageForAll(lang: String): Boolean
    fun getCurrentLanguage(): String
    fun setChangedForAll()
    fun setValidForAll()
    fun isValidForAll() : Boolean
    fun isChangedForAll(): Boolean
    fun addAttribute(attr: Attribute<*,*,*>)
    fun getPossibleLanguages(): List<String>

    fun getAttributeById(id: Int): Attribute<*,*,*>?{
        return getAttributes().find{ it.getId() == id}
    }

    fun attributeChanged(attr: Attribute<*,*,*>)
    fun validationChanged(attr: Attribute<*,*,*>)

    fun addFocusRequester(fr: FocusRequester): Int
    fun focusNext()
    fun focusPrevious()
    fun setCurrentFocusIndex(index: Int)
    fun getCurrentFocusIndex(): Int

}