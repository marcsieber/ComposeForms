package model.util.attribute

import model.BaseFormModel
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

abstract class NumberAttributeTest<T> : AttributeTest<T>() where T : Number, T : Comparable<T>{

    lateinit var upperBound : T
    lateinit var lowerBoundWrong_becauseGreaterThanUpperBound : T
    lateinit var lowerBoundWrong_becauseSameAsUpperBound : T
    lateinit var lowerBoundCorrect : T

    lateinit var lowerBound : T
    lateinit var upperBoundWrong_becauseLowerThanLowerBound : T
    lateinit var upperBoundWrong_becauseSameAsLowerBound : T
    lateinit var upperBoundCorrect : T

    lateinit var stepSizeCorrect_even : T
    lateinit var stepSizeWrong_because0  : T
    lateinit var stepSizeWrong_becauseNegative  : T
    lateinit var notValidValueBecauseWrongStepAsText : String
    lateinit var valueWithCorrectStepSize : T

    lateinit var numAt : NumberAttribute<*, T>

    abstract fun provideNumberAttribute(model: BaseFormModel, value: T?) : NumberAttribute<*, T>
    
    @BeforeEach
    fun setUpNumAtr(){
        //given
        numAt = provideNumberAttribute(model, validValue1_uneven)
    }

    @Test
    fun testSetLowerBound() {
        //when
        numAt.setUpperBound(upperBound)

        //then
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            //when
            numAt.setLowerBound(lowerBoundWrong_becauseGreaterThanUpperBound)
        }

        //then
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            //when
            numAt.setLowerBound(lowerBoundWrong_becauseSameAsUpperBound)
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
            numAt.setUpperBound(upperBoundWrong_becauseLowerThanLowerBound)
        }

        //then
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            //when
            numAt.setUpperBound(upperBoundWrong_becauseSameAsLowerBound)
        }

        //when
        numAt.setUpperBound(upperBoundCorrect)

        //then
        Assertions.assertEquals(upperBoundCorrect, numAt.getUpperBound(), "correct upperBound")
    }

    @Test
    fun testGetUpperBound() {
        //when
        numAt.setUpperBound(upperBound)

        //then
        Assertions.assertSame(upperBound, numAt.getUpperBound())
    }

    @Test
    fun testSetStepSize() {
        //when
        numAt.setStepSize(stepSizeCorrect_even)

        //then
        assertEquals(stepSizeCorrect_even, numAt.getStepSize(), "valid stepSize")
        assertEquals(validValue1_uneven, numAt.getStepStart(), "correct stepStart")


        //when
        numAt.setValueAsText(valueWithCorrectStepSize.toString())

        //then
        assertTrue(numAt.isValid(), "valid value")

        //when
        numAt.setValueAsText(notValidValueBecauseWrongStepAsText)

        //then
        Assertions.assertFalse(numAt.isValid(), "invalid value, because of stepSize")

        //then
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            //when
            numAt.setStepSize(stepSizeWrong_because0)
        }

        //then
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            //when
            numAt.setStepSize(stepSizeWrong_because0)
        }

        //then
        Assertions.assertThrows(IllegalArgumentException::class.java) {
            //when
            numAt.setStepSize(stepSizeWrong_becauseNegative)
        }
    }

    @Test
    fun testGetStepSize() {
        //when
        numAt.setStepSize(stepSizeCorrect_even)

        //then
        assertEquals(stepSizeCorrect_even, numAt.getStepSize(), "valid stepSize")
    }

    @Test
    fun testGetStepStart() {
        //then
        assertEquals(validValue1_uneven, numAt.getStepStart())

        //when
        numAt.setValueAsText(validValue3AsText)

        //then
        assertEquals(validValue1_uneven, numAt.getStepStart())
    }
}