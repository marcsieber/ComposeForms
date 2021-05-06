
import androidx.compose.runtime.Composable
import demo.UserDefinedModel
import ui.Form

@Composable
fun DemoUI(model: UserDefinedModel) {

    Form().of(model)

}