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
import model.FormModel
import model.util.attribute.Attribute
import ui.theme.ColorsUtil.Companion.get
import ui.theme.FormColors


@Composable
fun InputField(model: FormModel, attr: Attribute<*, *, *>, keyEvent: (KeyEvent) -> Boolean){

    val focusRequester = remember { FocusRequester() }

    val index = remember { model.addFocusRequester(focusRequester) }
    val interactionSource = remember { MutableInteractionSource() }
    val focused = model.getCurrentFocusIndex() == index
    val error = if(!focused) !attr.isValid() else !attr.isRightTrackValid() //TODO: focuses size not check throws an out of bounds when clicking on selection attribute on an element

    OutlinedTextField(
        singleLine = true,
        modifier = Modifier
            .focusModifier().onFocusEvent { focS ->
                if(focused != focS.isFocused){
                    if(!focS.isFocused){
                        attr.checkAndSetConvertableBecauseUnfocussedAttribute() //setConvertables()
                    }
                }
                if(focS.isFocused){
                    model.setCurrentFocusIndex(index)
                }
            }
            .focusOrder(focusRequester)
            .onKeyEvent{event ->
                if(event.key == Key.Tab){
                    model.focusNext()
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