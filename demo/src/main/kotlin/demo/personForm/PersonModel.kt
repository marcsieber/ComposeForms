package demo.personForm

import model.BaseFormModel
import model.util.attribute.*
import model.convertables.CustomConvertable
import model.convertables.ReplacementPair
import model.util.attribute.*
import model.validators.semanticValidators.NumberValidator
import model.validators.semanticValidators.StringValidator
import java.time.LocalTime

class PersonModel() : BaseFormModel() {


    override fun getPossibleLanguages(): List<String> {
        return Labels.getLanguages()
    }

    val id          = IntegerAttribute(this, 1, label = Labels.ID, readOnly = true)
    val firstName   = StringAttribute(this, label = Labels.FIRSTNAME, validators = listOf(StringValidator(minLength = 5, maxLength = 10)))
    val lastName    = StringAttribute(this, label = Labels.LASTNAME)
    val occupation  = StringAttribute(this, label = Labels.OCCUPATION, onChangeListeners = listOf{taxNumber.setRequired(it != null)})
    val taxNumber   = IntegerAttribute(this, label = Labels.TAXNUMBER)
    val age         = LongAttribute(this, label = Labels.AGE, validators = listOf(NumberValidator(lowerBound = 0, upperBound = 130)))
    val gender      = SelectionAttribute(this, label = Labels.GENDER, possibleSelections = setOf<String>("Frau", "Mann"))

    val d1 = DoubleAttribute(
        model = this,
        value = 0.0,
        label = demo.playGroundForm.Labels.convertOnUnfocussing,
        convertables = listOf(
            CustomConvertable(listOf(
                ReplacementPair("eins", "1"),
                ReplacementPair("zwei", "2")
            ), convertUserView = true),
            CustomConvertable(listOf(
                ReplacementPair("(\\d*)(,)(\\d*)", "$1.$3")
            ), convertUserView = true)
        )
    )

    val d2 = DoubleAttribute(
        model = this,
        value = 0.0,
        label = demo.playGroundForm.Labels.convertImmediately,
        convertables = listOf(
            CustomConvertable(listOf(
                ReplacementPair("eins", "1"),
                ReplacementPair("zwei", "2")
            ), true, true),
            CustomConvertable(listOf(
                ReplacementPair("(\\d*)(,)(\\d*)", "$1.$3")
            ), convertUserView = true, true)
        )
    )

    val d3 = DoubleAttribute(
        model = this,
        value = 0.0,
        label = demo.playGroundForm.Labels.doNotConvert,
        convertables = listOf(
            CustomConvertable(listOf(
                ReplacementPair("eins", "1"),
                ReplacementPair("zwei", "2")
            ), false),
            CustomConvertable(listOf(
                ReplacementPair("(\\d*)(,)(\\d*)", "$1.$3")
            ), convertUserView = false)
        )
    )

    val time = StringAttribute(
        model = this,
        label = demo.playGroundForm.Labels.timeLabel,
        convertables = listOf(CustomConvertable(listOf(
            ReplacementPair("now", LocalTime.now().toString())
        ), convertUserView = true)
        )
    )



    init {
        setCurrentLanguageForAll("deutsch")
        setTitle(if (isCurrentLanguageForAll("deutsch")) "Klienten" else "Clients")
    }

    override fun attributeChanged(attr: Attribute<*, *, *>) {

    }
}