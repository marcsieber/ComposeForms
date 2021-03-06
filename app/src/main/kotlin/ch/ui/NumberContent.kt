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

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ch.model.Model
import communication.AttributeType
import ui.theme.ColorsUtil
import ui.theme.DropdownColors
import ui.theme.FormColors

/**
 * @author Louisa Reinger
 * @author Steve Vogel
 */


@ExperimentalFoundationApi
@Composable
fun NumberContent(model: Model){
    with(model){



        /**
         * A calcItem defines the information that is needed for a calculator-button.
         * A calcItem is made up of a Triple that is defined as following:
         * Triple(text: String, onClick : () -> Unit, enabled : Boolean)
         */
        val calcItems = listOf<Triple<String, () -> Unit, Boolean>>(
            Triple("7",  {  calcModel?.newNumberForCalc(7)   }, true),
            Triple("8",  {  calcModel?.newNumberForCalc(8)   }, true),
            Triple("9",  {  calcModel?.newNumberForCalc(9)   }, true),
            Triple("/",  {  calcModel?.newOperatorForCalc("/")}, true),
            Triple("4",  {  calcModel?.newNumberForCalc(4)   }, true),
            Triple("5",  {  calcModel?.newNumberForCalc(5)   }, true),
            Triple("6",  {  calcModel?.newNumberForCalc(6)   }, true),
            Triple("*",  {  calcModel?.newOperatorForCalc("*")}, true),
            Triple("1",  {  calcModel?.newNumberForCalc(1)   }, true),
            Triple("2",  {  calcModel?.newNumberForCalc(2)   }, true),
            Triple("3",  {  calcModel?.newNumberForCalc(3)   }, true),
            Triple("-",  {  calcModel?.newOperatorForCalc("-") }, true),
            Triple(".",  {  calcModel?.addSpecialCharacters('.')    }, calcModel!!.pointIsActive.value),
            Triple("0",  {  calcModel?.newNumberForCalc(0)   }, true),
            Triple("CE", {  calcModel?.deleteLastCharacter()}, true),
            Triple("+",  {  calcModel?.newOperatorForCalc("+")}, true),
        )


        Column(modifier = Modifier.fillMaxSize().padding(top = 0.dp, bottom = 12.dp)) {
            //Calculationfield
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Top){
                calcModel?.calculationString?.value?.let{
                    OutlinedTextField(
                        it,
                        onValueChange = { },
                        label = { Icon(Icons.Filled.Calculate, "Calculation-Icon") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = ColorsUtil.get(FormColors.BACKGROUND_COLOR),
                            focusedLabelColor = ColorsUtil.get(FormColors.BACKGROUND_COLOR),
                            cursorColor = Color.Black
                        ),
                        readOnly = true
                    )
                }
            }

            //Calculator
            Row(modifier = Modifier.fillMaxSize().padding(bottom = 12.dp, top = 12.dp), verticalAlignment = Alignment.Top){
                Column(modifier = Modifier.fillMaxSize().padding(0.dp ,12.dp, 0.dp, 48.dp), verticalArrangement = Arrangement.Center) {
                    LazyVerticalGrid(cells = GridCells.Fixed(4)){
                        items(calcItems.size) {
                            CalcButton(
                                text = calcItems.get(it).first,
                                onClick = {calcItems.get(it).second()},
                                enabled = calcItems.get(it).third,
                                backgroundColor =   if(it%4==3){ ColorsUtil.get(DropdownColors.BACKGROUND_ELEMENT_SEL)}
                                                    else{ ColorsUtil.get(DropdownColors.BACKGROUND_ELEMENT_NOT_SEL)},
                                border = if(!calcItems.get(it).third){ // Button disabled
                                    BorderStroke(1.dp, ColorsUtil.get(DropdownColors.BACKGROUND_ELEMENT_SEL))} else BorderStroke(0.dp, Color.Transparent))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalcButton(text : String, onClick: () -> Unit, enabled : Boolean, backgroundColor : Color, border : BorderStroke){
    Button(onClick = onClick,
        modifier = Modifier.requiredHeight(84.dp).width(84.dp).padding(6.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(backgroundColor = backgroundColor, disabledBackgroundColor = Color.Transparent),
        border = border
    ){Text(text)}
}

