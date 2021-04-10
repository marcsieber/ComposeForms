package model.util.attribute

import model.BaseFormModel
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

abstract class NumberAttributeTest<T> : AttributeTest<T>() where T : Number, T : Comparable<T>{

    lateinit var upperBound : T
    lateinit var lowerBoundWrong1 : T
    lateinit var lowerBoundWrong2 : T
    lateinit var lowerBoundCorrect : T

    lateinit var lowerBound : T
    lateinit var upperBoundWrong1 : T
    lateinit var upperBoundWrong2 : T
    lateinit var upperBoundCorrect : T

    lateinit var stepSizeCorrect : T
    lateinit var stepSizeWrong1  : T
    lateinit var stepSizeWrong2  : T
    lateinit var notValidValueBecauseWrongStepAsText : String

    lateinit var numAt : NumberAttribute<*, T>

    abstract fun provideNumberAttribute(model: BaseFormModel, value: T) : NumberAttribute<*, T>
    
    @BeforeEach
    fun setUpNumAtr(){
        //given
        numAt = provideNumberAttribute(model, validValue1)
    }

    @Test
    fun testSetLowerBound() {
        //when
        numAt.setUpperBound(upperBound)

        //then
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            //when
            numAt.setLowerBound(lowerBoundWrong1)
        }

        //then
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            //when
            numAt.setLowerBound(lowerBoundWrong2)
        }

        //when
        numAt.setLowerBound(lowerBoundCorrect)

        //then
        Assertions.assertSame(lowerBoundCorrect, numAt.getLowerBound())
    }

    @Test
    fun testGetLowerBound() {
        //when
        numAt.setLowerBound(lowerBound)

        //then
        Assertions.assertSame(lowerBound, numAt.getLowerBound())
    }

    @Test
    fun testSetUpperBound() {
        //when
        numAt.setLowerBound(lowerBound)

        //then
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            //when
            numAt.setUpperBound(upperBoundWrong1)
        }

        //then
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            //when
            numAt.setUpperBound(upperBoundWrong2)
        }

        //when
        numAt.setUpperBound(upperBoundCorrect)

        //then
        Assertions.assertSame(upperBoundCorrect, numAt.getUpperBound())
    }

    @Test
    fun testGetUpperBound() {
        //when
        numAt.setUpperBound(upperBound)

        //then
        Assertions.assertSame(upperBound, numAt.getUpperBound())
    }

    @Test
    fun testSetStepSize() { //TODO negative step sizes
        //when
        numAt.setStepSize(stepSizeCorrect)

        //then
        Assertions.assertSame(stepSizeCorrect, numAt.getStepSize())
    }

    @Test
    fun testGetStepSize() {
        //when
        numAt.setStepSize(stepSizeCorrect)

        //then
        Assertions.assertSame(stepSizeCorrect, numAt.getStepSize())

        //when
        numAt.setValueAsText(validValue1AsText)

        //then
        Assertions.assertSame(true, numAt.isValid())

        //when
        numAt.setValueAsText(notValidValueBecauseWrongStepAsText)

        //then
        Assertions.assertFalse(numAt.isValid())
    }

}