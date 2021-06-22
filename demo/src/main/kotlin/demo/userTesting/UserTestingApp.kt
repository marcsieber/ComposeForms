package demo.userTesting

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import model.BaseFormModel
import ui.Form

class UserTestingApp {

    val testingModel = UserTestingFormModel()

    @ExperimentalFoundationApi
    @Composable
    fun createAppUI(){
        testingModel.setTitle("Formulare sind cool")
        Form().of(testingModel)

    }
}