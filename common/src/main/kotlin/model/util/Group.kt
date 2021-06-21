package model.util

import model.FormModel
import model.util.attribute.Attribute

class Group(val model : FormModel, val title : String, val attributes : List<Attribute<*, *, *>>) {

    init {
        model.addGroup(this)
    }

}