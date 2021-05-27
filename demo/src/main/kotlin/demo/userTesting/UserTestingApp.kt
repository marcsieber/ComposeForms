package demo.userTesting

import androidx.compose.runtime.Composable
import model.BaseFormModel
import ui.Form

class UserTestingApp {

    val testingModel = UserTestingFormModel()

    @Composable
    fun createAppUI(){
        testingModel.setTitle("Formulare sind cool")
        Form().of(testingModel)

    }
}