package ch.fhnw.forms.desktop

import androidx.compose.desktop.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.IntSize
import ch.fhnw.forms.desktop.demo.DemoUI
import ch.fhnw.forms.desktop.demo.UserDefinedModel


fun main() = Window(
    title = "Compose for Desktop",
    size = IntSize(600, 800)
) {
    MaterialTheme {
        val model = remember { UserDefinedModel() }
        DemoUI(model)
    }
}

