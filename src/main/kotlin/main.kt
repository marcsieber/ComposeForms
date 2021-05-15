import androidx.compose.desktop.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.IntSize
import demo.UserDefinedModel
import demo.mountainForm.MountainApp


fun main() = Window(
    title = "Compose for Desktop",
    size = IntSize(600, 1000)
) {
    MaterialTheme {
//        val model = remember { UserDefinedModel() }
//        DemoUI(model)

        MountainApp.createAppUI()
    }
}

