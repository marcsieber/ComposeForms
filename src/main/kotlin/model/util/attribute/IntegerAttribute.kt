import androidx.compose.runtime.mutableStateOf

class IntegerAttribute(value : Int){
    private var value = mutableStateOf(value)

    private var valueAsText = mutableStateOf(value.toString())
    private var label = mutableStateOf("")
    private var required = mutableStateOf(false)



    fun setValue(value : String){
        valueAsText.value = value
    }
    fun getValAsText(): String {
        return valueAsText.value
    }

    fun setLabel(label : String): IntegerAttribute{
        this.label.value = label
        return this
    }
    fun getLabel() : String{
        if(required.value){
            return label.value + "*"
        }
        else{
            return label.value
        }
    }

    fun setRequired(isRequired : Boolean) : IntegerAttribute{
        this.required.value = isRequired
        return this
    }
    fun getRequired() : Boolean{
        return required.value
    }
}