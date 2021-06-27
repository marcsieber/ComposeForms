package model.meanings

class Percentage<T> : SemanticMeaning<T> where T : Number, T : Comparable<T>{

    override fun addMeaning(valAsText: String): String {
        return "%"
    }
}