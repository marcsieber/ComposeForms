package model.util.attribute

import model.BaseModel
import model.util.Labels
import model.validators.semanticValidators.StringValidator
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

internal class StringAttributeTest: AttributeTest<String>() {

    override fun provideAttribute(model: BaseModel, value: String?): Attribute<*, Any, *> {
        return StringAttribute(model, Labels.TEST, value) as Attribute<*, Any, *>
    }

    init{
        validValue1Uneven = "Hallo"
        validValue1AsText  = "Hallo"

        validValue2        = "789"
        validValue2AsText  = "789"

        validValue3        = "AEIOU"
        validValue3AsText  = "AEIOU"

        validValue4        = "Name"
        validValue4AsText  = "Name"

        notValidValueAsText  = "A".repeat(1_000_000 + 1)  //Not used (type String is always correct)

    }

    lateinit var stringAtr : StringAttribute<Labels>

    @BeforeEach
    fun setUpStringAtr(){
        //given
        stringAtr = StringAttribute(model, Labels.TEST, validValue1Uneven)
    }

    @Test
    fun testStringValidator_MinLength() {
        //given
        val validator = StringValidator(4, validationMessage = "Der Wert muss mindestens 4 Buchstaben haben.")

        stringAtr = StringAttribute(
            value = "123",
            model = model,
            validators = listOf(validator),
            label = Labels.TEST
        )

        //then
        assertFalse(stringAtr.isValid())
        assertEquals("Der Wert muss mindestens 4 Buchstaben haben.", stringAtr.getErrorMessages()[0])

        //when
        validator.overrideStringValidator(2)

        //then
        assertTrue(stringAtr.isValid())


        //given
        stringAtr = StringAttribute(
            value = "1234",
            model = model,
            validators = listOf(StringValidator(4)),
            label = Labels.TEST
        )

        //then
        assertTrue(stringAtr.isValid())


        //then
        assertThrows(IllegalArgumentException::class.java){
            //when
            stringAtr = StringAttribute(
                value = "1234",
                model = model,
                validators = listOf(StringValidator(-1)),
                label = Labels.TEST
            )
        }

        //then
        assertThrows(IllegalArgumentException::class.java){
            //when
            validator.overrideStringValidator(5,3,"")
        }
    }

    @Test
    fun testStringValidator_MaxLength() {
        //given
        val validator = StringValidator(maxLength = 4, validationMessage = "Der Wert darf maximal 4 Buchstaben haben.")

        val attr = StringAttribute(
            value = "12345",
            model = model,
            validators = listOf(validator),
            label = Labels.TEST
        )

        //then
        assertFalse(attr.isValid())
        assertEquals("Der Wert darf maximal 4 Buchstaben haben.", attr.getErrorMessages()[0])

        //when
        validator.overrideStringValidator(maxLength = 10)

        //then
        assertTrue(attr.isValid())


        //given
        val attr2 = StringAttribute(
            value = "1234",
            model = model,
            validators = listOf(StringValidator(4)),
            label = Labels.TEST
        )

        //then
        assertTrue(attr2.isValid())

        //then
        assertThrows(IllegalArgumentException::class.java){
            //when
            val attr3 = StringAttribute(
                value = "1234",
                model = model,
                validators = listOf(StringValidator(maxLength = -1)),
                label = Labels.TEST
            )
        }

        //then
        assertThrows(IllegalArgumentException::class.java){
            //when
            validator.overrideStringValidator(5,3,"")
        }
    }

    @Test
    fun testRequiredInConstructor() {
        //given
        val attr = StringAttribute(
            value = "",
            model = model,
            required = true,
            label = Labels.TEST
        )

        //then
        assertFalse(attr.isValid())
        assertEquals("Input required", attr.getErrorMessages()[0])

        //when
        attr.setRequired(false)

        //then
        assertTrue(attr.isValid())


        //given
        val attr2 = StringAttribute(
            value = "1234",
            model = model,
            required = false,
            label = Labels.TEST
        )

        //then
        assertTrue(attr2.isValid())
    }

}