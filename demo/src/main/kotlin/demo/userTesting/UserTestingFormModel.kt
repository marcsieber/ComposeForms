package demo.userTesting

import model.BaseFormModel
import model.util.attribute.IntegerAttribute
import model.util.attribute.SelectionAttribute
import model.util.attribute.StringAttribute
import model.validators.semanticValidators.NumberValidator
import model.validators.semanticValidators.RegexValidator
import model.validators.semanticValidators.SelectionValidator
import model.validators.semanticValidators.StringValidator

class UserTestingFormModel : BaseFormModel() {

    init {
        setCurrentLanguageForAll("english")
    }

    var id = IntegerAttribute(this,
        -1,
        UserTestingLabels.ID,
        required = false,
        readOnly = true,
    )
    var vorname = StringAttribute(this,
        "",
        UserTestingLabels.NAME,
        true,
        validators = listOf(StringValidator(3, validationMessage = "Nicht so kurzer Namen du hast!"))
    )

    var nachname = StringAttribute(this,
        "",
        UserTestingLabels.NACHNAME,
        true,
    )

    var alter = IntegerAttribute(this,
        value = 20,
        label = UserTestingLabels.ALTER,
        required = true,
        validators = listOf(NumberValidator(18))
    )

    var beruf = StringAttribute(this,
        label = UserTestingLabels.BERUF,
        onChangeListeners = listOf({
            steuernummer.setRequired(it != null)
        })
    )
    var myGeschlechter = setOf("Mann", "Frau")
    var geschlecht = SelectionAttribute(
        this,
        label = UserTestingLabels.GESCHLECHT,
        possibleSelections = setOf(
            UserTestingLabels.GESCHLECHT_MANN.getLabelInLanguage(UserTestingLabels.GESCHLECHT_MANN, getCurrentLanguage()),
            UserTestingLabels.GESCHLECHT_FRAU.getLabelInLanguage(UserTestingLabels.GESCHLECHT_FRAU, getCurrentLanguage())),
        validators = listOf(SelectionValidator(1, 1, "Hee"))
    )

    var steuernummer = StringAttribute(this,
        "",
        UserTestingLabels.STEURNUMMER,
        validators = listOf(
            RegexValidator<String>("\\d{3}\\.\\d{4}\\.\\d{4}\\.\\d{2}")
        )
    )

    override fun getPossibleLanguages(): List<String> {
        return mutableListOf("english","german")
    }



}