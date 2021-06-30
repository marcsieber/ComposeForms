package model

import androidx.compose.ui.focus.FocusRequester
import model.util.presentationElements.Group
import model.util.attribute.Attribute

interface IModel {
    fun getIPAdress(): String

    fun getGroups() : List<Group>
    fun getTitle() : String
    fun setTitle(title: String)
    fun saveAll() : Boolean
    fun resetAll() : Boolean
    fun validateAll()
    fun setCurrentLanguageForAll(lang : String)
    fun isCurrentLanguageForAll(lang: String): Boolean
    fun getCurrentLanguage(): String
    fun setChangedForAll()
    fun setValidForAll()
    fun isValidForAll() : Boolean
    fun isChangedForAll(): Boolean
    fun addGroup(group: Group)
    fun getPossibleLanguages(): List<String>

    fun getAttributeById(id: Int?): Attribute<*,*,*>?{
        return getGroups().flatMap{it.getAttributes()}.find{it.getId() == id}
    }

    fun attributeChanged(attr: Attribute<*,*,*>)
    fun validationChanged(attr: Attribute<*,*,*>)
    fun textChanged(attr: Attribute<*,*,*>)

    fun addFocusRequester(fr: FocusRequester, attr: Attribute<*,*,*>): Int
    fun focusNext()
    fun focusPrevious()
    fun setCurrentFocusIndex(index: Int?)
    fun getCurrentFocusIndex(): Int?

    fun smartphoneOption() : Boolean

}