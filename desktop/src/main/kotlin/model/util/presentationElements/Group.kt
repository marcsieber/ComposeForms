package model.util.presentationElements

import model.IModel
import model.util.attribute.Attribute

class Group(val model : IModel, val title : String, vararg fields : Field) {

    private val groupFields = fields.map { it }.toMutableList()
    private val groupAttributes = fields.map { it.getAttribute() }.toMutableList()


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