package com.myapplication

import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*

@Composable
fun App() {
    var text by remember { mutableStateOf("Hello, its running!") }

    MaterialTheme {
        Button(onClick = {
            text = "Hello, changed"
        }) {
            Text(text)
        }
    }
}