package com.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.model.Model
import communication.Command

@Composable
fun UI(model: Model) {

    with(model) {
        Column {
            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                    publish()
                }
            )

            Row{
               Button(onClick = { sendCommand(Command.PREVIOUS)}){
                   Text("PREV")
               }
                Button(onClick = { sendCommand(Command.NEXT)}){
                    Text("NEXT")
                }
            }
        }
    }
}