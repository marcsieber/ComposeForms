package ch.fhnw.forms.desktop.demo


import androidx.compose.runtime.Composable
import ch.fhnw.forms.desktop.ui.Form

@Composable
fun DemoUI(model: UserDefinedModel) {

    Form().of(model)

}