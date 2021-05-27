package com.ui

import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import com.model.Model

@Composable
fun UI(model: Model) {

    with(model) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                publish()
            }
        )
    }
}