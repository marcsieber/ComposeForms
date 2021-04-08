package model.util.attribute

import model.BaseFormModel
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.lang.IllegalArgumentException

internal class IntegerAttributeTest: AttributeTest<Int>() {

    override fun provideAttribute(model: BaseFormModel, value: Int): Attribute<*,*> {
        return IntegerAttribute(model, value)
    }

    init{
        validValue1        = 5
        validValue1AsText  = "5"

        validValue2        = 7
        validValue2AsText  = "7"

        validValue3        = 12
        validValue3AsText  = "12"

        validValue4        = 14
        validValue4AsText  = "14"

        notValidValue1AsText   = "a"
        validationMessage = "No Integer"
    }


    @Test
    fun testSetLowerBound() {
        //given
        val intA = IntegerAttribute(model,12)
        val upperBound = 10
        val lowerBoundWrong1 = 11
        val lowerBoundWrong2 = 10
        val lowerBoundCorrect = 0

        //when
        intA.setUpperBound(upperBound)

        //then
        assertThrows(IllegalArgumentException :: class.java){
            //when
            intA.setLowerBound(lowerBoundWrong1)
        }

        //then
        assertThrows(IllegalArgumentException :: class.java){
            //when
            intA.setLowerBound(lowerBoundWrong2)
        }

        //when
        intA.setLowerBound(lowerBoundCorrect)

        //then
        assertSame(lowerBoundCorrect, intA.getLowerBound())
    }

    @Test
    fun testGetLowerBound() {
        //given
        val intA = IntegerAttribute(model,24)
        val lowerBound = 3

        //when
        intA.setLowerBound(lowerBound)

        //then
        assertSame(lowerBound, intA.getLowerBound())
    }

    @Test
    fun testSetUpperBound() {
        //given
        val intA = IntegerAttribute(model,12)
        val lowerBound = 10
        val upperBoundWrong1 = 9
        val upperBoundWrong2 = 10
        val upperBoundCorrect = 14

        //when
        intA.setLowerBound(lowerBound)

        //then
        assertThrows(IllegalArgumentException :: class.java){
            //when
            intA.setUpperBound(upperBoundWrong1)
        }

        //then
        assertThrows(IllegalArgumentException :: class.java){
            //when
            intA.setUpperBound(upperBoundWrong2)
        }

        //when
        intA.setUpperBound(upperBoundCorrect)

        //then
        assertSame(upperBoundCorrect, intA.getUpperBound())
    }

    @Test
    fun testGetUpperBound() {
        //given
        val intA = IntegerAttribute(model,24)
        val upperBound = 3

        //when
        intA.setUpperBound(upperBound)

        //then
        assertSame(upperBound, intA.getUpperBound())
    }

    @Test
    fun testSetStepSize() {
        //given
        val intA = IntegerAttribute(model,12)
        val stepSize = 2

        //when
        intA.setStepSize(stepSize)

        //then
        assertSame(2,intA.getStepSize())
    }

    @Test
    fun testGetStepSize() {
        //given
        val intA = IntegerAttribute(model,24)
        val stepSize = 4

        //when
        intA.setStepSize(stepSize)

        //then
        assertSame(stepSize, intA.getStepSize())

        //when
        intA.setValueAsText("28")

        //then
        assertSame(true, intA.isValid())

        //when
        intA.setValueAsText("25")

        //then
        assertSame(false, intA.isValid())
    }



}
