package communication

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DTOValidationTest{


    @Test
    fun testDefaultValues(){
        //when
        val dtoValidation = DTOValidation()

        //then
        assertEquals(true, dtoValidation.onRightTrack)
        assertEquals(true, dtoValidation.isValid)
        assertEquals(false, dtoValidation.readOnly)
        assertEquals(listOf<String>(), dtoValidation.errorMessages)
    }
}