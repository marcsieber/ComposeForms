package model.util

import model.FormModel
import model.util.attribute.Attribute

class Group(val model : FormModel, val title : String, vararg attributes :Attribute<*, *, *>) {

    private val groupAttributes = attributes.toMutableList()

    init {
        model.addGroup(this)
    }

    fun addAttribute(attribute : Attribute<*,*,*>){
        groupAttributes.add(attribute)
    }

    fun removeAttribute(attribute: Attribute<*, *, *>){
        groupAttributes.remove(attribute)
    }

    //**************
    //Getter

    fun getAttributes(): List<Attribute<*,*,*>>{
        return groupAttributes.toList()
    }
}