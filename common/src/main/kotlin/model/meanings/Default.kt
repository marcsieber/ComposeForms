package model.meanings

class Default<Any> : SemanticMeaning<Any> {
    override fun addMeaning(valAsText: String): String {
        return ""
    }
}