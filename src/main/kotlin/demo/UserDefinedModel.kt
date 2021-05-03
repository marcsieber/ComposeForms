package demo

import androidx.compose.runtime.mutableStateOf
import model.BaseFormModel
import model.FormModel
import model.util.attribute.*
import model.validators.StringValidator
import java.util.*

class UserDefinedModel() : BaseFormModel(){

    init {
        setCurrentLanguageForAll(Locale.GERMAN)
        setTitle("Demo Title")
    }

    val strValidator = StringValidator(5, 10, onChange = { validateAll() })

    //String
    val stringValue = StringAttribute(
        model = this,
        value = "Ich bin Text",
        required = true,
        readOnly = false,
        label = "deutscher String",
        validators = listOf(strValidator)
    )

    //Numbers
    val intValue1    = IntegerAttribute(
        model = this,
        value = 10,
        label = "deutscher Int:",
        required = true,
        readOnly = false,
        stepSize = 2
    )

    val intValue2    = IntegerAttribute(model = this,
        value = 15,
        required = false,
        readOnly = true,
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
        onlyStepValuesAreValid = true,
        onChangeListeners = listOf({
            if(it == 12.5f){
                shortValue.setLabel("Haha")
            }},
            {
                longValue.setReadOnly(it == 12.5f)
            })
        )

    val doubleValue = DoubleAttribute(
        model = this,
        value = 8.4,
        label = "deutscher double",
        required = true,
        readOnly = false,
        lowerBound = 4.9,
        upperBound = 20.5,
        stepSize = 0.45,
        onChangeListeners = listOf {
            if (it == 8.85) {
                selectionValue.addANewPossibleSelection("Neues Element")
            }
        }
    )

    val list = setOf("Hallo", "Louisa", "Steve")
    val selectionValue = SelectionAttribute(
        model = this,
        value = setOf(),
        possibleSelections = list,
        label = "Selection-Label",
        minNumberOfSelections = 0,
        maxNumberOfSelections = 2
    )

    val dateValue   = mutableStateOf("01/05/2020")
    val booleanValue = mutableStateOf(true)
    val radioButtonValue = mutableStateOf(true)

    //Selection
    val dropDownItems  = setOf<String>("1","2","3","4")
    val dropDownOpen    = mutableStateOf(false)
    val dropDownSelIndex = mutableStateOf(0)


    init{
        Thread {
            println("start thread")
            Thread.sleep(3000)
            println("sleep done")
            strValidator.overrideStringValidator(15,20,"Length must be between 15 and 20 characters")
        }.start()
    }
}