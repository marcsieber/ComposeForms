package demo.playGroundForm
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import ui.Form

@ExperimentalFoundationApi
@Composable
fun DemoUI(model: UserDefinedModel) {

    Form().of(model)

}