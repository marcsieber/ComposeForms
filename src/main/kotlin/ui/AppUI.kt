import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun AppUI(model: AppModel) {
    with(model){
        Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {

            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(backgroundColor =  Color.Green),
                enabled = intValue.isValid() && intValue.isChanged(),
                onClick = {
                    intValue.save()
                }){
                Text("Save")
            }
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(backgroundColor =  Color.Yellow),
                enabled = intValue.isChanged(),
                onClick = {
                    intValue.undo()
                }){
                Text("Undo")
            }
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(backgroundColor =  Color.Magenta),
                enabled = !intValue.isCurrentLanguage(Locale.GERMAN),
                onClick = {
                    intValue.setCurrentLanguage(Locale.GERMAN)
                }){
                Text("Deutsch")
            }
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(backgroundColor =  Color.Magenta),
                enabled = !intValue.isCurrentLanguage(Locale.ENGLISH),
                onClick = {
                    intValue.setCurrentLanguage(Locale.ENGLISH)
                }){
                Text("Englisch")
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
                        modifier = Modifier.onKeyEvent{event ->
                            if (event.key == Key.DirectionUp) {
                                intValue.setValAsText( (intValue.getValue() + intValue.getStepSize()!!).toString())
                            }
                            if(event.key == Key.DirectionDown){
                                intValue.setValAsText( (intValue.getValue() - intValue.getStepSize()!!).toString())
                            }
                            return@onKeyEvent true},
                        value = intValue.getValAsText(),
                        onValueChange = {intValue.setValAsText(it)},
                        label = {Text(intValue.getLabel())},
                        readOnly = intValue.isReadOnly(),
                        isError = !intValue.isValid()
                    )

                    OutlinedTextField(
                        modifier = Modifier ,
                        value = doubleValue.value,
                        onValueChange = {doubleValue.value = it},
                        label = {Text("Double: ")}
                    )
                }
                Column {
                    OutlinedTextField(
                        modifier = Modifier ,
                        value = floatValue.value,
                        onValueChange = {floatValue.value = it},
                        label = {Text("Float: ")}
                    )
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