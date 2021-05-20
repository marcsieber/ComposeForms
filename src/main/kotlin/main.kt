import androidx.compose.desktop.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.IntSize
import demo.Demo.DemoModel
import demo.Demo.DemoUI
import demo.personForm.PersonModel
import ui.Form


fun main() = Window(
    title = "Compose for Desktop",
    size = IntSize(600, 1000)
) {
    MaterialTheme {
//        val model = remember { UserDefinedModel() }
//        demo.playGroundForm.DemoUI(model)

//        MountainApp.createAppUI()

//        DemoUI(DemoModel())

        val model = remember { PersonModel() }
        Form().of(model)
    }
}

