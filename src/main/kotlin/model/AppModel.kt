import androidx.compose.runtime.mutableStateOf

class AppModel(){
    var count = mutableStateOf(0)

    fun increaseCount(){
        count.value++
    }

    fun resetCount(){
        count.value = 0
    }
}