package model.util.presentationElements

import model.FormModel
import model.util.attribute.Attribute

class Group(val model : FormModel, val title : String, vararg field : Field) {

    private val groupFields = field.map { it }.toMutableList()
    private val groupAttributes = field.map { it.getAttribute() }.toMutableList()


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
        return groupAttributes
    }

    fun getFields(): List<Field>{
        return groupFields
    }
}