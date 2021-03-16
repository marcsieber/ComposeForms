import androidx.compose.runtime.mutableStateOf
import model.util.HelpFunctions

class AppModel() : HelpFunctions(){
    var count = mutableStateOf(0)

    fun increaseCount(){
        count.value++
    }

    fun resetCount(){
        count.value = 0
    }

    var stringValue = mutableStateOf("Ich bin Text")
    var longValue   = mutableStateOf("5")

    var intValue    = createIntegerAttr(5)



    var doubleValue = mutableStateOf("6.0")
    var floatValue  = mutableStateOf("7.9")
    var dateValue   = mutableStateOf("01/05/2020")
    var booleanValue = mutableStateOf(true)
    var radioButtonValue = mutableStateOf(true)

    var dropDownItems  = listOf("1","2","3","4")
    var dropDownOpen    = mutableStateOf(false)
    var dropDownSelIndex = mutableStateOf(0)

}