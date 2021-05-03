//package model.util.attribute
//
//import model.BaseFormModel
//import org.junit.jupiter.api.Assertions
//import org.junit.jupiter.api.Assertions.*
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import java.lang.IllegalArgumentException
//
//internal class StringAttributeTest: AttributeTest<String>() {
//
//    override fun provideAttribute(model: BaseFormModel, value: String?): Attribute<*, Any> {
//        return StringAttribute(model, value) as Attribute<*, Any>
//    }
//
//    init{
//        validValue1Uneven = "Hallo"
//        validValue1AsText  = "Hallo"
//
//        validValue2        = "789"
//        validValue2AsText  = "789"
//
//        validValue3        = "AEIOU"
//        validValue3AsText  = "AEIOU"
//
//        validValue4        = "Name"
//        validValue4AsText  = "Name"
//
//        notValidValueAsText  = "A".repeat(1_000_000 + 1)
//
//        validationMessage     = "Validation mismatched (maxLength)"
//    }
//
//    lateinit var stringAtr : StringAttribute
//
//    @BeforeEach
//    fun setUpStringAtr(){
//        //given
//        stringAtr = StringAttribute(model, validValue1Uneven)
//    }
//
//    @Test
//    fun setMinLength() {
//        //then
//        assertTrue(stringAtr.isValid())
//
//        //when
//        stringAtr.setMinLength(7)
//
//        //then
//        assertFalse(stringAtr.isValid())
//    }
//
//    @Test
//    fun setMaxLength() {
//        //then
//        assertTrue(stringAtr.isValid())
//
//        //when
//        stringAtr.setMaxLength(3)
//
//        //then
//        assertFalse(stringAtr.isValid())
//    }
//
//    @Test
//    fun getMinLength() {
//        //then
//        assertEquals(0,stringAtr.getMinLength())
//
//        //when
//        stringAtr.setMinLength(6)
//
//        //then
//        assertEquals(6,stringAtr.getMinLength())
//        assertFalse(stringAtr.isValid())
//
//        //then
//        assertThrows(IllegalArgumentException::class.java){
//            //when
//            stringAtr.setMinLength(-1)
//        }
//
//        //when
//        stringAtr.setMaxLength(7)
//
//        //then
//        assertThrows(IllegalArgumentException::class.java) {
//            //when
//            stringAtr.setMinLength(8)
//        }
//
//    }
//
//    @Test
//    fun getMaxLength() {
//        //then
//        assertEquals(1_000_000, stringAtr.getMaxLength())
//
//        //when
//        stringAtr.setMaxLength(4)
//
//        //then
//        assertEquals(4,stringAtr.getMaxLength())
//        assertFalse(stringAtr.isValid())
//
//        //then
//        assertThrows(IllegalArgumentException::class.java){
//            //when
//            stringAtr.setMaxLength(-1)
//        }
//
//        //when
//        stringAtr.setMinLength(3)
//
//        //then
//        Assertions.assertThrows(IllegalArgumentException::class.java) {
//            //when
//            stringAtr.setMaxLength(2)
//        }
//    }
//}