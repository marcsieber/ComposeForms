package model.util.attribute

import model.BaseFormModel
import model.validators.semanticValidators.NumberValidator
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

abstract class NumberAttributeTest<T> : AttributeTest<T>() where T : Number, T : Comparable<T>{

    lateinit var upperBound : T
    lateinit var lowerBoundWrong_becauseGreaterThanUpperBound : T
    lateinit var lowerBoundCorrect : T

    lateinit var lowerBound : T
    lateinit var upperBoundWrong_becauseLowerThanLowerBound : T
    lateinit var upperBoundCorrect : T

    lateinit var stepSizeCorrect_even : T
    lateinit var stepSizeWrong_because0  : T
    lateinit var stepSizeWrong_becauseNegative  : T
    lateinit var notValidValueBecauseWrongStepAsText : String
    lateinit var valueWithCorrectStepSize : T

    lateinit var numAt : NumberAttribute<*, T,*>

    abstract fun provideNumberAttribute(model: BaseFormModel, value: T?) : NumberAttribute<*, T,*>
    
    @BeforeEach
    fun setUpNumAtr(){
        //given
        numAt = provideNumberAttribute(model, validValue1Uneven)
    }

    @Test
    fun testNumberValidator_LowerBound() {

        if(numAt is DoubleAttribute){
            //given
            val numValidator = NumberValidator(upperBound = upperBound as Double)
            (numAt as DoubleAttribute).addValidator(numValidator)

            //then
            assertThrows(IllegalArgumentException::class.java) {
                //when
                numValidator.overrideNumberValidator(lowerBoundWrong_becauseGreaterThanUpperBound as Double)
            }
            assertNotEquals(lowerBoundWrong_becauseGreaterThanUpperBound, numValidator.getLowerBound())
            assertEquals(upperBound, numValidator.getUpperBound())

            //when
            numValidator.overrideNumberValidator(lowerBound = lowerBoundCorrect as Double)

            //then
            Assertions.assertSame(lowerBoundCorrect, numValidator.getLowerBound())

        }
        if(numAt is FloatAttribute){
            //given
            val numValidator = NumberValidator(upperBound = upperBound as Float)
            (numAt as FloatAttribute).addValidator(numValidator)

            //then
            assertThrows(IllegalArgumentException::class.java) {
                //when
                numValidator.overrideNumberValidator(lowerBoundWrong_becauseGreaterThanUpperBound as Float)
            }
            assertNotEquals(lowerBoundWrong_becauseGreaterThanUpperBound, numValidator.getLowerBound())
            assertEquals(upperBound, numValidator.getUpperBound())

            //when
            numValidator.overrideNumberValidator(lowerBound = lowerBoundCorrect as Float)

            //then
            Assertions.assertSame(lowerBoundCorrect, numValidator.getLowerBound())

        }
        if(numAt is IntegerAttribute) {
            //given
            val numValidator = NumberValidator(upperBound = upperBound as Int)
            (numAt as IntegerAttribute).addValidator(numValidator)

            //then
            assertThrows(IllegalArgumentException::class.java) {
                //when
                numValidator.overrideNumberValidator(lowerBoundWrong_becauseGreaterThanUpperBound as Int)
            }
            assertNotEquals(lowerBoundWrong_becauseGreaterThanUpperBound, numValidator.getLowerBound())
            assertEquals(upperBound, numValidator.getUpperBound())

            //when
            numValidator.overrideNumberValidator(lowerBound = lowerBoundCorrect as Int)

            //then
            Assertions.assertSame(lowerBoundCorrect, numValidator.getLowerBound())

        }
        if(numAt is LongAttribute){
            //given
            val numValidator = NumberValidator(upperBound = upperBound as Long)
            (numAt as LongAttribute).addValidator(numValidator)

            //then
            assertThrows(IllegalArgumentException::class.java) {
                //when
                numValidator.overrideNumberValidator(lowerBoundWrong_becauseGreaterThanUpperBound as Long)
            }
            assertNotEquals(lowerBoundWrong_becauseGreaterThanUpperBound, numValidator.getLowerBound())
            assertEquals(upperBound, numValidator.getUpperBound())

            //when
            numValidator.overrideNumberValidator(lowerBound = lowerBoundCorrect as Long)

            //then
            Assertions.assertSame(lowerBoundCorrect, numValidator.getLowerBound())

        }
        if(numAt is ShortAttribute){
            //given
            val numValidator = NumberValidator(upperBound = upperBound as Short)
            (numAt as ShortAttribute).addValidator(numValidator)

            //then
            assertThrows(IllegalArgumentException::class.java) {
                //when
                numValidator.overrideNumberValidator(lowerBoundWrong_becauseGreaterThanUpperBound as Short)
            }
            assertNotEquals(lowerBoundWrong_becauseGreaterThanUpperBound, numValidator.getLowerBound())
            assertEquals(upperBound, numValidator.getUpperBound())

            //when
            numValidator.overrideNumberValidator(lowerBound = lowerBoundCorrect as Short)

            //then
            Assertions.assertSame(lowerBoundCorrect, numValidator.getLowerBound())

        }
    }

    @Test
    fun testNumberValidator_UpperBound(){
        if(numAt is DoubleAttribute){
            //given
            val numValidator = NumberValidator(lowerBound = lowerBound as Double)
            (numAt as DoubleAttribute).addValidator(numValidator)

            //then
            assertThrows(IllegalArgumentException::class.java) {
                //when
                numValidator.overrideNumberValidator(upperBound = upperBoundWrong_becauseLowerThanLowerBound as Double)
            }
            assertNotEquals(upperBoundWrong_becauseLowerThanLowerBound, numValidator.getUpperBound())
            assertEquals(lowerBound, numValidator.getLowerBound())

            //when
            numValidator.overrideNumberValidator(upperBound = upperBoundCorrect as Double)

            //then
            Assertions.assertSame(upperBoundCorrect, numValidator.getUpperBound())

        }
        if(numAt is FloatAttribute){
            //given
            val numValidator = NumberValidator(lowerBound = lowerBound as Float)
            (numAt as FloatAttribute).addValidator(numValidator)

            //then
            assertThrows(IllegalArgumentException::class.java) {
                //when
                numValidator.overrideNumberValidator(upperBound = upperBoundWrong_becauseLowerThanLowerBound as Float)
            }
            assertNotEquals(upperBoundWrong_becauseLowerThanLowerBound, numValidator.getUpperBound())
            assertEquals(lowerBound, numValidator.getLowerBound())

            //when
            numValidator.overrideNumberValidator(upperBound = upperBoundCorrect as Float)

            //then
            Assertions.assertSame(upperBoundCorrect, numValidator.getUpperBound())


        }
        if(numAt is IntegerAttribute) {
            //given
            val numValidator = NumberValidator(lowerBound = lowerBound as Int)
            (numAt as IntegerAttribute).addValidator(numValidator)

            //then
            assertThrows(IllegalArgumentException::class.java) {
                //when
                numValidator.overrideNumberValidator(upperBound = upperBoundWrong_becauseLowerThanLowerBound as Int)
            }
            assertNotEquals(upperBoundWrong_becauseLowerThanLowerBound, numValidator.getUpperBound())
            assertEquals(lowerBound, numValidator.getLowerBound())

            //when
            numValidator.overrideNumberValidator(upperBound = upperBoundCorrect as Int)

            //then
            Assertions.assertSame(upperBoundCorrect, numValidator.getUpperBound())


        }
        if(numAt is LongAttribute){
            //given
            val numValidator = NumberValidator(lowerBound = lowerBound as Long)
            (numAt as LongAttribute).addValidator(numValidator)

            //then
            assertThrows(IllegalArgumentException::class.java) {
                //when
                numValidator.overrideNumberValidator(upperBound = upperBoundWrong_becauseLowerThanLowerBound as Long)
            }
            assertNotEquals(upperBoundWrong_becauseLowerThanLowerBound, numValidator.getUpperBound())
            assertEquals(lowerBound, numValidator.getLowerBound())

            //when
            numValidator.overrideNumberValidator(upperBound = upperBoundCorrect as Long)

            //then
            Assertions.assertSame(upperBoundCorrect, numValidator.getUpperBound())

        }
        if(numAt is ShortAttribute){
            //given
            val numValidator = NumberValidator(lowerBound = lowerBound as Short)
            (numAt as ShortAttribute).addValidator(numValidator)

            //then
            assertThrows(IllegalArgumentException::class.java) {
                //when
                numValidator.overrideNumberValidator(upperBound = upperBoundWrong_becauseLowerThanLowerBound as Short)
            }
            assertNotEquals(upperBoundWrong_becauseLowerThanLowerBound, numValidator.getUpperBound())
            assertEquals(lowerBound, numValidator.getLowerBound())

            //when
            numValidator.overrideNumberValidator(upperBound = upperBoundCorrect as Short)

            //then
            Assertions.assertSame(upperBoundCorrect, numValidator.getUpperBound())

        }
    }


    @Test
    fun testNumberValidator_OnlyStepSizValid(){

        if(numAt is DoubleAttribute){
            //given
            val numValidator = NumberValidator(stepSize = stepSizeCorrect_even as Double, stepStart = validValue1Uneven as Double)
            (numAt as DoubleAttribute).addValidator(numValidator)

            //when
            numAt.setValueAsText(notValidValueBecauseWrongStepAsText)

            //then
            assertTrue(numAt.isValid())
            assertEquals(stepSizeCorrect_even, numValidator.getStepSize())

            //when
            numValidator.overrideNumberValidator(onlyStepValuesAreValid = true)

            //then
            assertFalse(numAt.isValid())
            assertEquals(true, numValidator.isOnlyStepValuesAreValid())

        }
        if(numAt is FloatAttribute){
            //given
            val numValidator = NumberValidator(stepSize = stepSizeCorrect_even as Float, stepStart = validValue1Uneven as Float)
            (numAt as FloatAttribute).addValidator(numValidator)

            //when
            numAt.setValueAsText(notValidValueBecauseWrongStepAsText)

            //then
            assertTrue(numAt.isValid())
            assertEquals(stepSizeCorrect_even, numValidator.getStepSize())

            //when
            numValidator.overrideNumberValidator(onlyStepValuesAreValid = true)

            //then
            assertFalse(numAt.isValid())
            assertEquals(true, numValidator.isOnlyStepValuesAreValid())

        }
        if(numAt is IntegerAttribute) {
            //given
            val numValidator = NumberValidator(stepSize = stepSizeCorrect_even as Int, stepStart = validValue1Uneven as Int)
            (numAt as IntegerAttribute).addValidator(numValidator)

            //when
            numAt.setValueAsText(notValidValueBecauseWrongStepAsText)

            //then
            assertTrue(numAt.isValid())
            assertEquals(stepSizeCorrect_even, numValidator.getStepSize())

            //when
            numValidator.overrideNumberValidator(onlyStepValuesAreValid = true)

            //then
            assertFalse(numAt.isValid())
            assertEquals(true, numValidator.isOnlyStepValuesAreValid())
        }
        if(numAt is LongAttribute){
            //given
            val numValidator = NumberValidator(stepSize = stepSizeCorrect_even as Long, stepStart = validValue1Uneven as Long)
            (numAt as LongAttribute).addValidator(numValidator)

            //when
            numAt.setValueAsText(notValidValueBecauseWrongStepAsText)

            //then
            assertTrue(numAt.isValid())
            assertEquals(stepSizeCorrect_even, numValidator.getStepSize())

            //when
            numValidator.overrideNumberValidator(onlyStepValuesAreValid = true)

            //then
            assertFalse(numAt.isValid())
            assertEquals(true, numValidator.isOnlyStepValuesAreValid())

        }
        if(numAt is ShortAttribute){
            //given
            val numValidator = NumberValidator(stepSize = stepSizeCorrect_even as Short, stepStart = validValue1Uneven as Short)
            (numAt as ShortAttribute).addValidator(numValidator)

            //when
            numAt.setValueAsText(notValidValueBecauseWrongStepAsText)

            //then
            assertTrue(numAt.isValid())
            assertEquals(stepSizeCorrect_even, numValidator.getStepSize())

            //when
            numValidator.overrideNumberValidator(onlyStepValuesAreValid = true)

            //then
            assertFalse(numAt.isValid())
            assertEquals(true, numValidator.isOnlyStepValuesAreValid())
        }
    }

    //    @Test
//    fun testSetStepSize() {
//        //when
//        numAt.setStepSize(stepSizeCorrect_even)
//
//        //then
//        assertEquals(stepSizeCorrect_even, numAt.getStepSize(), "valid stepSize")
//        assertEquals(validValue1Uneven, numAt.getStepStart(), "correct stepStart")
//
//
//        //when
//        numAt.setValueAsText(valueWithCorrectStepSize.toString())
//
//        //then
//        assertTrue(numAt.isValid(), "valid value")
//
//        //when
//        numAt.setValueAsText(notValidValueBecauseWrongStepAsText)
//        numAt.setOnlyStepValuesAreValid(true)
//
//        //then
//        Assertions.assertFalse(numAt.isValid(), "invalid value, because of stepSize")
//
//        //then
//        Assertions.assertThrows(IllegalArgumentException::class.java) {
//            //when
//            numAt.setStepSize(stepSizeWrong_because0)
//        }
//
//
//        //then
//        Assertions.assertThrows(IllegalArgumentException::class.java) {
//            //when
//            numAt.setStepSize(stepSizeWrong_becauseNegative)
//        }
//    }

    @Test
    fun testNumberValidator_StepSize(){

        if(numAt is DoubleAttribute){
            //given
            val numValidator = NumberValidator(stepSize = stepSizeCorrect_even as Double, stepStart = validValue1Uneven as Double)
            (numAt as DoubleAttribute).addValidator(numValidator)

            //then
            assertEquals(stepSizeCorrect_even, numValidator.getStepSize(), "valid stepSize")
            assertEquals(validValue1Uneven, numValidator.getStepStart(), "correct stepStart")

            //when
            numAt.setValueAsText(valueWithCorrectStepSize.toString())

            //then
            assertTrue(numAt.isValid(), "valid value")

            //when
            numAt.setValueAsText(notValidValueBecauseWrongStepAsText)
            numValidator.overrideNumberValidator(onlyStepValuesAreValid = true)

            //then
            Assertions.assertFalse(numAt.isValid(), "invalid value, because of stepSize")

            //then
            Assertions.assertThrows(IllegalArgumentException::class.java) {
                //when
                numValidator.overrideNumberValidator(stepSize = stepSizeWrong_because0 as Double)
            }

            //then
            Assertions.assertThrows(IllegalArgumentException::class.java) {
                //when
                numValidator.overrideNumberValidator(stepSize = stepSizeWrong_becauseNegative as Double)
            }
        }
        if(numAt is FloatAttribute){
            //given
            val numValidator = NumberValidator(stepSize = stepSizeCorrect_even as Float, stepStart = validValue1Uneven as Float)
            (numAt as FloatAttribute).addValidator(numValidator)

            //then
            assertEquals(stepSizeCorrect_even, numValidator.getStepSize(), "valid stepSize")
            assertEquals(validValue1Uneven, numValidator.getStepStart(), "correct stepStart")

            //when
            numAt.setValueAsText(valueWithCorrectStepSize.toString())

            //then
            assertTrue(numAt.isValid(), "valid value")

            //when
            numAt.setValueAsText(notValidValueBecauseWrongStepAsText)
            numValidator.overrideNumberValidator(onlyStepValuesAreValid = true)

            //then
            Assertions.assertFalse(numAt.isValid(), "invalid value, because of stepSize")

            //then
            Assertions.assertThrows(IllegalArgumentException::class.java) {
                //when
                numValidator.overrideNumberValidator(stepSize = stepSizeWrong_because0 as Float)
            }

            //then
            Assertions.assertThrows(IllegalArgumentException::class.java) {
                //when
                numValidator.overrideNumberValidator(stepSize = stepSizeWrong_becauseNegative as Float)
            }

        }
        if(numAt is IntegerAttribute) {
            //given
            val numValidator = NumberValidator(stepSize = stepSizeCorrect_even as Int, stepStart = validValue1Uneven as Int)
            (numAt as IntegerAttribute).addValidator(numValidator)

            //then
            assertEquals(stepSizeCorrect_even, numValidator.getStepSize(), "valid stepSize")
            assertEquals(validValue1Uneven, numValidator.getStepStart(), "correct stepStart")

            //when
            numAt.setValueAsText(valueWithCorrectStepSize.toString())

            //then
            assertTrue(numAt.isValid(), "valid value")

            //when
            numAt.setValueAsText(notValidValueBecauseWrongStepAsText)
            numValidator.overrideNumberValidator(onlyStepValuesAreValid = true)

            //then
            Assertions.assertFalse(numAt.isValid(), "invalid value, because of stepSize")

            //then
            Assertions.assertThrows(IllegalArgumentException::class.java) {
                //when
                numValidator.overrideNumberValidator(stepSize = stepSizeWrong_because0 as Int)
            }

            //then
            Assertions.assertThrows(IllegalArgumentException::class.java) {
                //when
                numValidator.overrideNumberValidator(stepSize = stepSizeWrong_becauseNegative as Int)
            }
        }
        if(numAt is LongAttribute){
            //given
            val numValidator = NumberValidator(stepSize = stepSizeCorrect_even as Long, stepStart = validValue1Uneven as Long)
            (numAt as LongAttribute).addValidator(numValidator)

            //then
            assertEquals(stepSizeCorrect_even, numValidator.getStepSize(), "valid stepSize")
            assertEquals(validValue1Uneven, numValidator.getStepStart(), "correct stepStart")

            //when
            numAt.setValueAsText(valueWithCorrectStepSize.toString())

            //then
            assertTrue(numAt.isValid(), "valid value")

            //when
            numAt.setValueAsText(notValidValueBecauseWrongStepAsText)
            numValidator.overrideNumberValidator(onlyStepValuesAreValid = true)

            //then
            Assertions.assertFalse(numAt.isValid(), "invalid value, because of stepSize")

            //then
            Assertions.assertThrows(IllegalArgumentException::class.java) {
                //when
                numValidator.overrideNumberValidator(stepSize = stepSizeWrong_because0 as Long)
            }

            //then
            Assertions.assertThrows(IllegalArgumentException::class.java) {
                //when
                numValidator.overrideNumberValidator(stepSize = stepSizeWrong_becauseNegative as Long)
            }

        }
        if(numAt is ShortAttribute){
            //given
            val numValidator = NumberValidator(stepSize = stepSizeCorrect_even as Short, stepStart = validValue1Uneven as Short)
            (numAt as ShortAttribute).addValidator(numValidator)

            //then
            assertEquals(stepSizeCorrect_even, numValidator.getStepSize(), "valid stepSize")
            assertEquals(validValue1Uneven, numValidator.getStepStart(), "correct stepStart")

            //when
            numAt.setValueAsText(valueWithCorrectStepSize.toString())

            //then
            assertTrue(numAt.isValid(), "valid value")

            //when
            numAt.setValueAsText(notValidValueBecauseWrongStepAsText)
            numValidator.overrideNumberValidator(onlyStepValuesAreValid = true)

            //then
            Assertions.assertFalse(numAt.isValid(), "invalid value, because of stepSize")

            //then
            Assertions.assertThrows(IllegalArgumentException::class.java) {
                //when
                numValidator.overrideNumberValidator(stepSize = stepSizeWrong_because0 as Short)
            }

            //then
            Assertions.assertThrows(IllegalArgumentException::class.java) {
                //when
                numValidator.overrideNumberValidator(stepSize = stepSizeWrong_becauseNegative as Short)
            }
        }
    }
}