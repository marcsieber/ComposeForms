package model.util.attribute

import model.BaseFormModel
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SelectionAttributeTest: AttributeTest<Set<String>>() {

    override fun provideAttribute(model: BaseFormModel, value: Set<String>?): Attribute<*, Any> {
        return SelectionAttribute(model, value!!) as Attribute<*, Any>
    }

    init{
        validValue1Uneven = setOf<String>("") //todo: define
        validValue1AsText  = validValue1Uneven.toString()

        validValue2        = setOf<String>("") //todo: define
        validValue2AsText  = validValue2.toString()

        validValue3        = setOf<String>("") //todo: define
        validValue3AsText  = validValue3.toString()

        validValue4        = setOf<String>("") //todo: define
        validValue4AsText  = validValue4.toString()

        notValidValueAsText  = setOf<String>("").toString() //todo: define

        validationMessage     = "" //todo: define
    }

    lateinit var selAtr : SelectionAttribute

    @Test
    fun checkAndSetValue() {
        //todo
    }

    @Test
    fun setMinNumberOfSelections() {
        //todo
    }

    @Test
    fun setMaxNumberOfSelections() {
        //todo
    }

    @Test
    fun setPossibleSelections() {
        //todo
    }

    @Test
    fun addANewPossibleSelection() {
        //todo
    }

    @Test
    fun deleteAPossibleSelection() {
        //todo
    }

    @Test
    fun getMinNumberOfSelections() {
        //todo
    }

    @Test
    fun getMaxNumberOfSelections() {
        //todo
    }

    @Test
    fun getPossibleSelections() {
        //todo
    }

}