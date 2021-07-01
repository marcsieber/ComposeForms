/*
 *
 *   ========================LICENSE_START=================================
 *   Compose Forms
 *   %%
 *   Copyright (C) 2021 FHNW Technik
 *   %%
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *   =========================LICENSE_END==================================
 *
 */

package ch.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ch.model.Model
import ch.model.Model.possibleSelections
import ch.model.Model.text
import util.Utilities
import ui.theme.ColorsUtil
import ui.theme.DropdownColors
import ui.theme.FormColors

@Composable
fun SelectionContent(model: Model) {
    with(model){
        Column(modifier = Modifier.fillMaxSize().padding(top = 0.dp, bottom = 12.dp)) {
            //Searchfield
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Top){
                OutlinedTextField(
                    searchString.value,
                    onValueChange = { searchString.value = it; filterPossibleSelections(it) },
                    label = {Icon(Icons.Filled.Search, "Search")},
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = ColorsUtil.get(FormColors.BACKGROUND_COLOR),
                        focusedLabelColor = ColorsUtil.get(FormColors.BACKGROUND_COLOR),
                        cursorColor = Color.Black
                    )
                )
            }

            //Possible Selections
            Row(modifier = Modifier.fillMaxSize().padding(bottom = 12.dp, top = 12.dp), verticalAlignment = Alignment.Top){
                LazyColumn(verticalArrangement = Arrangement.Top, modifier = Modifier.fillMaxHeight().padding(0.dp ,12.dp, 0.dp, 48.dp)) {
                    filterPossibleSelections(searchString.value)
                    items(filteredListOfPossibleSelections.value){
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxSize()
                            .requiredHeight(50.dp)
                            .clickable{ if(elementIsSelected(it)) removeUserSelection(it) else addUserSelection(it); publish() }
                            .background(if(elementIsSelected(it)) ColorsUtil.get(DropdownColors.BACKGROUND_ELEMENT_SEL) else ColorsUtil.get(
                                DropdownColors.BACKGROUND_ELEMENT_NOT_SEL)),
                            horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically){
                            Text(it,
                                fontWeight = if(elementIsSelected(it))FontWeight.Bold else FontWeight.Normal,
                                color = if(elementIsSelected(it)) ColorsUtil.get(DropdownColors.TEXT_ELEMENT_SEL) else ColorsUtil.get(DropdownColors.TEXT_ELEMENT_NOT_SEL)
                            )
                        }
                    }
                }
            }
        }
    }
}

val searchString                        = mutableStateOf("")
var filteredListOfPossibleSelections    = mutableStateOf(possibleSelections.toList())

fun filterPossibleSelections(searchString: String){
    val interimList = possibleSelections.filter{it.contains(searchString, true)}.toMutableStateList()
    filteredListOfPossibleSelections.value = interimList
}

fun addUserSelection(value: String){
    if(possibleSelections.contains(value)){
        var newSet = Utilities<Set<String>>().stringToSetConverter(text)
        newSet = newSet.toMutableSet()
        newSet.add(value)
        text = newSet.toString()
    }
}

fun removeUserSelection(value: String){
    val newSet : MutableSet<String> = Utilities<Set<String>>().stringToSetConverter(text)!!.toMutableSet()
    newSet.remove(value)
    text = newSet.toString()
}

fun elementIsSelected(element : String) : Boolean{ //TODO: Improve function (if element is Eintrag -> Eintrag1 is also true at the moment)
    return text.contains(element)
}