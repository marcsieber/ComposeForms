package demo

import androidx.compose.runtime.mutableStateOf
import model.BaseFormModel
import model.FormModel
import model.util.attribute.*
import java.util.*

class UserDefinedModel() : BaseFormModel(){

    init {
        setCurrentLanguageForAll(Locale.GERMAN)
        setTitle("Demo Title")
    }

    //String
    val stringValue = StringAttribute(
        model = this,
        value = "Ich bin Text",
        required = true,
        readOnly = false,
        minLength = 2,
        maxLength = 20,
        label = "deutscher String",
    )

    //Numbers
    val intValue1    = IntegerAttribute(
        model = this,
        value = 10,
        label = "deutscher Int:",
        required = true,
        readOnly = false,
        lowerBound = 10,
        upperBound = 20,
        stepSize = 2
    )

    val intValue2    = IntegerAttribute(model = this,
        value = 15,
        required = false,
        readOnly = false,
        lowerBound = 10,
        upperBound = 20,
        stepSize = 1,
        label = "deutscher int 2"
    )

    val shortValue = ShortAttribute(model = this,
        value = 9,
        label = "deutscher Short",
        required = true,
        readOnly = false,
        lowerBound = 0,
        upperBound = 100,
        stepSize = 2
        )

    val longValue = LongAttribute(model = this,
        value = 9,
        label = "deutscher Long",
        required = true,
        readOnly = false,
        lowerBound = 0,
        upperBound = 100,
        stepSize = 2
        )

    val floatValue = FloatAttribute(
        model = this,
        value = 9.5f,
        label = "deutscher float",
        required = true,
        readOnly = false,
        lowerBound = 0f,
        upperBound = 100f,
        stepSize = 3f,
        onlyStepValuesAreValid = true
        )

    val doubleValue = DoubleAttribute(
        model = this,
        value = 8.4,
        label = "deutscher double",
        required = true,
        readOnly = false,
        lowerBound = 4.9,
        upperBound = 20.5,
        stepSize = 0.45
    )

    val dateValue   = mutableStateOf("01/05/2020")
    val booleanValue = mutableStateOf(true)
    val radioButtonValue = mutableStateOf(true)

    val dropDownItems  = listOf("1","2","3","4")
    val dropDownOpen    = mutableStateOf(false)
    val dropDownSelIndex = mutableStateOf(0)

}