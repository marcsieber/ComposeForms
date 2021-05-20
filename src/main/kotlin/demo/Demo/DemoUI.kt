package demo.Demo

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DemoUI(model : DemoModel) {
    with(model){
        Row(){
            Column(modifier = Modifier.defaultMinSize(100.dp).padding(2.dp)) {
                TextField(
                    value = name,
                    onValueChange = {name = it},
                    label = { Text(nameCaption)}
                )
            }
            Column(modifier = Modifier.defaultMinSize(100.dp).padding(2.dp)) {
                TextField(
                    value = height,
                    onValueChange = {height = it},
                    label = { Text(heightCaption)}
                )
            }
        }
    }
}