package demo

import androidx.compose.runtime.mutableStateOf
import model.BaseFormModel
import java.util.*

class UserDefinedModel() : BaseFormModel(){

    init {
        setCurrentLanguageForAll(Locale.GERMAN)
        setTitle("Demo Title")
    }

    //String
    val stringValue = createStringAttribute("Ich bin Text")
        .setLabelForLanguage(Locale.GERMAN,"deutscher String: ")
        .setLabelForLanguage(Locale.ENGLISH,"english String: ")
        .setRequired(true)
        .setReadOnly(false)
        .setMinLength(2)
        .setMaxLength(20)

    //Numbers
    val intValue1    = createIntegerAttribute(10)
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
        .setRequired(false)
        .setReadOnly(false)
        .setLowerBound(10)
        .setUpperBound(20)
        .setStepSize(1)

    val shortValue = createShortAttribute(9)
        .setLabelForLanguage(Locale.GERMAN,"deutscher Short: ")
        .setLabelForLanguage(Locale.ENGLISH,"english Short: ")
        .setRequired(true)
        .setReadOnly(false)
        .setLowerBound(0)
        .setUpperBound(100)
        .setStepSize(2)

    val longValue = createLongAttribute(9)
        .setLabelForLanguage(Locale.GERMAN,"deutscher Long: ")
        .setLabelForLanguage(Locale.ENGLISH,"english Long: ")
        .setRequired(true)
        .setReadOnly(false)
        .setLowerBound(0)
        .setUpperBound(100)
        .setStepSize(2)

    val floatValue = createFloatAttribute(9.5f)
        .setLabelForLanguage(Locale.GERMAN,"deutscher Float: ")
        .setLabelForLanguage(Locale.ENGLISH,"english Float: ")
        .setRequired(true)
        .setReadOnly(false)
        .setLowerBound(0f)
        .setUpperBound(100f)
        .setStepSize(3f)

    val doubleValue = createDoubleAttribute(8.4)
        .setLabelForLanguage(Locale.GERMAN,"deutscher Double: ")
        .setLabelForLanguage(Locale.ENGLISH,"english Double: ")
        .setRequired(true)
        .setReadOnly(false)
        .setLowerBound(4.9)
        .setUpperBound(20.5)
        .setStepSize(0.4)


    val dateValue   = mutableStateOf("01/05/2020")
    val booleanValue = mutableStateOf(true)
    val radioButtonValue = mutableStateOf(true)

    val dropDownItems  = listOf("1","2","3","4")
    val dropDownOpen    = mutableStateOf(false)
    val dropDownSelIndex = mutableStateOf(0)

}