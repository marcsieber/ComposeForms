package model.util.attribute

import model.BaseFormModel
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class StringAttributeTest: AttributeTest<String>() {

    override fun provideAttribute(model: BaseFormModel, value: String): Attribute<*,*> {
        return StringAttribute(model, value).setMaxLength(10)
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

        notValidValue1AsText  = "123456789101112"
        validationMessage     = "Validation mismatched (maxLength)"
    }

    @Test
    fun checkAndSetValue() {
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