import androidx.compose.desktop.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.unit.IntSize
import demo.zwischenprasentation.DemoApp


fun main() = Window(
    title = "Compose for Desktop",
    size = IntSize(600, 1000)
) {
    MaterialTheme {
//        val model = remember { UserDefinedModel() }
//        DemoUI(model)

//        MountainApp.createAppUI()

        DemoApp.createAppUI()
    }
}

