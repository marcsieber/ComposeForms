package demo.personForm

import model.util.ILabel

enum class Labels(val deutsch : String, val english : String) : ILabel{
    FIRSTNAME("Vorname", "First name"),
    LASTNAME("Nachname", "Last name"),
    GENDER("Geschlecht", "Gender"),
    AGE("Alter", "Age"),
    OCCUPATION("Beruf", "Occupation"),
    TAXNUMBER("Steuer-Nummer", "Tax Number");

    // Needed to get Languages
    companion object {
        fun getLanguages(): List<String> {
            return (values()[0] as ILabel).getLanguagesDynamic()
        }
    }
}