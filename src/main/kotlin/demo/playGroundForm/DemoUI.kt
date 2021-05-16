package demo.playGroundForm
import androidx.compose.runtime.Composable
import ui.Form

@Composable
fun DemoUI(model: UserDefinedModel) {

    Form().of(model)

}