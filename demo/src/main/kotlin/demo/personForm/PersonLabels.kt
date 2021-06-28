package demo.personForm

import model.util.ILabel

enum class PersonLabels(val deutsch : String, val english : String) : ILabel{
    //Personal Information
    ID("ID", "ID"),
    FIRSTNAME("Vorname", "First name"),
    LASTNAME("Nachname", "Last name"),
    GENDER("Geschlecht", "Gender"),
    AGE("Alter", "Age"),
    SIZE("Grösse", "Size"),
    OCCUPATION("Beruf", "Occupation"),
    TAXNUMBER("Steuer-Nummer", "Tax Number"),

    //Adress
    POSTCODE("Postleitzahl", "Postcode"),
    PLACE("Ort", "Town/City"),
    STREET("Strasse", "Street"),
    HOUSENUMBER("Hausnummer","House Number");


    // Needed to get Languages
    companion object {
        fun getLanguages(): List<String> {
            return (values()[0] as ILabel).getLanguagesDynamic()
        }
    }
}