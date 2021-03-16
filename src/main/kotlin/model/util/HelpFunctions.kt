package model.util

import IntegerAttribute

abstract class HelpFunctions {

    fun createIntegerAttr(value : Int): IntegerAttribute {
        var attr = IntegerAttribute(value);
        return attr;
    }
}
