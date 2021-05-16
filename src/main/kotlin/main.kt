import androidx.compose.desktop.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.IntSize
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

        val model = remember { PersonModel() }
        Form().of(model)
    }
}

