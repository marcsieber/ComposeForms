package demo.zwischenprasentation.model

import model.util.ILabel

enum class Labels(val deutsch: String, val english : String, val francais: String) :  ILabel{

    FIRSTNAME("Vorname", "Firstname", "Prénom"),
    SURNAME("Nachname", "Surname", "Nom de famille"),
    GENDER("Geschlecht", "Gender", "Sexe"),
    AGE("Alter", "Age", "Âge"),
    JOB("Beruf", "Job", "Profession");

    companion object {
        fun getLanguages(): List<String> {
            return values()[0].getLanguagesDynamic()
        }
    }
}