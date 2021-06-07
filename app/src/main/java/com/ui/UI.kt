package com.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.model.Model
import communication.AttributeType
import communication.Command
import ui.theme.FormColors
import ui.theme.ColorsUtil.Companion.get

@Composable
fun UI(model: Model) {
    with(model) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {
            Column (modifier = Modifier.padding(12.dp)){
                Header(model)
            }
            BottomBar(model)
        }
    }
}

@Composable
fun Header(model: Model){
    with(model){
        Row(verticalAlignment = Alignment.Top){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                InputField(model, type)
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(vertical = 12.dp)){
                    Column(modifier = Modifier.widthIn(max = 220.dp)) {
                        for(msg in errorMessages.value){
                            Text(msg, color = get(FormColors.ERROR), fontSize = 16.sp)
                        }
                    }
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                        Text("Previous value", fontSize = 16.sp)
                        Text("Hier verbinden", fontSize = 16.sp)
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
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = {
                text = it
                publish()
            },
            label = { Text(label) }
        )
    }
}
