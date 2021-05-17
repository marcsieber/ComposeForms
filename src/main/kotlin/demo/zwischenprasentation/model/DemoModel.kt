package demo.zwischenprasentation.model

import model.BaseFormModel
import model.util.attribute.IntegerAttribute
import model.util.attribute.SelectionAttribute
import model.util.attribute.StringAttribute
import model.validators.semanticValidators.SelectionValidator

class DemoModel() : BaseFormModel() {

    init{
        setTitle("Demo App")
    }

    val firstNameAttr = StringAttribute(value = "", model = this, label = Labels.FIRSTNAME)
    val surNameAttr = StringAttribute(value = "", model = this, label = Labels.SURNAME)

    val genderAttr = SelectionAttribute(
        value = emptySet(),
        model = this,
        label = Labels.GENDER,
        possibleSelections = setOf("Mann", "Frau"),
        validators = listOf(SelectionValidator(minNumberOfSelections = 1, maxNumberOfSelections = 1)))


    val ageAttribute = IntegerAttribute(model = this, label = Labels.AGE, onChangeListeners = listOf{ jobAttribute.setRequired(it != null && it >= 18)})

    val jobAttribute = StringAttribute(model = this, label = Labels.JOB)

    override fun getPossibleLanguages(): List<String> {
        return Labels.getLanguages()
    }
}