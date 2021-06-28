package demo.personForm

import model.BaseModel
import model.util.attribute.*
import model.convertibles.CustomConvertible
import model.convertibles.ReplacementPair
import model.meanings.CustomMeaning
import model.util.presentationElements.Field
import model.util.presentationElements.FieldSize
import model.util.presentationElements.Group
import model.validators.semanticValidators.*

class PersonModel : BaseModel(true) {

    override fun getPossibleLanguages(): List<String> {
        return PersonLabels.getLanguages()
    }

    init {
        setTitle(if (isCurrentLanguageForAll("deutsch")) "Klienten" else "Clients")
    }

    val id          = IntegerAttribute(model = this, label = PersonLabels.ID,
        value = 1,
        readOnly = true)

    val firstName   = StringAttribute(model = this, label = PersonLabels.FIRSTNAME,
        required = true,
        validators = listOf(StringValidator(minLength = 5, maxLength = 10)))

    val lastName    = StringAttribute(model = this, label = PersonLabels.LASTNAME,
        required = true)

    val age         = LongAttribute(this, PersonLabels.AGE,
        validators = listOf(NumberValidator(lowerBound = 0, upperBound = 130)))

    val gender      = SelectionAttribute(this, PersonLabels.GENDER,
        possibleSelections = setOf<String>("Man", "Woman", "Other"))

    val size        = DoubleAttribute(this, PersonLabels.SIZE,
        meaning = CustomMeaning("m"),
        validators = listOf(FloatingPointValidator(2, "Too many decimal places."), NumberValidator(lowerBound = 0.0, upperBound = 3.0)),
        convertibles = listOf(CustomConvertible(replaceRegex = listOf(ReplacementPair("(\\d*)(,)(\\d*)", "$1.$3")), convertUserView = false, convertImmediately = true)))

    val occupation  = StringAttribute(this, PersonLabels.OCCUPATION)

    val taxNumber   = IntegerAttribute(this, PersonLabels.TAXNUMBER,
        onChangeListeners = listOf(
            occupation addOnChangeListener {taxN, occ -> taxN.setRequired(occ != null)}
        ))

    val postCode    = IntegerAttribute(this, PersonLabels.POSTCODE,
        validators = listOf(RegexValidator(regexPattern = "*{3,5}", rightTrackRegexPattern = "*{0,5}", validationMessage = "The input must be 3 - 5 characters long")))

    val place       = StringAttribute(this, PersonLabels.PLACE)
    val street      = StringAttribute(this, PersonLabels.STREET)
    val houseNumber = ShortAttribute(this, PersonLabels.HOUSENUMBER)


    val group1 = Group(model = this, title = "Personal Information",
        Field(id, FieldSize.SMALL),
        Field(firstName, FieldSize.SMALL),
        Field(lastName),
        Field(age, FieldSize.SMALL),
        Field(size, FieldSize.SMALL),
        Field(gender, FieldSize.NORMAL),
        Field(occupation),
        Field(taxNumber)
    )

    val group2 = Group(model = this, title = "Adress",
        Field(postCode),
        Field(place),
        Field(street),
        Field(houseNumber)
    )

    init{
        this.setCurrentLanguageForAll("english")
    }
}