package model.meanings

interface SemanticMeaning<T> {

    /**
     * Returns the meaning for a String.
     * @param valAsText: String on which the meaning is added
     * @param String: returns the meaning accordingly to the valAsText
     */
    fun addMeaning(valAsText : String) : String
}