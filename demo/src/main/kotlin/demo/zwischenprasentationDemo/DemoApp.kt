package demo.zwischenprasentationDemo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import demo.zwischenprasentationDemo.model.DemoModel
import ui.Form


object DemoApp {

    var model : DemoModel = DemoModel()

    @ExperimentalFoundationApi
    @Composable
    fun createAppUI(){
        Form().of(model)
    }
}