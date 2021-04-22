package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import model.FormModel
import model.util.attribute.*


class Form {

    private var focusRequesters: MutableList<FocusRequester> = mutableListOf()

    @Composable
    fun of(model: FormModel){
        with(model) {
            Column() {
                Row() {
                    Text(getTitle())
                }

                Row() {
                    LazyColumn(Modifier.fillMaxHeight(0.7f)) {
                        items(getAttributes()) { attribute ->
                            AttributeElement(attribute)
                        }
                    }
                }

                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                        enabled = isChangedForAll(),
                        onClick = {
                            undoAll()
                        }) {
                        Text("Undo")
                    }

                    Button(
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

    }

    @Composable
    private fun AttributeElement(attr: Attribute<*,*>){

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
    private fun InputField(attr: Attribute<*, *>, keyEvent: (KeyEvent) -> Boolean){

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
    }

    @Composable
    private fun AttributeElement(strAttr: StringAttribute){
        InputField(strAttr){return@InputField true}
    }

    @Composable
    private fun AttributeElement(longAttr: LongAttribute){
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
    private fun AttributeElement(intAttr: IntegerAttribute){
        InputField(intAttr){ return@InputField true}
    }

    @Composable
    private fun AttributeElement(shortAttr: ShortAttribute){
        InputField(shortAttr){ return@InputField true}
    }

    @Composable
    private fun AttributeElement(floatAttr: FloatAttribute){
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
    private fun AttributeElement(doubleAttr: DoubleAttribute){
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

    @Composable fun AttributeElement(selectionAttribute: SelectionAttribute){ //todo
//        val dropDownItems  = remember {setOf<String>("1","2","3","4")}
        val dropDownOpen    = remember {mutableStateOf(false)}
//        val dropDownSelIndex = remember {mutableStateOf(0)}

        Box(
            modifier = Modifier.height(300.dp)
        ){
            Text(
                selectionAttribute.getValue()?.joinToString(", ") ?: "",
                modifier = Modifier.fillMaxWidth().clickable(onClick = {dropDownOpen.value = true}).background(
                    Color.LightGray
                ))
            DropdownMenu(
                expanded = dropDownOpen.value,
                onDismissRequest = { dropDownOpen.value = false},
                modifier = Modifier.fillMaxWidth().background(Color.LightGray),
                content = {
                    selectionAttribute.getPossibleSelections().forEachIndexed{ index, string ->
                        var isSelected = remember{ mutableStateOf(selectionAttribute.getValue()?.contains(string) == true)}
                        DropdownMenuItem(
                            onClick = {if(!isSelected.value){
                                selectionAttribute.addSelection(string)
                            }else{
                                selectionAttribute.removeSelection(string)
                            }}){
                            Text(modifier = Modifier.background(
                                if (isSelected.value) Color.DarkGray else Color.LightGray
                            ), text = string)
                        }
                    }
                }
            )
        }


    }
}