import androidx.compose.desktop.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.IntSize
import demo.UserDefinedModel


fun main() = Window(
    title = "Compose for Desktop",
    size = IntSize(600, 800)
) {
    MaterialTheme {
        val model = remember { UserDefinedModel() }
        AppUI(model)
    }
}

