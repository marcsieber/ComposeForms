import androidx.compose.desktop.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.IntSize


fun main() = Window(
    title = "Compose for Desktop",
    size = IntSize(600, 460)
) {
    MaterialTheme {
        val model = remember { UserDefinedModel()}
        AppUI(model)
    }
}

