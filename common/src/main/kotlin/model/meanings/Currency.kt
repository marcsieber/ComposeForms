package model.meanings

import java.util.Currency

class Currency<T>(var currency : Currency) : SemanticMeaning<T> where T : Number, T : Comparable<T> {

    override fun addMeaning(valAsText: String): String {
        return currency.symbol
    }

}