package demo

import model.util.ILabel


enum class Labels(val english: String, val deutsch: String): ILabel {
    intLabel1("Good Morning", "Guten Tag"),
    intLabel2("eng", "deutsch"),
    stringLabel1("String end", "String de");

    // Needed to get Languages
    companion object {
        fun getLanguages(): List<String> {
            return (values()[0] as ILabel).getLanguagesDynamic()
        }
    }
}



