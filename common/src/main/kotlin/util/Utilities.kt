package util

class Utilities<T> {

    /**
     *
     */
    fun toDataType(value : String, typeT : T) : T{
        return when (typeT){
            is Int -> value.toInt() as T
            is Short -> value.toShort() as T
            is Long -> value.toLong() as T
            is Double -> value.toDouble() as T
            is Float -> value.toFloat() as T
            is Set<*> -> stringToSetConverter(value) as T
            else -> value as T
        }
    }

    /**
     * This method converts a String into a Set<String>.
     * If this is not possible, a Numberformatexception is thrown
     * @param valAsText : String
     * @return set : Set<String>
     * @throws NumberFormatException
     */
    fun stringToSetConverter(valAsText : String) : Set<String>{
        var set : Set<String>
        if(valAsText.equals("[]")){
            set = emptySet()
        }else{
            set = valAsText.substring(1,valAsText.length-1).split(", ").toSet() //convert string to set
        }
        return set
    }

    /**
     * get min value of T as T
     */
    fun getMinValueOfT(typeT : T): T{
        return when (typeT) {
            is Int -> Int.MIN_VALUE as T
            is Short -> Short.MIN_VALUE as T
            is Long -> Long.MIN_VALUE as T
            is Double -> -Double.MAX_VALUE as T
            is Float -> -Float.MAX_VALUE as T
            else -> 0 as T
        }
    }

    /**
     * Get max value of T as T
     */
    fun getMaxValueOfT(typeT: T): T{
        return when (typeT) {
            is Int -> Int.MAX_VALUE as T
            is Short -> Short.MAX_VALUE as T
            is Long -> Long.MAX_VALUE as T
            is Double -> Double.MAX_VALUE as T
            is Float -> Float.MAX_VALUE as T
            else -> 0 as T
        }
    }
}