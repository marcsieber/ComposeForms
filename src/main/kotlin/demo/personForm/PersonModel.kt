package demo.personForm

import model.BaseFormModel
import model.util.attribute.IntegerAttribute
import model.util.attribute.LongAttribute
import model.util.attribute.SelectionAttribute
import model.util.attribute.StringAttribute
import model.validators.semanticValidators.NumberValidator
import model.validators.semanticValidators.StringValidator

class PersonModel() : BaseFormModel() {


    override fun getPossibleLanguages(): List<String> {
        return Labels.getLanguages()
    }

    val firstName   = StringAttribute(this, label = Labels.FIRSTNAME, validators = listOf(StringValidator(minLength = 3)))
    val lastName    = StringAttribute(this, label = Labels.LASTNAME)
    val occupation  = StringAttribute(this, label = Labels.OCCUPATION, onChangeListeners = listOf{taxNumber.setRequired(it != null)})
    val taxNumber   = IntegerAttribute(this, label = Labels.TAXNUMBER)
    val age         = LongAttribute(this, label = Labels.AGE, validators = listOf(NumberValidator(lowerBound = 0, upperBound = 130)))
    val gender      = SelectionAttribute(this, label = Labels.GENDER, possibleSelections = if (isCurrentLanguageForAll("deutsch")) setOf<String>("Frau", "Mann") else setOf<String>("Female", "Male"))


    init {
        setCurrentLanguageForAll("deutsch")
        setTitle(if (isCurrentLanguageForAll("deutsch")) "Klienten" else "Clients")
    }
}