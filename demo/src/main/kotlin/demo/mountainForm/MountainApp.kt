package demo.mountainForm

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import demo.mountainForm.model.MountainPM
import demo.mountainForm.service.MountainService
import demo.mountainForm.service.serviceimpl.MountainServiceImpl
import ui.Form

object MountainApp {

    lateinit var model : MountainPM

    val service: MountainService = MountainServiceImpl()

    @ExperimentalFoundationApi
    @Composable
    fun createAppUI(){
        model = remember {MountainPM(service)}

        Column {
            Text("hallo")
            Form().of(model)
        }
    }


}