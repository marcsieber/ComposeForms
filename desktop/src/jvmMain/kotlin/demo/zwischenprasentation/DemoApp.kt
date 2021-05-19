package demo.zwischenprasentation


import androidx.compose.runtime.Composable
import demo.zwischenprasentation.model.DemoModel
import ui.Form


object DemoApp {

    var model : DemoModel = DemoModel()

    @Composable
    fun createAppUI(){
        Form().of(model)
    }
}