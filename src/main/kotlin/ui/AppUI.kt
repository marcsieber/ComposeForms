import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AppUI(model: AppModel){
    with(model){
        Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    increaseCount()
                }) {
                Text(if (count.value == 0) "Hello World" else "Clicked ${count.value}!")
            }
            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    resetCount()
                }) {
                Text("Reset")
            }
            Row {
                Column {
                    OutlinedTextField(
                        modifier = Modifier ,
                        value = stringValue.value,
                        onValueChange = {stringValue.value = it},
                        label = {Text("String: ")}
                    )
                    OutlinedTextField(
                        modifier = Modifier ,
                        value = longValue.value,
                        onValueChange = {longValue.value = it},
                        label = {Text("Long: ")}
                    )
                    OutlinedTextField(
                        modifier = Modifier,
                        value = intValue.getValAsText(),
                        onValueChange = {intValue.setValAsText(it)},
                        label = {Text(intValue.getLabel())},
                        readOnly = intValue.isReadOnly()
                    )
                    OutlinedTextField(
                        modifier = Modifier ,
                        value = doubleValue.value,
                        onValueChange = {doubleValue.value = it},
                        label = {Text("Double: ")}
                    )
                    OutlinedTextField(
                        modifier = Modifier ,
                        value = floatValue.value,
                        onValueChange = {floatValue.value = it},
                        label = {Text("Float: ")}
                    )
                }
                Column {
                    OutlinedTextField(
                        modifier = Modifier ,
                        value = dateValue.value,
                        onValueChange = {dateValue.value = it},
                        label = {Text("Date: ")}
                    )
                    Text("Boolean: ")
                    Switch(
                        booleanValue.value,
                        onCheckedChange = {booleanValue.value = it}
                    )

                    Text("Radio Buttons: ")
                    RadioButton(radioButtonValue.value,
                        onClick = {radioButtonValue.value = !radioButtonValue.value})

                    Text("Dropdown Chooser: ")
                    Box(
                        modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.TopStart)
                    ){
                        Text(dropDownItems[dropDownSelIndex.value],
                            modifier = Modifier.fillMaxWidth().clickable(onClick = {dropDownOpen.value = true}).background(
                                Color.LightGray
                            ))
                        DropdownMenu(
                            expanded = dropDownOpen.value,
                            onDismissRequest = { dropDownOpen.value = false},
                            modifier = Modifier.fillMaxWidth().background(Color.LightGray),
                            content = {
                                dropDownItems.forEachIndexed{ index, string ->
                                    DropdownMenuItem(
                                        onClick = {dropDownSelIndex.value = index}){
                                        Text(string)
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}