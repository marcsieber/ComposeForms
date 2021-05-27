package demo.userTesting

import model.util.ILabel

enum class UserTestingLabels(val german : String, val english : String) : ILabel {

    NAME("Vorname", "Suranme"),
    NACHNAME("Nachname", "Name"),
    BERUF("Beruf", "Profsssion"),
    ID("Id", "Id"),
    STEURNUMMER("Stuernummer", "Tax number"),
    ALTER("Alter", "Age"),
    GESCHLECHT("Geschlecht", "Gender"),
    GESCHLECHT_MANN("Mann", "Gender"),
    GESCHLECHT_FRAU("Frau", "Gender"),
}