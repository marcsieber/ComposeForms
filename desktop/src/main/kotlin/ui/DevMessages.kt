package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.theme.ColorsUtil
import ui.theme.FormColors

@Composable
fun NoGroupsMessage(){
    Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
        Box {
            Row(modifier = Modifier.wrapContentSize().background(ColorsUtil.get(FormColors.ERROR)),
                horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Text("You forgot to define groups.", color = Color.White, modifier = Modifier.padding(56.dp))
            }
        }
    }
}