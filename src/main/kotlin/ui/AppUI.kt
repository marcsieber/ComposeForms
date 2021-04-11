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
import demo.UserDefinedModel
import java.util.*

@Composable
fun AppUI(model: UserDefinedModel) {
    with(model){
        Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {

            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(backgroundColor =  Color.Green),
                enabled = isValidForAll() && isChangedForAll(),
                onClick = {
                    saveAll()
                }){
                Text("Save")
            }
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(backgroundColor =  Color.Yellow),
                enabled = isChangedForAll(),
                onClick = {
                    undoAll()
                }){
                Text("Undo")
            }
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(backgroundColor =  Color.Magenta),
                enabled = !isCurrentLanguageForAll(Locale.GERMAN),
                onClick = {
                    setCurrentLanguageForAll(Locale.GERMAN)
                }){
                Text("Deutsch")
            }
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(backgroundColor =  Color.Magenta),
                enabled = !isCurrentLanguageForAll(Locale.ENGLISH),
                onClick = {
                    setCurrentLanguageForAll(Locale.ENGLISH)
                }){
                Text("Englisch")
            }

            Row {
                Column {

                    OutlinedTextField(          //stringValue
                        modifier = Modifier ,
                        value = stringValue.getValueAsText(),
                        onValueChange = {stringValue.setValueAsText(it)},
                        label = {Text(stringValue.getLabel())} ,
                        readOnly = stringValue.isReadOnly(),
                        isError = !stringValue.isValid()
                    )

                    OutlinedTextField(          //intValue1
                        modifier = Modifier.onKeyEvent{event ->
                            if (event.key == Key.DirectionUp) {
                                intValue1.setValueAsText( (intValue1.getValue() + intValue1.getStepSize()).toString())
                            }
                            if(event.key == Key.DirectionDown){
                                intValue1.setValueAsText( (intValue1.getValue() - intValue1.getStepSize()).toString())
                            }
                            return@onKeyEvent true},
                        value = intValue1.getValueAsText(),
                        onValueChange = {intValue1.setValueAsText(it)},
                        label = {Text(intValue1.getLabel())},
                        readOnly = intValue1.isReadOnly(),
                        isError = !intValue1.isValid()
                    )


                    OutlinedTextField(          //intValue2
                        modifier = Modifier.onKeyEvent{event ->
                            if (event.key == Key.DirectionUp) {
                                intValue2.setValueAsText( (intValue2.getValue() + intValue2.getStepSize()).toString())
                            }
                            if(event.key == Key.DirectionDown){
                                intValue2.setValueAsText( (intValue2.getValue() - intValue2.getStepSize()).toString())
                            }
                            return@onKeyEvent true},
                        value = intValue2.getValueAsText(),
                        onValueChange = {intValue2.setValueAsText(it)},
                        label = {Text(intValue2.getLabel())},
                        readOnly = intValue2.isReadOnly(),
                        isError = !intValue2.isValid()
                    )

                    OutlinedTextField(          //shortValue
                        modifier = Modifier.onKeyEvent{event ->
                            if (event.key == Key.DirectionUp) {
                                shortValue.setValueAsText( (shortValue.getValue() + shortValue.getStepSize()).toString())
                            }
                            if(event.key == Key.DirectionDown){
                                shortValue.setValueAsText( (shortValue.getValue() - shortValue.getStepSize()).toString())
                            }
                            return@onKeyEvent true},
                        value = shortValue.getValueAsText(),
                        onValueChange = {shortValue.setValueAsText(it)},
                        label = {Text(shortValue.getLabel())},
                        readOnly = shortValue.isReadOnly(),
                        isError = !shortValue.isValid()
                    )

                    OutlinedTextField(          //longValue
                        modifier = Modifier.onKeyEvent{event ->
                            if (event.key == Key.DirectionUp) {
                                longValue.setValueAsText( (longValue.getValue() + longValue.getStepSize()).toString())
                            }
                            if(event.key == Key.DirectionDown){
                                longValue.setValueAsText( (longValue.getValue() - longValue.getStepSize()).toString())
                            }
                            return@onKeyEvent true},
                        value = longValue.getValueAsText(),
                        onValueChange = {longValue.setValueAsText(it)},
                        label = {Text(longValue.getLabel())},
                        readOnly = longValue.isReadOnly(),
                        isError = !longValue.isValid()
                    )

                    OutlinedTextField(          //doubleValue
                        modifier = Modifier.onKeyEvent{event ->
                            if (event.key == Key.DirectionUp) {
                                doubleValue.setValueAsText( doubleValue.convertComma(String.format("%.8f",doubleValue.getValue() + doubleValue.getStepSize())))
                            }
                            if(event.key == Key.DirectionDown){
                                doubleValue.setValueAsText( doubleValue.convertComma(String.format("%.8f",(doubleValue.getValue() - doubleValue.getStepSize()))))
                            }
                            return@onKeyEvent true},
                        value = doubleValue.getValueAsText(),
                        onValueChange = {doubleValue.setValueAsText(it)},
                        label = {Text(doubleValue.getLabel())},
                        readOnly = doubleValue.isReadOnly(),
                        isError = !doubleValue.isValid()
                    )

                    OutlinedTextField(          //floatValue
                        modifier = Modifier.onKeyEvent{event ->
                            if (event.key == Key.DirectionUp) {
                                floatValue.setValueAsText( floatValue.convertComma(String.format("%.8f",floatValue.getValue() + floatValue.getStepSize())))
                            }
                            if(event.key == Key.DirectionDown){
                                floatValue.setValueAsText( floatValue.convertComma(String.format("%.8f",(floatValue.getValue() - floatValue.getStepSize()))))
                            }
                            return@onKeyEvent true},
                        value = floatValue.getValueAsText(),
                        onValueChange = {floatValue.setValueAsText(it)},
                        label = {Text(floatValue.getLabel())},
                        readOnly = floatValue.isReadOnly(),
                        isError = !floatValue.isValid()
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