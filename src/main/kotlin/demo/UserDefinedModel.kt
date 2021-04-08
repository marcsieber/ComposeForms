package demo

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import model.BaseFormModel
import model.util.attribute.IntegerAttribute
import java.util.*

class UserDefinedModel() : BaseFormModel(){

    val stringValue = createStringAttribute("Ich bin Text")
        .setLabelForLanguage(Locale.GERMAN,"deutscher String: ")
        .setLabelForLanguage(Locale.ENGLISH,"english String: ")
        .setRequired(true)
        .setReadOnly(false)
        .setMinLength(2)
        .setMaxLength(10)

    val longValue   = mutableStateOf("5")

    val intValue1    = createIntegerAttribute(11)
        //.setLabel("Int: ")
        .setLabelForLanguage(Locale.GERMAN,"deutscher Int: ")
        .setLabelForLanguage(Locale.ENGLISH,"english Int: ")
//        .setCurrentLanguage(Locale.GERMAN)  //TODO: make setCurrentLanguage protected
        .setRequired(true)
        .setReadOnly(false)
        .setLowerBound(10)
        .setUpperBound(20)
        .setStepSize(2)

    val intValue2    = createIntegerAttribute(15)
        .setLabelForLanguage(Locale.GERMAN,"deutscher Int: ")
        .setLabelForLanguage(Locale.ENGLISH,"english Int: ")
        .setRequired(true)
        .setReadOnly(false)
        .setLowerBound(10)
        .setUpperBound(20)
        .setStepSize(2)

    init {
        setCurrentLanguageForAll(Locale.GERMAN)
    }

    val doubleValue = mutableStateOf("6.0")
    val floatValue  = mutableStateOf("7.9")
    val dateValue   = mutableStateOf("01/05/2020")
    val booleanValue = mutableStateOf(true)
    val radioButtonValue = mutableStateOf(true)

    val dropDownItems  = listOf("1","2","3","4")
    val dropDownOpen    = mutableStateOf(false)
    val dropDownSelIndex = mutableStateOf(0)

}