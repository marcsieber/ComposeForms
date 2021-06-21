package model.meanings

class CustomMeaning<T>(val customText: String) : SemanticMeaning<T> {

    override fun addMeaning(valAsText: String): String {
        return customText
    }

}