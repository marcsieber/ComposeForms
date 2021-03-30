import androidx.compose.runtime.mutableStateOf
import java.util.*

class AppModel(){

    val stringValue = mutableStateOf("Ich bin Text")
    val longValue   = mutableStateOf("5")

    val intValue    = IntegerAttribute(11)
        //.setLabel("Int: ")
        .setLabelForLanguage(Locale.GERMAN,"deutscher Int: ")
        .setLabelForLanguage(Locale.ENGLISH,"english Int: ")
        .setCurrentLanguage(Locale.GERMAN)
        .setRequired(true)
        .setReadOnly(false)
        .setLowerBound(10)
        .setUpperBound(20)
        .setStepSize(2)


    val doubleValue = mutableStateOf("6.0")
    val floatValue  = mutableStateOf("7.9")
    val dateValue   = mutableStateOf("01/05/2020")
    val booleanValue = mutableStateOf(true)
    val radioButtonValue = mutableStateOf(true)

    val dropDownItems  = listOf("1","2","3","4")
    val dropDownOpen    = mutableStateOf(false)
    val dropDownSelIndex = mutableStateOf(0)

}