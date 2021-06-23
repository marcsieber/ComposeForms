package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.FormModel
import model.meanings.Default
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

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(6.dp, 15.dp, 6.dp, 15.dp)
            .focusModifier()
            .onFocusEvent { focS ->
                if(focused != focS.isFocused){
                    if(!focS.isFocused){
                        attr.checkAndSetConvertableBecauseUnfocussedAttribute() //setConvertables() //todo unfocus when no inputfield is focused
                    }
                }
                if(focS.isFocused){
                    model.setCurrentFocusIndex(index)
                }}
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
            }){

        val focusedColor by mutableStateOf(if(attr.isValid()) get(FormColors.VALID) else if(attr.isRightTrackValid()) get(FormColors.RIGHTTRACK) else get(FormColors.ERROR))
        val unfocusedColor by mutableStateOf(if(attr.isValid()) get(FormColors.RIGHTTRACK) else {get(FormColors.ERROR)})


        //Error-Message
        Row(modifier = Modifier.height(20.dp).background(get(FormColors.ERROR)), verticalAlignment = Alignment.CenterVertically) {
            if (error) {
                for (msg in attr.getErrorMessages()) {
                    Text(msg, color = Color.White, fontSize = 12.sp, modifier = Modifier.padding(4.dp))
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))

        //Label
        Row(modifier = Modifier.height(24.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
            Text( if(attr.isRequired()) attr.getLabel() + "*" else attr.getLabel(),
                fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 12.dp),
                color = if(focused) focusedColor else unfocusedColor)
        }

        //Input
        Row {
            BoxWithConstraints(contentAlignment = Alignment.CenterEnd) {
                //Field
                BasicTextField(
                    value = attr.getValueAsText(),
                    onValueChange = {attr.setValueAsText(it)},
                    singleLine = true,
                    modifier = Modifier.height(20.dp).fillMaxWidth().padding(start = 12.dp)
                )

                //Trailing-Icon
                if(attr.meaning !== Default<Any>()){
                    Text(attr.meaning.addMeaning(attr.getValueAsText()),
                        textAlign = TextAlign.Right,
                        modifier = Modifier.padding(end = 12.dp),
                        color = if(focused) focusedColor else unfocusedColor)
                }
            }
        }


        //Line
        Divider(color = if(attr.isReadOnly()) Color.Transparent else if(focused) focusedColor else unfocusedColor, thickness = if(focused) 2.dp else 1.dp)
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
                Column {
                    Card {
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
