import androidx.compose.runtime.mutableStateOf

class IntegerAttribute(value : Int){
    private var value = mutableStateOf(value)

    private var valueAsText = mutableStateOf(value.toString())
    private var label       = mutableStateOf("")
    private var required    = mutableStateOf(false)
    private var readOnly    = mutableStateOf(false)




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
        if(isRequired()){
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
    fun isRequired() : Boolean{
        return required.value
    }

    fun setReadOnly(isReadOnly : Boolean) : IntegerAttribute{
        this.readOnly.value = isReadOnly
        return this
    }
    fun isReadOnly() : Boolean{
        return readOnly.value
    }
}