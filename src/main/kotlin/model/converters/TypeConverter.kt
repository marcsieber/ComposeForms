package model.converters

import model.util.Utilities

class TypeConverter<T>() {

    fun convertStringToType(string : String, typeT: T ) : T {
        return Utilities<T>().toDataType(string, typeT)
    }

}