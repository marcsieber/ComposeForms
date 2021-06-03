package demo.personForm

import model.BaseFormModel
import model.util.attribute.*
import model.validators.semanticValidators.NumberValidator
import model.validators.semanticValidators.StringValidator

class PersonModel() : BaseFormModel() {


    override fun getPossibleLanguages(): List<String> {
        return Labels.getLanguages()
    }

    val id          = IntegerAttribute(this, label = Labels.ID, readOnly = true)
    val firstName   = StringAttribute(this, label = Labels.FIRSTNAME, validators = listOf(StringValidator(minLength = 3)))
    val lastName    = StringAttribute(this, label = Labels.LASTNAME)
    val occupation  = StringAttribute(this, label = Labels.OCCUPATION, onChangeListeners = listOf{taxNumber.setRequired(it != null)})
    val taxNumber   = IntegerAttribute(this, label = Labels.TAXNUMBER)
    val age         = LongAttribute(this, label = Labels.AGE, validators = listOf(NumberValidator(lowerBound = 0, upperBound = 130)))
    val gender      = SelectionAttribute(this, label = Labels.GENDER, possibleSelections = setOf<String>("Frau", "Mann"))



    init {
        setCurrentLanguageForAll("deutsch")
        setTitle(if (isCurrentLanguageForAll("deutsch")) "Klienten" else "Clients")
    }

    override fun attributeChanged(attr: Attribute<*, *, *>) {

    }
}