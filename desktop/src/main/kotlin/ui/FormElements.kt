/*
 *
 *   ========================LICENSE_START=================================
 *   Compose Forms
 *   %%
 *   Copyright (C) 2021 FHNW Technik
 *   %%
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *   =========================LICENSE_END==================================
 *
 */

package ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDownCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.IModel
import model.meanings.Default
import util.Utilities
import model.util.attribute.Attribute
import model.util.attribute.SelectionAttribute
import ui.theme.ColorsUtil
import ui.theme.ColorsUtil.Companion.get
import ui.theme.DropdownColors
import ui.theme.FormColors

/**
 * @author Louisa Reinger
 * @author Steve Vogel
 */

@Composable
fun GroupTitle(title : String){
    Row(modifier = Modifier.fillMaxWidth().padding(start = 6.dp, end = 6.dp, top = 12.dp)) {
        Card(
            modifier = Modifier.fillMaxWidth().height(38.dp).border(0.dp, Color.Transparent, RoundedCornerShape(10.dp)),
            backgroundColor = get(FormColors.BACKGROUND_COLOR_GROUPS)){
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically){
                Text(title, color = get(FormColors.FONT_ON_BACKGOUND))
            }
        }
    }
}

@Composable
fun InputField(model: IModel, attr: Attribute<*, *, *>, keyEvent: (KeyEvent) -> Boolean){

    val focusRequester = remember { FocusRequester() }

    val index = remember { model.addFocusRequester(focusRequester, attr) }
    val focused = model.getCurrentFocusIndex() == index
    val firstTimeUnfocused = remember { mutableStateOf(true) }

    if(firstTimeUnfocused.value && focused){
        firstTimeUnfocused.value = false
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(6.dp, 15.dp, 6.dp, 15.dp)
            .focusModifier()
            .onFocusEvent { focS ->
                if(focused != focS.isFocused){
                    if(!focS.isFocused){
                        attr.checkAndSetConvertibleBecauseUnfocusedAttribute()
                        model.setCurrentFocusIndex(null)
                    }
                }
                if(focS.isFocused){
                    model.setCurrentFocusIndex(index)
                }}
            .focusOrder(focusRequester)
            .onKeyEvent{event ->
                if(event.key == Key.Tab){
                    model.focusNext()
                    return@onKeyEvent false
                }
                keyEvent(event)
            }
            .shortcuts {
                on(Key.Tab){
                }
            }){

        //Variables
        val focusedColor by mutableStateOf(if(attr.isValid()) get(FormColors.VALID) else if(attr.isRightTrackValid()) get(FormColors.RIGHTTRACK) else get(FormColors.ERROR))
        val unfocusedColor by mutableStateOf(if(attr.isValid() || firstTimeUnfocused.value) get(FormColors.RIGHTTRACK) else {get(FormColors.ERROR)})

        val color = if(focused) focusedColor else unfocusedColor

        //Assemble UI
        Label(model, attr, color, focused)
        InputElement(model, attr, color, focused, index)
        ErrorMessage(model, attr, focused, firstTimeUnfocused)
    }
}


//*********************************************
//Internal functions


/**
 * Label for an input-field
 */
@Composable
private fun Label(model: IModel, attr: Attribute<*, *, *>, color: Color, focused : Boolean) {
    with(model) {
        Row(modifier = Modifier.height(24.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                if (attr.isRequired()) attr.getLabel() + "*" else attr.getLabel(),
                fontSize = 12.sp,
                modifier = Modifier.padding(bottom = 12.dp),
                color = color,
                fontWeight = if (focused) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

/**
 * Input-Element for an input-field. Here the user can make an input
 */
@Composable
private fun InputElement(model: IModel, attr: Attribute<*, *, *>, color: Color, focused: Boolean, index : Int){
    with(model){

        //variables for selectionField
        val isSelectionField : Boolean = attr is SelectionAttribute
        var dropDownIsOpen : MutableState<Boolean> = remember { mutableStateOf(false) }
        var selectionString : MutableState<String> = remember {mutableStateOf("")}
        if(isSelectionField) {
            selectionString = mutableStateOf(attr.getValueAsText().substring(1, attr.getValueAsText().length - 1))
        }

        Column {
//            BoxWithConstraints(contentAlignment = Alignment.CenterEnd) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)){

                //Field
                BasicTextField(
                    value = if(!isSelectionField) attr.getValueAsText() else selectionString.value,
                    onValueChange = {attr.setValueAsText(it)},
                    singleLine = true,
                    modifier = Modifier
                        .height(20.dp)
                        .fillMaxWidth(if(isSelectionField) 0.88f else if(attr.meaning !== Default<Any>()) 0.9f else 1f) //todo: Find better solution for Text field and icon side by side
                        .padding(start = 12.dp, end = 12.dp),
                    readOnly = attr.isReadOnly() || isSelectionField
                )

                //Trailing-Icon
                if(!isSelectionField && attr.meaning !== Default<Any>()){
                    Text(attr.meaning.addMeaning(attr.getValueAsText()),
                        textAlign = TextAlign.Right,
                        modifier = Modifier.padding(end = 12.dp),
                        color = color)
                }

                //Drop-Down-Icon for Selection-Field
                if(isSelectionField){
                    IconButton(
                        onClick = { dropDownIsOpen.value = true; model.setCurrentFocusIndex(index) },
                        modifier = Modifier.clip(CircleShape).size(20.dp)
                    ) {
                        Icon(Icons.Filled.ArrowDropDownCircle, "DropDown", tint = color)
                    }
                }
            }
            //DropDown-Menu
            if(isSelectionField){
                DropDownMenu(
                    dropDownIsOpen, attr.getPossibleSelections(), Utilities<Set<String>>().stringToSetConverter(attr.getValueAsText()),
                    (attr as SelectionAttribute)::addUserSelection, attr::removeUserSelection
                )
            }
        }
        //Line
        Row(modifier = Modifier.height(2.dp)){
            Divider(color = if(attr.isReadOnly()) Color.Transparent else color, thickness = if(focused) 2.dp else 1.dp)
        }
    }
}

@Composable
private fun ErrorMessage(model: IModel, attr: Attribute<*, *, *>, focused: Boolean, firstTimeUnfocused : MutableState<Boolean>){
    with(model){

        var showErrorMsg by remember { mutableStateOf(false) }
        val error = if(!focused) {
            if(firstTimeUnfocused.value){
                false
            }else {
                !attr.isValid()
            }
        } else {
            !attr.isRightTrackValid()
        }

        //Error-Message
        Spacer(modifier = Modifier.height(2.dp))
        Row(modifier = Modifier.fillMaxWidth().height(20.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp, androidx.compose.ui.Alignment.End),
            verticalAlignment = Alignment.CenterVertically){

            if (error && showErrorMsg) {
                Card(modifier = Modifier
                    .fillMaxWidth(0.9f)                                             //todo: Find better solution for Text field and icon side by side
                    .border(0.dp, get(FormColors.ERROR), RoundedCornerShape(50))
                    .height(20.dp),
                    backgroundColor = get(FormColors.ERROR)
                ) {
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
                        onClick = { showErrorMsg = !showErrorMsg },
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
private fun DropDownMenu(dropDownIsOpen: MutableState<Boolean>, selections: Set<String>, currentSelectionValue: Set<String>,
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
