package model

import model.util.attribute.Attribute
import java.util.*

interface FormModel {
    fun getAttributes() : List<Attribute<*,*>>
    fun getTitle() : String
    fun setTitle(title: String)
    fun saveAll() : Boolean
    fun undoAll() : Boolean
    fun setCurrentLanguageForAll(lang : Locale)
    fun setChangedForAll()
    fun setValidForAll()

}