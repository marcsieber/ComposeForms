import androidx.compose.desktop.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.IntSize


fun main() = Window(
    title = "Compose for Desktop",
    size = IntSize(300, 300)
) {
    MaterialTheme {
        val model = remember { AppModel()}
        AppUI(model)
    }
}

