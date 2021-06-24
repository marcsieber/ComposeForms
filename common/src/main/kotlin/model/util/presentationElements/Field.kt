package model.util.presentationElements

import model.util.attribute.Attribute

class Field(private val attribute : Attribute<*,*,*>, private val fieldSize : FieldSize = FieldSize.NORMAL) {

    fun getAttribute(): Attribute<*, *, *> {
        return attribute
    }

    fun getFieldSize(): FieldSize{
        return fieldSize
    }
}
