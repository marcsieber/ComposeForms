/*
 *
 *   ========================LICENSE_START=================================
 *   Compose Forms
 *   %%
 *   Copyright (C) 2021 FHNW Technik
 *   %%
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *   =========================LICENSE_END==================================
 *
 */

package demo.personForm

import model.BaseModel
import model.util.attribute.*
import convertibles.*
import model.meanings.CustomMeaning
import model.util.presentationElements.Field
import model.util.presentationElements.FieldSize
import model.util.presentationElements.Group
import model.validators.semanticValidators.*

/**
 * @author Louisa Reinger
 * @author Steve Vogel
 */
class PersonModel : BaseModel(smartphoneOption = true, iLabel = PersonLabels.SIZE) {


    init {
        setTitle("Clients")
    }

    val id          = IntegerAttribute(model = this, label = PersonLabels.ID,
        value = 1,
        readOnly = true)

    val firstName   = StringAttribute(model = this, label = PersonLabels.FIRSTNAME,
        required = true,
        validators = listOf(StringValidator(minLength = 3, maxLength = 10)))

    val lastName    = StringAttribute(model = this, label = PersonLabels.LASTNAME,
        required = true)

    val age         = LongAttribute(this, PersonLabels.AGE,
        validators = listOf(NumberValidator(lowerBound = 0, upperBound = 130)))

    val gender      = SelectionAttribute(this, PersonLabels.GENDER,
        possibleSelections = setOf("Man", "Woman", "Other"),
        validators = listOf(
            SelectionValidator(0,1,"Only 1 selection possible.")
        ))

    val size        = DoubleAttribute(this, PersonLabels.SIZE,
        decimalPlaces = 2,
        meaning = CustomMeaning("m"),
        validators = listOf(
            FloatingPointValidator(2, "Too many decimal places."),
            NumberValidator(lowerBound = 0.0, upperBound = 3.0)
        ),
        convertibles = listOf(CustomConvertible(
            replaceRegex = listOf(ReplacementPair("(\\d*)(,)(\\d*)", "$1.$3")),
            convertUserView = false,
            convertImmediately = true
        )))

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