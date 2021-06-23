package ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropDownCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.*
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
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

    val selectionField : Boolean = attr is SelectionAttribute
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
        var showErrorMsg by remember { mutableStateOf(false) }

        //for selectionField
        var dropDownIsOpen : MutableState<Boolean> = remember { mutableStateOf(false) }
        var selectionString : MutableState<String> = remember {mutableStateOf("")}
        if(selectionField) {
            selectionString = mutableStateOf(attr.getValueAsText().substring(1, attr.getValueAsText().length - 1))
        }


        //Label
        Row(modifier = Modifier.height(24.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
            Text( if(attr.isRequired()) attr.getLabel() + "*" else attr.getLabel(),
                fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 12.dp),
                color = if(focused) focusedColor else unfocusedColor,
                fontWeight = if(focused) FontWeight.Bold else FontWeight.Normal)
        }

        //Input
        Column {
//            BoxWithConstraints(contentAlignment = Alignment.CenterEnd) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)){

                    //Field
                    BasicTextField(
                        value = if(!selectionField) attr.getValueAsText() else selectionString.value,
                        onValueChange = {attr.setValueAsText(it)},
                        singleLine = true,
                        modifier = Modifier
                            .height(20.dp)
                            .fillMaxWidth(if(selectionField) 0.88f else if(attr.meaning !== Default<Any>()) 0.9f else 1f) //todo: Find better solution for Text field and icon side by side
                            .padding(start = 12.dp, end = 12.dp),
                        readOnly = attr.isReadOnly() || selectionField
                    )

                    //Trailing-Icon
                    if(!selectionField && attr.meaning !== Default<Any>()){
                        Text(attr.meaning.addMeaning(attr.getValueAsText()),
                            textAlign = TextAlign.Right,
                            modifier = Modifier.padding(end = 12.dp),
                            color = if(focused) focusedColor else unfocusedColor)
                    }

                    //Drop-Down-Icon for Selection-Field
                    if(selectionField){
                        IconButton(
                            onClick = { dropDownIsOpen.value = true; model.setCurrentFocusIndex(index) },
                            modifier = Modifier.clip(CircleShape).size(20.dp)
                        ) {
                            Icon(Icons.Filled.ArrowDropDownCircle, "DropDown", tint = if(focused) focusedColor else unfocusedColor)
                        }
                    }
            }
            //DropDown-Menu
            if(selectionField){
                DropDownMenu(
                    dropDownIsOpen, attr.getPossibleSelections(), Utilities<Set<String>>().stringToSetConverter(attr.getValueAsText()),
                    (attr as SelectionAttribute)::addUserSelection, attr::removeUserSelection
                )
            }
        }

        //Line
        Row(modifier = Modifier.height(2.dp)){
            Divider(color = if(attr.isReadOnly()) Color.Transparent else if(focused) focusedColor else unfocusedColor, thickness = if(focused) 2.dp else 1.dp)
        }

        //Error-Message
        Spacer(modifier = Modifier.height(2.dp))
        Row(modifier = Modifier.fillMaxWidth().height(20.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.End),
            verticalAlignment = Alignment.CenterVertically){

            if (error && showErrorMsg) {
                Card(modifier = Modifier
                    .fillMaxWidth(0.9f)                                             //todo: Find better solution for Text field and icon side by side
                    .border(0.dp,get(FormColors.ERROR), RoundedCornerShape(50))
                    .height(20.dp),
                backgroundColor = get(FormColors.ERROR)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        for (msg in attr.getErrorMessages()) {
                            Text(
                                msg, color = Color.White, fontSize = 9.sp, fontWeight = FontWeight.Light,
                                modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 1.dp)
                            )
                        }
                    }
                }
            }

            Row(modifier = Modifier.padding(end = 6.dp)){
                if (error) {
                    IconButton(
                        onClick = { showErrorMsg = !showErrorMsg
                                  println("new state: $showErrorMsg")},
                        modifier = Modifier.clip(CircleShape).size(20.dp)
                    ) {
                        Icon(Icons.Filled.Error, "Error", tint = get(FormColors.ERROR))
                    }
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
