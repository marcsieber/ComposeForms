
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import demo.UserDefinedModel
import ui.Form

@Composable
fun DemoUI(model: UserDefinedModel) {

    Form().of(model)

}