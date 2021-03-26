package model.util

//import org.testng.Assert.assertEquals
//import org.testng.annotations.Test

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class ObservableTest{

    @Test
    fun testGetValue(){
        //given
        val obs = Observable(1)
        //then
        assertEquals( 1, obs.getValue(), "Not set value")
    }


    @Test
    fun testSetValue(){
        //given
        val obs = Observable(1)
        //when
        obs.setValue(2)
        //then
        assertEquals( 2, obs.getValue(), "Not set value")
    }

    @Test
    fun testAddListener(){
        //given
        val obs = Observable(1)
        var testval = "empty"
        //when
        obs.addListener{ s -> testval = "changed" }
        obs.setValue(2)
        //then
        assertEquals( "changed", testval, "Listener not triggered")
    }

    @Test
    fun testSetSameVal(){
        //given
        val obs = Observable(1)
        var testval = "empty"
        //when
        obs.addListener{ s -> testval = "changed" }
        obs.setValue(1)
        //then
        assertEquals( "empty", testval, "Listener not triggered")
    }
}