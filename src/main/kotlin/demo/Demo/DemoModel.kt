package demo.Demo

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

class DemoModel {
    var name            by mutableStateOf("")
    var height          by mutableStateOf("")

    var nameCaption     by mutableStateOf("Name")
    var heightCaption   by mutableStateOf("Höhe (m)")
}