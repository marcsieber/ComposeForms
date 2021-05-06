package demo

import androidx.compose.runtime.mutableStateOf
import model.BaseFormModel
import model.util.attribute.*
import model.validators.StringValidator
import kotlin.concurrent.thread

class UserDefinedModel() : BaseFormModel(){

    init {
        setTitle("Demo Title")
    }

    val strValidator = StringValidator(5, 10, onChange = { validateAll() })


    val s = StringAttribute(
        model = this,
        value = "Test-Label",
        label = Labels.intLabel1
    )

    val d = DoubleAttribute(
        model = this,
        value = 0.0,
        onlyStepValuesAreValid = true,
        stepSize = 0.2,
        label = Labels.intLabel2
    )

    //String
    val stringValue = StringAttribute(
        model = this,
        value = "Ich bin Text",
        required = true,
        readOnly = false,
        validators = listOf(strValidator),
        label = Labels.stringLabel1,
    )

    //Numbers
    val intValue1    = IntegerAttribute(
        model = this,
        value = 10,
        label = Labels.intLabel2,
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
        label = Labels.intLabel2
    )

    val shortValue = ShortAttribute(model = this,
        value = 9,
        label = Labels.intLabel2,
        required = true,
        readOnly = false,
        lowerBound = 0,
        upperBound = 100,
        stepSize = 2
        )

    val longValue = LongAttribute(model = this,
        value = 9,
        label = Labels.intLabel2,
        required = true,
        readOnly = false,
        lowerBound = 0,
        upperBound = 100,
        stepSize = 2
        )

    val floatValue = FloatAttribute(
        model = this,
        value = 9.5f,
        label = Labels.intLabel2,
        required = true,
        readOnly = false,
        lowerBound = 0f,
        upperBound = 100f,
        stepSize = 3f,
        onlyStepValuesAreValid = true,
        onChangeListeners = listOf({
            if(it == 12.5f){
                println("NO CHANGE OF LABEL POSSIBLE") //TODO what do you want to test?
            }},
            {
                longValue.setReadOnly(it == 12.5f)
            })
        )

    val doubleValue = DoubleAttribute(
        model = this,
        value = 8.4,
        label = Labels.intLabel2,
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
        label = Labels.intLabel2,
        minNumberOfSelections = 0,
        maxNumberOfSelections = 2
    )

    init{
        setCurrentLanguageForAll("English")
//        println("ILabel languages" + ILabel.getLanguages())

        println("All languages:")
//        Labels.getLanguages()
        Labels.getLanguages().forEach{ println(it)}
        println("-------")
    }

    val dateValue   = mutableStateOf("01/05/2020")
    val booleanValue = mutableStateOf(true)
    val radioButtonValue = mutableStateOf(true)

    //Selection
    val dropDownItems  = setOf<String>("1","2","3","4")
    val dropDownOpen    = mutableStateOf(false)
    val dropDownSelIndex = mutableStateOf(0)


    init{
        thread {
            Thread.sleep(3000)
//            this.labels = label2
            this.setCurrentLanguageForAll("Deutsch")
            println("test")
        }
    }


}