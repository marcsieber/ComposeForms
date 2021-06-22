package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.FormModel
import model.util.Utilities
import model.util.attribute.Attribute
import model.util.attribute.SelectionAttribute
import ui.theme.ColorsUtil.Companion.get
import ui.theme.DropdownColors
import ui.theme.FormColors


@Composable
fun InputField(model: FormModel, attr: Attribute<*, *, *>, keyEvent: (KeyEvent) -> Boolean){

    val focusRequester = remember { FocusRequester() }

    val index = remember { model.addFocusRequester(focusRequester, attr) }
    val focused = model.getCurrentFocusIndex() == index
    val error = if(!focused) !attr.isValid() else !attr.isRightTrackValid() //TODO: focuses size not check throws an out of bounds when clicking on selection attribute on an element


    OutlinedTextField(
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp, 12.dp, 6.dp, 6.dp)
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
//                println(event.key)
                if(event.key == Key.Tab){
                    model.focusNext()
                    return@onKeyEvent false
                }
                keyEvent(event)
            }
            .shortcuts {
//                println("SHORTCUT" )
              on(Key.Tab){
//                  println("TAB PRESSED")
              }
            },
        value = attr.getValueAsText(),
        trailingIcon = {Text(attr.meaning.addMeaning(attr.getValueAsText()))},
        onValueChange = {attr.setValueAsText(it)},
        label = { Text( if(attr.isRequired()) attr.getLabel() + "*" else attr.getLabel()) },
        enabled = !attr.isReadOnly(),
        readOnly = attr.isReadOnly(),
        isError = error ,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = if(!attr.isValid()) get(FormColors.RIGHTTRACK) else get(FormColors.VALID),
            focusedLabelColor = if(!attr.isValid()) get(FormColors.RIGHTTRACK)  else get(FormColors.VALID),
            errorBorderColor = get(FormColors.ERROR),
            errorLabelColor = get(FormColors.ERROR),
            disabledBorderColor = Color.Transparent, //readonly
            disabledTextColor = Color.Black,         //readonly
            disabledLabelColor = Color.Black,        //readonly
            cursorColor = Color.Black
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

@Composable
fun SelectionField(model : FormModel, selectionAttribute : SelectionAttribute<*>){
    with(model){
        val dropDownIsOpen          = remember { mutableStateOf(false) }
        val selectionString         = mutableStateOf(selectionAttribute.getValueAsText().substring(1, selectionAttribute.getValueAsText().length-1))
        val label                   = selectionAttribute.getLabel()

        Row(modifier = Modifier.padding(6.dp, 12.dp, 6.dp, 6.dp)) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Column() {
                    Card(){
                        OutlinedButton(
                            modifier = Modifier.height(64.dp).fillMaxWidth().padding(top = 8.dp),
                            onClick = { dropDownIsOpen.value = true },
                            shape = RoundedCornerShape(8),
                            colors = ButtonDefaults.buttonColors(backgroundColor = DropdownColors.BUTTON_BACKGROUND.color),
                            border = BorderStroke(1.dp, if (selectionAttribute.isValid()) get(FormColors.RIGHTTRACK) else get(FormColors.ERROR))
                        ) {
                            if (selectionString.value.equals("")) {
                                Text(label, color = get(FormColors.LABEL))
                            } else {
                                Text(selectionString.value)
                            }
                        }
                        Row(modifier = Modifier.height(12.dp)){
                            if (!selectionString.value.equals("")) {
                                Text(
                                    label,
                                    color = get(FormColors.LABEL),
                                    modifier = Modifier.wrapContentSize().padding(4.dp),
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                    DropDownMenu(
                        dropDownIsOpen, selectionAttribute.getPossibleSelections(), Utilities<Set<String>>().stringToSetConverter(selectionAttribute.getValueAsText()),
                        selectionAttribute::addUserSelection, selectionAttribute::removeUserSelection
                    )

                }

            }
        }
        Column {
            if (!selectionAttribute.isValid()) {
                for (msg in selectionAttribute.getErrorMessages()) {
                    Text(msg, color = get(FormColors.ERROR), fontSize = 12.sp, modifier = Modifier.padding(4.dp))
                }
            }
        }
    }
}

@Composable
fun DropDownMenu(dropDownIsOpen: MutableState<Boolean>, selections: Set<String>, currentSelectionValue: Set<String>,
                 add :(String)-> Unit, remove : (String) -> Unit) {

    DropdownMenu(
        expanded = dropDownIsOpen.value,
        onDismissRequest = { dropDownIsOpen.value = false},
        modifier = Modifier.wrapContentSize().fillMaxWidth(),
        content = {
            selections.forEachIndexed { index, string ->
                val elementIsSelected       = currentSelectionValue.contains(string)
                val elementIsSelectedBackgroundColor  = if(elementIsSelected) get(DropdownColors.BACKGROUND_ELEMENT_SEL) else get(DropdownColors.BACKGROUND_ELEMENT_NOT_SEL)
                val elementIsSelectedTextColor = if(elementIsSelected) get(DropdownColors.TEXT_ELEMENT_SEL) else get(DropdownColors.TEXT_ELEMENT_NOT_SEL)
                DropdownMenuItem(
                    modifier = Modifier.background(elementIsSelectedBackgroundColor),
                    onClick = {
                        if (!elementIsSelected) {
                            add(string)
                        } else { remove(string)}},
                    content = {Text( text = string, modifier = Modifier.background(elementIsSelectedBackgroundColor), color = elementIsSelectedTextColor)}
                )
            }
        }
    )
}
