package model.util

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
            is Set<*> -> convertStringToSet(value) as T
            else -> value as T
        }
    }

    /**
     * This method converts a String into a MutableSet<String>.
     * If this is not possible,  a Numberformatexception is thrown
     * @param newVal : String
     * @return newVal : Double
     * @throws NumberFormatException
     */
    private fun convertStringToSet(newVal: String) : MutableSet<String>{
        val set = newVal.substring(1,newVal.length-1).split(", ")
        return set.toMutableSet()
    }


    /**
     * get min value of T as T
     */
    fun getMinValueOfT(typeT : T): T{
        return when (typeT) {
            is Int -> Int.MIN_VALUE as T
            is Short -> Short.MIN_VALUE as T
            is Long -> Long.MIN_VALUE as T
            is Double -> Double.MIN_VALUE as T
            is Float -> Float.MIN_VALUE as T
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