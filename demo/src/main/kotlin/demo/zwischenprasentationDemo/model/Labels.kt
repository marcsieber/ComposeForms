package demo.zwischenprasentationDemo.model

import model.util.ILabel

enum class Labels() :  ILabel{

    ;

    companion object {
        fun getLanguages(): List<String> {
            return values()[0].getLanguagesDynamic()
        }
    }
}