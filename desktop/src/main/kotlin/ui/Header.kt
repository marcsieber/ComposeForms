package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.FormModel
import ui.theme.ColorsUtil.Companion.get
import ui.theme.DropdownColors
import ui.theme.FormColors

@Composable
fun header(model : FormModel){
    with(model){
        TopAppBar(
            backgroundColor = get(FormColors.HEADER_BACKGROUND),
            elevation = 100.dp
        ){
            Row(modifier = androidx.compose.ui.Modifier.fillMaxWidth(), horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween){
                Text(getTitle(), color = get(FormColors.HEADER_FONT), fontSize = 22.sp, modifier = androidx.compose.ui.Modifier.align(
                    androidx.compose.ui.Alignment.CenterVertically))

                Row(){
                    Column() {
                        val langDropDownIsOpen = remember { mutableStateOf(false) }
                        OutlinedButton(
                            modifier = androidx.compose.ui.Modifier.padding(4.dp),
                            onClick = { langDropDownIsOpen.value = !langDropDownIsOpen.value },
                            shape = RoundedCornerShape(12),
                            colors = ButtonDefaults.buttonColors(backgroundColor = get(DropdownColors.BUTTON_BACKGROUND)),
                            border = BorderStroke(1.dp, Color.White),
                        ) {
                            Text(getCurrentLanguage(), color = Color.White)
                        }
                        DropdownMenu(
                            expanded = langDropDownIsOpen.value,
                            onDismissRequest = { langDropDownIsOpen.value = false },
                            modifier = androidx.compose.ui.Modifier.wrapContentSize(),
                            content = {
                                getPossibleLanguages().forEachIndexed { index, string ->
                                    val elementIsSelected = isCurrentLanguageForAll(string)
                                    val elementIsSelectedBackgroundColor =
                                        if (elementIsSelected) get(DropdownColors.BACKGROUND_ELEMENT_SEL) else get(DropdownColors.BACKGROUND_ELEMENT_NOT_SEL)
                                    val elementIsSelectedTextColor =
                                        if (elementIsSelected) get(DropdownColors.TEXT_ELEMENT_SEL) else get(DropdownColors.TEXT_ELEMENT_NOT_SEL)
                                    DropdownMenuItem(
                                        modifier = androidx.compose.ui.Modifier.background(elementIsSelectedBackgroundColor),
                                        onClick = {
                                            setCurrentLanguageForAll(string)
                                        },
                                        content = {
                                            Text(
                                                text = string,
                                                modifier = androidx.compose.ui.Modifier.background(elementIsSelectedBackgroundColor),
                                                color = elementIsSelectedTextColor
                                            )
                                        }
                                    )
                                }
                            }
                        )

                    }
                    Button(
                        modifier = androidx.compose.ui.Modifier.padding(4.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = get(FormColors.VALID)),
                        enabled = isChangedForAll(),
                        onClick = {
                            undoAll()
                        }) {
                        Text("Undo", color = if(isChangedForAll()) Color.White else Color.Gray)
                    }
                    Button(
                        modifier = androidx.compose.ui.Modifier.padding(4.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = get(FormColors.VALID)),
                        enabled = isValidForAll() && isChangedForAll(),
                        onClick = {
                            saveAll()
                        }) {
                        Text("Save", color = if(isValidForAll() && isChangedForAll()) Color.White else Color.Gray)
                    }
                }
            }
        }
    }
}

