import androidx.compose.runtime.mutableStateOf
import java.util.*

class AppModel(){

    var stringValue = mutableStateOf("Ich bin Text")
    var longValue   = mutableStateOf("5")

    var intValue    = IntegerAttribute(11)
        //.setLabel("Int: ")
        .setLabelForLanguage(Locale.GERMAN,"deutscher Int: ")
        .setLabelForLanguage(Locale.ENGLISH,"english Int: ")
        .setCurrentLanguage(Locale.GERMAN)
        .setRequired(true)
        .setReadOnly(false)
        .setLowerBound(10)
        .setUpperBound(20)
        .setStepSize(2)
        .addValueAsTextListener { s -> println(s) }


    var doubleValue = mutableStateOf("6.0")
    var floatValue  = mutableStateOf("7.9")
    var dateValue   = mutableStateOf("01/05/2020")
    var booleanValue = mutableStateOf(true)
    var radioButtonValue = mutableStateOf(true)

    var dropDownItems  = listOf("1","2","3","4")
    var dropDownOpen    = mutableStateOf(false)
    var dropDownSelIndex = mutableStateOf(0)

}