package ui

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.util.attribute.Attribute
import ui.theme.ColorsUtil.Companion.get
import ui.theme.FormColors


private var focusRequesters: MutableList<FocusRequester> = mutableListOf()
private var focuses:         MutableList<MutableState<Boolean>> = mutableListOf()

@Composable
fun InputField(attr: Attribute<*, *, *>, keyEvent: (KeyEvent) -> Boolean){

    val focusRequester = remember { FocusRequester() }
    val index = remember{ focusRequesters.size }
    val focused = remember { mutableStateOf(false) }

    if(focusRequester !in focusRequesters) {
        focusRequesters.add(focusRequester)
        focuses.add(focused)
    }

    val interactionSource = remember { MutableInteractionSource() }
    //val focused = true //interactionSource.collectIsFocusedAsState()
    val error = if(focuses.size > index && !focuses[index].value) !attr.isValid() else !attr.isRightTrackValid() //TODO: focuses size not check throws an out of bounds when clicking on selection attribute on an element

    OutlinedTextField(
        modifier = Modifier
            .focusModifier().onFocusEvent { focS ->
                focuses[index].value = focS.isFocused
                if(!focS.isFocused){
                    attr.checkAndSetConvertableBecauseUnfocussedAttribute() //setConvertables()
                }
            }
            .focusOrder(focusRequester)
            .onKeyEvent{event ->
                if(event.key == Key.Tab){
                    focusRequesters[(index+1) % focusRequesters.size].requestFocus()
                    for(i in 0 until focusRequesters.size){
                        focuses[i].value = false
                    }
                    focuses[(index+1) % focusRequesters.size].value = true
                    return@onKeyEvent false
                }
                keyEvent(event)
            },
        value = attr.getValueAsText(),
        onValueChange = {attr.setValueAsText(it)},
        label = { Text( if(attr.isRequired()) attr.getLabel() + "*" else attr.getLabel()) },
        enabled = !attr.isReadOnly(),
        readOnly = attr.isReadOnly(),
        isError = error ,
        interactionSource = interactionSource,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = if(!attr.isValid()) Color.Gray else get(FormColors.VALID),
            focusedLabelColor = if(!attr.isValid()) Color.Gray else get(FormColors.VALID),
            disabledBorderColor = Color.Transparent, //readonly
            disabledTextColor = Color.Black,        //readonly
            disabledLabelColor = Color.Black        //readonly
        )
    )
    Column {
        if(error){
            for(msg in attr.getErrorMessages()){
                Text(msg, color = get(FormColors.ERROR), fontSize = 12.sp, modifier = Modifier.padding(4.dp) )
            }
        }
    }
}