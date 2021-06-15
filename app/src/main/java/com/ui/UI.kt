package com.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.model.Model
import com.model.Model.text
import communication.AttributeType
import communication.Command
import ui.theme.ColorsUtil
import ui.theme.FormColors
import ui.theme.ColorsUtil.Companion.get

@Composable
fun UI(model: Model) {
    with(model) {
        Scaffold (topBar = {Header(model)}, bottomBar = { BottomBar(model)}){
            Column (modifier = Modifier.padding(12.dp)){
                when(type){
                    AttributeType.SELECTION -> SelectionContent(model)
                    AttributeType.SHORT -> NumberContent(model)
                    AttributeType.LONG -> NumberContent(model)
                    AttributeType.INTEGER -> NumberContent(model)
                    AttributeType.FLOAT -> NumberContent(model)
                    AttributeType.DOUBLE -> NumberContent(model)
                }
            }
        }
    }
}

@Composable
fun Header(model: Model){
    with(model){
        Row(verticalAlignment = Alignment.Top, modifier = Modifier.background(get(FormColors.BACKGROUND_COLOR_LIGHT))){
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(12.dp)) {
                InputField(model, type)
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(vertical = 12.dp)){
                    Column(modifier = Modifier.widthIn(max = 220.dp)) {
                        for(msg in errorMessages.value){
                            Text(msg, color = get(FormColors.ERROR), fontSize = 14.sp)
                        }
                    }
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                        Text("Previous value", fontSize = 14.sp)
                        Text("Hier verbinden", fontSize = 14.sp)
                    }
                }
                Divider(color = get(FormColors.BACKGROUND_COLOR), thickness = 1.dp)
            }
        }
    }
}

@Composable
fun BottomBar(model: Model){
    with(model){
        BottomAppBar(backgroundColor = get(FormColors.BACKGROUND_COLOR), contentColor = get(FormColors.FONT_ON_BACKGOUND)) {
            Row(modifier = Modifier.fillMaxSize().padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                IconButton(onClick = {sendCommand(Command.PREVIOUS)}) {
                    Icon(Icons.Filled.ArrowBack, "Back") }

                Text("undo", modifier = Modifier.clickable {  }, textAlign = TextAlign.Center, fontSize = 16.sp)

                IconButton(onClick = {sendCommand(Command.NEXT)}) {
                    Icon(Icons.Filled.ArrowForward, "Forward")}
            }
        }
    }
}


@Composable
fun InputField(model : Model, attrType: AttributeType){
    with(model) {
        OutlinedTextField(
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            value = showTextForType(text, attrType),
            onValueChange = {
                text = it
                publish()
            },
            label = { Text(label) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor  = if(isValid) get(FormColors.VALID) else if(isOnRightTrack) get(FormColors.RIGHTTRACK) else get(FormColors.ERROR),
                unfocusedLabelColor   = if(isValid) get(FormColors.VALID) else if(isOnRightTrack) get(FormColors.RIGHTTRACK) else get(FormColors.ERROR),
                focusedBorderColor    = if(isValid) get(FormColors.VALID) else if(isOnRightTrack) get(FormColors.RIGHTTRACK) else get(FormColors.ERROR),
                focusedLabelColor     = if(isValid) get(FormColors.VALID) else if(isOnRightTrack) get(FormColors.RIGHTTRACK) else get(FormColors.ERROR),
                cursorColor           = Color.Black,
                errorBorderColor      = get(FormColors.ERROR),
                errorLabelColor       = get(FormColors.ERROR)
            ),
            readOnly = true
        )
    }
}


fun showTextForType(text : String, type: AttributeType) : String {
    return when(type){
        AttributeType.SELECTION -> text.substring(1, text.length-1)
        else                    -> text
    }
}
