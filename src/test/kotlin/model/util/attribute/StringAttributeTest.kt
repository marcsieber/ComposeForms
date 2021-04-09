package model.util.attribute

import model.BaseFormModel
import org.junit.jupiter.api.Test

internal class StringAttributeTest: AttributeTest<String>() {

    override fun provideAttribute(model: BaseFormModel, value: String): Attribute<*, Any> {
        return StringAttribute(model, value) as Attribute<*, Any>
    }

    init{
        validValue1        = "Hallo"
        validValue1AsText  = "Hallo"

        validValue2        = "789"
        validValue2AsText  = "789"

        validValue3        = "AEIOU"
        validValue3AsText  = "AEIOU"

        validValue4        = "Name"
        validValue4AsText  = "Name"

        notValidValueAsText  = "123456789101112"
        validationMessage     = "Validation mismatched (maxLength)"
    }

    @Test
    fun checkAndSetValue() {
        //when

    }

    @Test
    fun setMinLength() {
    }

    @Test
    fun setMaxLength() {
    }

    @Test
    fun getMinLength() {
    }

    @Test
    fun getMaxLength() {
    }
}