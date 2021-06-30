package ch.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ch.model.Model



@Composable
fun DecisionContent(model: Model){
    with(model){
        Column(modifier = Modifier.fillMaxSize().padding(top = 0.dp, bottom = 12.dp)) {
            //Switch
            Row(modifier = Modifier.fillMaxSize().padding(bottom = 12.dp, top = 12.dp), verticalAlignment = Alignment.Top){
                Column(modifier = Modifier.fillMaxHeight().padding(0.dp ,12.dp, 0.dp, 48.dp), verticalArrangement = Arrangement.Center) {
                    Row(modifier = Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween){
                        Text(decisionSel1, fontWeight = if(!decision1IsSelected.value) FontWeight.Bold else FontWeight.Normal)
                        Switch(
                            decision1IsSelected.value,
                            onCheckedChange = { decision1IsSelected.value = !decision1IsSelected.value})
                        Text(decisionSel2, fontWeight = if(decision1IsSelected.value) FontWeight.Bold else FontWeight.Normal)
                    }
                }
            }
        }
    }
}

val decisionSel1 = "Male"
val decisionSel2 = "Female"

val decision1IsSelected = mutableStateOf(true)