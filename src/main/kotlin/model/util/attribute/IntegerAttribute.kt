import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class IntegerAttribute(value : Int){
    private var value = mutableStateOf(value)

    private var valueAsText = mutableStateOf(value.toString());
    private var label = mutableStateOf("")

    fun setLabel(label : String): IntegerAttribute{
        this.label.value = label
        return this
    }

    fun getLabel() : String{
        return label.value
    }

    fun setValue(value : String){
        valueAsText.value = value
    }

    fun getValAsText(): String {
        return valueAsText.value
    }
}