package model.meanings

class Default<T> : SemanticMeaning<T> {
    override fun addMeaning(valAsText: String): String {
        return ""
    }
}