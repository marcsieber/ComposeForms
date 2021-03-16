import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class IntegerAttribute(value : Int){
    private var value = mutableStateOf(value)

    private var valueAsText = mutableStateOf(value.toString());
    private var label = mutableStateOf("")


    fun setValue(value : String){
        valueAsText.value = value;
    }

    fun getValAsText(): MutableState<String> {
        return valueAsText
    }
}