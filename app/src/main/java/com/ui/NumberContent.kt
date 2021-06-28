package com.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.model.CalculatorModel
import com.model.Model
import ui.theme.ColorsUtil
import ui.theme.DropdownColors
import ui.theme.FormColors


lateinit var calcModel : CalculatorModel<Double>

@ExperimentalFoundationApi
@Composable
fun NumberContent(model: Model){
    calcModel = remember{CalculatorModel<Double>(model)}
    with(model){
        Column(modifier = Modifier.fillMaxSize().padding(top = 0.dp, bottom = 12.dp)) {
            //Calculationfield
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.Top){
                OutlinedTextField(
                    calcModel.calculationString.value,
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

            //Calculator
            Row(modifier = Modifier.fillMaxSize().padding(bottom = 12.dp, top = 12.dp), verticalAlignment = Alignment.Top){
                Column(modifier = Modifier.fillMaxSize().padding(0.dp ,12.dp, 0.dp, 48.dp), verticalArrangement = Arrangement.Center) {
                    LazyVerticalGrid(cells = GridCells.Fixed(4)){
                        items(calcItems.size) {
                            CalcButton(
                                text = calcItems.get(it).first,
                                onClick = calcItems.get(it).second,
                                color = if(it%4==3){ButtonDefaults.buttonColors(backgroundColor = ColorsUtil.get(DropdownColors.BACKGROUND_ELEMENT_SEL))}
                                else{ButtonDefaults.buttonColors(backgroundColor = ColorsUtil.get(DropdownColors.BACKGROUND_ELEMENT_NOT_SEL))})
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalcButton(text : String, onClick: () -> Unit, color: ButtonColors = ButtonDefaults.buttonColors(backgroundColor = ColorsUtil.get(DropdownColors.BUTTON_BACKGROUND))){
    Button(onClick = onClick,
        modifier = Modifier.height(84.dp).width(84.dp).padding(6.dp),
        colors = color,
    ){Text(text)}
}

var calcItems = listOf<Pair<String, () -> Unit>>(
    Pair("7", {calcModel.newNumberForCalc(7)}),
    Pair("8", {calcModel.newNumberForCalc(8)}),
    Pair("9", {calcModel.newNumberForCalc(9)}),
    Pair("/", {calcModel.newOperatorForCalc("/")}),
    Pair("4", {calcModel.newNumberForCalc(4)}),
    Pair("5", {calcModel.newNumberForCalc(5)}),
    Pair("6", {calcModel.newNumberForCalc(6)}),
    Pair("*", {calcModel.newOperatorForCalc("*")}),
    Pair("1", {calcModel.newNumberForCalc(1)}),
    Pair("2", {calcModel.newNumberForCalc(2)}),
    Pair("3", {calcModel.newNumberForCalc(3)}),
    Pair("-", {calcModel.newOperatorForCalc("-")}),
    Pair(".", {}),
    Pair("0", {calcModel.newNumberForCalc(0)}),
    Pair("CE", {}),
    Pair("+", {calcModel.newOperatorForCalc("+")}),
)