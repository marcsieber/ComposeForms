package com.myapplication

import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import model.util.attribute.StringAttribute

@Composable
fun App() {
    var text by remember { mutableStateOf("Hello, its running again!") }


    MaterialTheme {
        Button(onClick = {
            text = "Hello, changed"
        }) {
            Text(text)
        }
    }
}