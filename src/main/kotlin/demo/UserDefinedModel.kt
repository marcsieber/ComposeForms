package demo

import androidx.compose.runtime.mutableStateOf
import model.BaseFormModel
import model.util.attribute.*
import model.validators.semanticValidators.*
import kotlin.concurrent.thread

class UserDefinedModel() : BaseFormModel(){

    init {
        setTitle("Demo Title")
    }

    val strValidator = StringValidator(5, 10)
    val customValidator = CustomValidator<String>({value -> value!!.length in 3..5}, validationMessage = "Message")


    val s = StringAttribute(
        model = this,
        value = "Test-Label",
        label = Labels.stringLabel
    )

    val d = DoubleAttribute(
        model = this,
        value = 0.0,
//        onlyStepValuesAreValid = true,
//        stepSize = 0.2,
        label = Labels.doubleLabel
    )

    //String
    val strVal = StringValidator(5)
    val stringValue = StringAttribute(
        model = this,
        value = "Ich bin Text",
        readOnly = false,
        required = true,
        validators = listOf(strVal),
        label = Labels.stringLabel,
        onChangeListeners = listOf {
            if (it.equals("Ich")) {
                customValidator.overrideCustomValidator(validationMessage = "Neue Message")
            }
        }
    )

    //Numbers
    val intValue1    = IntegerAttribute(
        model = this,
        value = 10,
        label = Labels.intLabel,
        readOnly = false,
        validators = listOf(NumberValidator(0, 10, 2, 4, true, "LowerBound ist bei 0, upperBound bei 10, es sind nur 2er-Schritte zugelassen")),
        onChangeListeners = listOf{
            if(it == 4){
                strVal.overrideStringValidator(8)
            }

        }
    )

    val intValue2    = IntegerAttribute(model = this,
        value = 15,
        required = false,
        readOnly = true,
        label = Labels.intLabel
    )

    val shortValue = ShortAttribute(model = this,
        value = 9,
        label = Labels.shortLabel,
        readOnly = false,
        validators = listOf(NumberValidator(0,100,2, 9))
        )

    val longValue = LongAttribute(model = this,
        value = 9,
        label = Labels.longLabel,
        readOnly = false,
        validators = listOf(NumberValidator(0, 100, 2))
        )

    val floatValue = FloatAttribute(
        model = this,
        value = 9.5f,
        label = Labels.floatLabel,
        required = true,
        readOnly = false,
        validators = listOf(NumberValidator(0f, 100f, 3f, 9.5f, true)) ,
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
        value = 7.7,
        label = Labels.doubleLabel,
        required = true,
        readOnly = false,
        validators = listOf(FloatingPointValidator(2, "Nur 2 Nachkommastellen")),
        onChangeListeners = listOf {
            if (it == 8.85) {
                selectionValue.addANewPossibleSelection("Neues Element")
            }
        }
    )

    val list = setOf("Hallo", "Louisa", "Steve", "Eintrag")
    val selectionValue = SelectionAttribute(
        model = this,
        value = setOf(),
        possibleSelections = list,
        label = Labels.selectionLabel,
        validators = listOf(SelectionValidator(0,2))
    )

    init{
        setCurrentLanguageForAll("English")
        println("All languages:")
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
            strValidator.overrideStringValidator(15,20,"Length must be between 15 and 20 characters")
            println("test")
        }
    }

}