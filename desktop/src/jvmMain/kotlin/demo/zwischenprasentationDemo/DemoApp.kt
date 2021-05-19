package demo.zwischenprasentationDemo

import androidx.compose.runtime.Composable
import demo.zwischenprasentationDemo.model.DemoModel
import ui.Form


object DemoApp {

    var model : DemoModel = DemoModel()

    @Composable
    fun createAppUI(){
        Form().of(model)
    }
}