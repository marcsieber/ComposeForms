package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import model.util.Utilities
import model.util.attribute.*
import ui.theme.Colors


class Form {

    private var focusRequesters: MutableList<FocusRequester> = mutableListOf()
    private var focuses:         MutableList<MutableState<Boolean>> = mutableListOf()

    @Composable
    fun of(model: FormModel){
        with(model) {

            Column() {
                TopAppBar(
                    backgroundColor = Color.Blue,
                    elevation = 100.dp
                ){
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){

                        Column() {
                            Text(getTitle(), color = Color.White)

                            val langDropDownIsOpen = remember { mutableStateOf(false) }
                            OutlinedButton(
                                modifier = Modifier.height(50.dp),
                                onClick = { langDropDownIsOpen.value = !langDropDownIsOpen.value },
                                shape = MaterialTheme.shapes.large,
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                                border = BorderStroke(1.dp, Color.White)
                            ) {
                                Text(getCurrentLanguage(), color = Color.White)
                            }
                            DropdownMenu(
                                expanded = langDropDownIsOpen.value,
                                onDismissRequest = { langDropDownIsOpen.value = false },
                                modifier = Modifier.wrapContentSize(),
                                content = {
                                    getPossibleLanguages().forEachIndexed { index, string ->
                                        val elementIsSelected = isCurrentLanguageForAll(string)
                                        val elementIsSelectedBackgroundColor =
                                            if (elementIsSelected) Color.DarkGray else Color.LightGray
                                        val elementIsSelectedTextColor =
                                            if (elementIsSelected) Color.White else Color.Black
                                        DropdownMenuItem(
                                            modifier = Modifier.background(elementIsSelectedBackgroundColor),
                                            onClick = {
                                                setCurrentLanguageForAll(string)
                                            },
                                            content = {
                                                Text(
                                                    text = string,
                                                    modifier = Modifier.background(elementIsSelectedBackgroundColor),
                                                    color = elementIsSelectedTextColor
                                                )
                                            }
                                        )
                                    }
                                }
                            )

                        }

                        Row(){
                            Button(
                                modifier = Modifier.padding(4.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                                enabled = isChangedForAll(),
                                onClick = {
                                    undoAll()
                                }) {
                                Text("Undo")
                            }
                            Button(
                                modifier = Modifier.padding(4.dp),
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                                enabled = isValidForAll() && isChangedForAll(),
                                onClick = {
                                    saveAll()
                                }) {
                                Text("Save")
                            }
                        }
                    }
                }
                Row(modifier = Modifier.fillMaxSize().background(Color.White)) {
                    LazyColumn(Modifier.fillMaxHeight()) {
                        items(getAttributes()) { attribute ->
                            AttributeElement(attribute)
                        }
                    }
                }
            }
        }

    }

    @Composable
    private fun AttributeElement(attr: Attribute<*,*,*>){

        Row(Modifier.fillMaxWidth(0.8f).padding(5.dp)
            ) {

            Column(Modifier.fillMaxHeight()) {
                Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                    when (attr) {
                        is StringAttribute -> AttributeElement(attr)
                        is LongAttribute -> AttributeElement(attr)
                        is IntegerAttribute -> AttributeElement(attr)
                        is ShortAttribute -> AttributeElement(attr)
                        is DoubleAttribute -> AttributeElement(attr)
                        is FloatAttribute -> AttributeElement(attr)
                        is SelectionAttribute -> AttributeElement(attr)
                    }
                }

                Divider(Modifier.fillMaxWidth())
            }

        }
    }


    @Composable
    private fun InputField(attr: Attribute<*,*,*>, keyEvent: (KeyEvent) -> Boolean){

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
            label = {Text(attr.getLabel())},
            readOnly = attr.isReadOnly(),
            isError = error ,
            interactionSource = interactionSource,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = if(!attr.isValid()) Color.Gray else Colors.getColor("2e7d32"),
                focusedLabelColor = if(!attr.isValid()) Color.Gray else Color(0x2e, 0x7d, 0x32)
            )
        )
        Column {
            if(error){
                for(msg in attr.getErrorMessages()){
                    Text(msg, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(4.dp) )
                }
            }
        }
    }

    @Composable
    private fun AttributeElement(strAttr: StringAttribute<*>){
        InputField(strAttr){return@InputField true}
    }

    @Composable
    private fun AttributeElement(longAttr: LongAttribute<*>){
        InputField(longAttr)
            {
//                if (it.key == Key.DirectionUp) {
//                    longAttr.setValueAsTextFromKeyEvent( (longAttr.getValue() as Long + longAttr.validators.
//                    getStepSize()).toString())
//                }
//                if(it.key == Key.DirectionDown){
//                    longAttr.setValueAsTextFromKeyEvent( (longAttr.getValue() as Long - longAttr.getStepSize()).toString())
//                }
                return@InputField true
        }
    }

    @Composable
    private fun AttributeElement(intAttr: IntegerAttribute<*>){
        InputField(intAttr){ return@InputField true}
    }

    @Composable
    private fun AttributeElement(shortAttr: ShortAttribute<*>){
        InputField(shortAttr){ return@InputField true}
    }

    @Composable
    private fun AttributeElement(floatAttr: FloatAttribute<*>){
        InputField(floatAttr){
//            if (it.key == Key.DirectionUp) {
//                floatAttr.setValueAsTextFromKeyEvent( (floatAttr.getValue() as Float + floatAttr.getStepSize()).toString())
//            }
//            if(it.key == Key.DirectionDown){
//                floatAttr.setValueAsTextFromKeyEvent( (floatAttr.getValue() as Float - floatAttr.getStepSize()).toString())
//            }
            return@InputField true}
    }

    @Composable
    private fun AttributeElement(doubleAttr: DoubleAttribute<*>){
        InputField(doubleAttr){
//            if (it.key == Key.DirectionUp) {
//                doubleAttr.setValueAsTextFromKeyEvent( (doubleAttr.getValue() as Double + doubleAttr.getStepSize()).toString())
//            }
//            if(it.key == Key.DirectionDown){
//                doubleAttr.setValueAsTextFromKeyEvent( (doubleAttr.getValue() as Double - doubleAttr.getStepSize()).toString())
//            }
            return@InputField true
        }
    }

    @Composable fun AttributeElement(selectionAttribute: SelectionAttribute<*>){ //todo: undo & save when dropdown is open
        val dropDownIsOpen          = remember {mutableStateOf(false)}
        val selectionString         = mutableStateOf(selectionAttribute.getValueAsText().substring(1, selectionAttribute.getValueAsText().length-1))
        val label                   = selectionAttribute.getLabel()

        Row {
            Box(modifier = Modifier.height(300.dp).wrapContentSize(Alignment.TopStart)) {
                Column {
                    if (!selectionString.value.equals("")) {
                        Text(
                            label,
                            color = Color.DarkGray,
                            modifier = Modifier.wrapContentSize().padding(4.dp),
                            fontSize = 12.sp
                        )
                    }
                    OutlinedButton(
                        modifier = Modifier.height(50.dp),
                        onClick = { dropDownIsOpen.value = true },
                        shape = MaterialTheme.shapes.large,
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        border = BorderStroke(1.dp, if (selectionAttribute.isValid()) Color.DarkGray else Color.Red)
                    ) {
                        if (selectionString.value.equals("")) {
                            Text(label, color = Color.DarkGray)
                        } else {
                            Text(selectionString.value)
                        }
                    }
                    DropDownMenu(
                        dropDownIsOpen, selectionAttribute.getPossibleSelections(), Utilities<Set<String>>().stringToSetConverter(selectionAttribute.getValueAsText()),
                        selectionAttribute::addUserSelection, selectionAttribute::removeUserSelection
                    )

                }

            }
            Column {
                if (!selectionAttribute.isValid()) {
                    for (msg in selectionAttribute.getErrorMessages()) {
                        Text(msg, color = Color.Red, fontSize = 12.sp, modifier = Modifier.padding(4.dp))
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
            modifier = Modifier.wrapContentSize(),
            content = {
                selections.forEachIndexed { index, string ->
                    val elementIsSelected       = currentSelectionValue.contains(string)
                    val elementIsSelectedBackgroundColor  = if(elementIsSelected) Color.DarkGray else Color.LightGray
                    val elementIsSelectedTextColor = if(elementIsSelected) Color.White else Color.Black
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
}