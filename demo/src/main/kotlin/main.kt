import androidx.compose.desktop.Window
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.IntSize
import demo.personForm.PersonModel
import demo.playGroundForm.DemoUI
import demo.playGroundForm.UserDefinedModel
import demo.userTesting.UserTestingApp
import demo.zwischenprasentation.DemoApp
import ui.Form


fun main() = Window(
    title = "Compose for Desktop",
    size = IntSize(600, 1000)
) {
    MaterialTheme {
        val model = remember { UserDefinedModel() }
        DemoUI(model)

//        val model = remember { PersonModel() }
//        Form().of(model)

//        DemoUI(DemoModel())

//        MountainApp.createAppUI()

//        DemoApp.createAppUI()

//        UserTestingApp().createAppUI()
    }
}

