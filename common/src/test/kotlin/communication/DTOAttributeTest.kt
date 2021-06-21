package communication

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DTOAttributeTest{

    @Test
    fun testValuesDefault(){
        //when
        val dtoAttribute = DTOAttribute(1, "label")

        //then
        assertEquals(1, dtoAttribute.id)
        assertEquals("label", dtoAttribute.label)
        assertEquals(AttributeType.OTHER, dtoAttribute.attrType)
        assertEquals(emptySet<String>(), dtoAttribute.possibleSelections)
    }

    @Test
    fun testValues(){
        //given
        val set = setOf("hallo", "123")
        val attributeType = AttributeType.SELECTION

        //when
        val dtoAttribute = DTOAttribute(1, "label", attributeType, set)

        //then
        assertEquals(1, dtoAttribute.id)
        assertEquals("label", dtoAttribute.label)
        assertEquals(attributeType, dtoAttribute.attrType)
        assertEquals(set, dtoAttribute.possibleSelections)
    }
}