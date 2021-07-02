package demo.playGroundForm

import androidx.compose.runtime.mutableStateOf
import model.BaseModel
import convertibles.CustomConvertible
import convertibles.ReplacementPair
import model.meanings.Currency
import model.meanings.CustomMeaning
import model.meanings.Percentage
import model.util.presentationElements.Group
import model.util.attribute.*
import model.util.presentationElements.Field
import model.util.presentationElements.FieldSize
import model.validators.semanticValidators.*
import java.time.LocalTime

class UserDefinedModel : BaseModel(Labels.stringLabel, smartphoneOption = true){

    init {
        setTitle("Demo Title")
    }



    val strValidator = StringValidator(5, 10)
    val customValidator = CustomValidator<String>({value -> value!!.length in 3..5}, validationMessage = "Message")


    val s = StringAttribute(
        model = this,
        value = "",
        label = Labels.stringLabel,
        validators = listOf(RegexValidator<String>("^\\w+\\W\\w+\$", validationMessage = "Muss genau zwei Wörter sein")),
        meaning = CustomMeaning("m")
    )

    val d1 = DoubleAttribute(
        model = this,
        value = 0.0,
        label = Labels.convertOnUnfocussing,
        required = true,
        convertibles = listOf(
            CustomConvertible(listOf(
                ReplacementPair("eins", "1"),
                ReplacementPair("zwei", "2")
            )),
            CustomConvertible(listOf(
                ReplacementPair("(\\d*)(,)(\\d*)", "$1.$3")
            ), convertUserView = true)
        ),
        meaning = CustomMeaning("g")
    )

    val d2 = DoubleAttribute(
        model = this,
        value = 0.0,
        label = Labels.convertImmediately,
        convertibles = listOf(
            CustomConvertible(listOf(
                ReplacementPair("eins", "1"),
                ReplacementPair("zwei", "2")
            ), false, true),
            CustomConvertible(listOf(
                ReplacementPair("(\\d*)(,)(\\d*)", "$1.$3")
            ), convertUserView = true, true)
        ),
        meaning = Currency(java.util.Currency.getInstance("EUR"))
    )

    val d3 = DoubleAttribute(
        model = this,
        value = 0.0,
        label = Labels.doNotConvert,
        convertibles = listOf(
            CustomConvertible(listOf(
                ReplacementPair("eins", "1"),
                ReplacementPair("zwei", "2")
            ), false),
            CustomConvertible(listOf(
                ReplacementPair("(\\d*)(,)(\\d*)", "$1.$3")
            ), convertUserView = false)
        ),
        meaning = Percentage()
    )


    val time = StringAttribute(
        model = this,
        label = Labels.timeLabel,
        convertibles = listOf(CustomConvertible(listOf(
            ReplacementPair("now", LocalTime.now().toString())
            ), convertUserView = true)
        )
    )

    val intAttr = IntegerAttribute(this, Labels.intLabel, 12)

    //String
    val strVal = StringValidator(5)
    val stringValue = StringAttribute(
        model = this,
        value = "Ich bin Text",
        readOnly = false,
        required = true,
        validators = listOf(strVal),
        label = Labels.stringLabel,
    )

    val string: StringAttribute<Labels> = StringAttribute(
        model = this,
        value = "1234",
        validators = listOf(StringValidator(minLength = 3, maxLength = 5)),
        label = Labels.stringLabel,
        required = true,
        onChangeListeners = listOf(
            intAttr addOnChangeListener { a, v -> a.setRequired(v ?: 0 >= 123) },
            intAttr addOnChangeListener { a, v -> a.setReadOnly(v ?: 0 == 1) },
        )
    )


    //Numbers
    val intValue1    = IntegerAttribute(
        model = this,
        label = Labels.intLabel,
        readOnly = false,
        validators = listOf(NumberValidator(0, 10, 2, 4, true, "LowerBound ist bei 0, upperBound bei 10, es sind nur 2er-Schritte zugelassen")),
        meaning = Currency(java.util.Currency.getInstance("EUR"))
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
    )

    val doubleValue = DoubleAttribute(
        model = this,
        value = 7.7,
        label = Labels.doubleLabel,
        required = true,
        readOnly = false,
        validators = listOf(FloatingPointValidator(2, "Nur 2 Nachkommastellen")),
    )

    val list = setOf("Hallo", "Louisa", "Steve", "Eintrag", "Eintrag564", "Hi", "Mann", "Frau")
    val selectionValue = SelectionAttribute(
        model = this,
        value = setOf(),
        possibleSelections = list,
        label = Labels.selectionLabel,
        validators = listOf(SelectionValidator(0,2))
    )

    init{
        setCurrentLanguageForAll("english")
    }

    val dateValue   = mutableStateOf("01/05/2020")
    val booleanValue = mutableStateOf(true)
    val radioButtonValue = mutableStateOf(true)

    //Selection
    val dropDownItems  = setOf<String>("1","2","3","4")
    val dropDownOpen    = mutableStateOf(false)
    val dropDownSelIndex = mutableStateOf(0)

    val group0 = Group(this, "Stranger Things",
        Field(stringValue),
        Field(string),
        Field(intAttr)
    )


    val group1 = Group(this, "Group-Name",
        Field(s, FieldSize.SMALL),
        Field(d1, FieldSize.SMALL),
        Field(d2),
        Field(selectionValue))

    val group2 = Group(this, "Group-Name 2",
        Field(intValue1),
        Field(intValue2),
        Field(longValue),
        Field(shortValue))

    val group = Group(this,"")
}