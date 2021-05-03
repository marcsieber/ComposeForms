package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.FormModel
import model.util.attribute.*


class Form {

    private var focusRequesters: MutableList<FocusRequester> = mutableListOf()

    @Composable
    fun of(model: FormModel){
        with(model) {

            Column() {
                TopAppBar(
                    backgroundColor = Color.LightGray,
                    elevation = 100.dp
                ){
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){

                        Text(getTitle())

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
                Row() {
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

        if(focusRequester !in focusRequesters) {
            focusRequesters.add(focusRequester)
        }

        OutlinedTextField(
            modifier = Modifier
                .focusModifier()
                .focusOrder(focusRequester)
                .onKeyEvent{event ->
                    if(event.key == Key.Tab){
                        focusRequesters[(index+1) % focusRequesters.size].requestFocus()
                        return@onKeyEvent false
                    }
                    keyEvent(event)
            },
            value = attr.getValueAsText(),
            onValueChange = {attr.setValueAsText(it)},
            label = {Text(attr.getLabel())},
            readOnly = attr.isReadOnly(),
            isError = !attr.isValid(),
        )
        Column {
            if(!attr.isValid()){
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
                if (it.key == Key.DirectionUp) {
                    longAttr.setValueAsTextFromKeyEvent( (longAttr.getValue() as Long + longAttr.getStepSize()).toString())
                }
                if(it.key == Key.DirectionDown){
                    longAttr.setValueAsTextFromKeyEvent( (longAttr.getValue() as Long - longAttr.getStepSize()).toString())
                }
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
            if (it.key == Key.DirectionUp) {
                floatAttr.setValueAsTextFromKeyEvent( (floatAttr.getValue() as Float + floatAttr.getStepSize()).toString())
            }
            if(it.key == Key.DirectionDown){
                floatAttr.setValueAsTextFromKeyEvent( (floatAttr.getValue() as Float - floatAttr.getStepSize()).toString())
            }
            return@InputField true}
    }

    @Composable
    private fun AttributeElement(doubleAttr: DoubleAttribute<*>){
        InputField(doubleAttr){
            if (it.key == Key.DirectionUp) {
                doubleAttr.setValueAsTextFromKeyEvent( (doubleAttr.getValue() as Double + doubleAttr.getStepSize()).toString())
            }
            if(it.key == Key.DirectionDown){
                doubleAttr.setValueAsTextFromKeyEvent( (doubleAttr.getValue() as Double - doubleAttr.getStepSize()).toString())
            }
            return@InputField true
        }
    }

    @Composable fun AttributeElement(selectionAttribute: SelectionAttribute<*>){ //todo: undo & save when dropdown is open
        val dropDownIsOpen          = remember {mutableStateOf(false)}
        val selectionString         = mutableStateOf(selectionAttribute.getValueAsText().substring(1, selectionAttribute.getValueAsText().length-1))
        val label                   = selectionAttribute.getLabel()

        Box( modifier = Modifier.height(300.dp).wrapContentSize(Alignment.TopStart)){
            Column {
                if(!selectionString.value.equals("")){
                    Text(label, color = Color.DarkGray, modifier = Modifier.wrapContentSize().padding(4.dp), fontSize = 12.sp)
                }
                OutlinedButton(
                    modifier = Modifier.height(50.dp),
                    onClick = {dropDownIsOpen.value = true},
                    shape = MaterialTheme.shapes.large,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    border = BorderStroke(1.dp, if(selectionAttribute.isValid()) Color.DarkGray else Color.Red)
                ){
                    if(selectionString.value.equals("")){
                        Text(label, color = Color.DarkGray)
                    }else{
                        Text(selectionString.value)
                    }
                }
                DropdownMenu(
                    expanded = dropDownIsOpen.value,
                    onDismissRequest = { dropDownIsOpen.value = false},
                    modifier = Modifier.wrapContentSize(),
                    content = {
                        selectionAttribute.getPossibleSelections().forEachIndexed { index, string ->
                            val elementIsSelected       = selectionAttribute.getValue()!!.contains(string)
                            val elementIsSelectedBackgroundColor  = if(elementIsSelected) Color.DarkGray else Color.LightGray
                            val elementIsSelectedTextColor = if(elementIsSelected) Color.White else Color.Black
                            DropdownMenuItem(
                                modifier = Modifier.background(elementIsSelectedBackgroundColor),
                                onClick = {
                                    if (!elementIsSelected) {
                                        selectionAttribute.addUserSelection(string)
                                    } else { selectionAttribute.removeUserSelection(string)}},
                                content = {Text( text = string, modifier = Modifier.background(elementIsSelectedBackgroundColor), color = elementIsSelectedTextColor)}
                            )
                        }
                    }
                )
            }

        }
    }
}