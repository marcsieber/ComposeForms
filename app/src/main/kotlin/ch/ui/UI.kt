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

package ch.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ch.model.Model
import communication.AttributeType
import communication.Command
import ui.theme.ColorsUtil
import ui.theme.FormColors
import ui.theme.ColorsUtil.Companion.get

/**
 * @author Louisa Reinger
 * @author Steve Vogel
 */

@ExperimentalFoundationApi
@Composable
fun UI(model: Model) {

    with(model) {
        Scaffold (topBar = { Header(model) }, bottomBar = { BottomBar(model) }){
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
        Row(verticalAlignment = Alignment.Top, modifier = Modifier.background(get(FormColors.BACKGROUND_COLOR_LIGHT)).shadow(2.dp)){
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(12.dp)) {
                InputField(model, type)
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.padding(vertical = 12.dp)){
                    Column(modifier = Modifier.widthIn(max = 220.dp)) {
                        if(!isOnRightTrack) {
                            for (msg in errorMessages.value) {
                                Text(msg, color = get(FormColors.ERROR), fontSize = 14.sp)
                            }
                        }
                    }
                    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                        Text("Previous value", fontSize = 14.sp)
                        Text("Hier verbinden", fontSize = 14.sp)
                    }
                }
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
        val color = if(isValid) get(FormColors.VALID) else if(isOnRightTrack) get(FormColors.RIGHTTRACK) else get(FormColors.ERROR)
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
                unfocusedBorderColor  = color,
                unfocusedLabelColor   = color,
                focusedBorderColor    = color,
                focusedLabelColor     = color,
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
        AttributeType.SELECTION -> if(text.equals("[]")){""}else if(text.isNotEmpty()) text.substring(1,  text.length-1) else ""
        else                    -> text
    }
}
