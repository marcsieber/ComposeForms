package ch.ui

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

@ExperimentalFoundationApi
@Composable
fun NumberContent(model: Model){
    with(model){

        val floatingPointNumber = type === AttributeType.DOUBLE || type === AttributeType.FLOAT

        /**
         * A calcItem defines the information that is needed for a calculator-button.
         * A calcItem is made up of a Triple that is defined as following:
         * Triple(text: String, onClick : () -> Unit, enabled : Boolean)
         */
        var calcItems = listOf(
            Triple("7", {calcModel?.newNumberForCalc(7)}, true),
            Triple("8", {calcModel?.newNumberForCalc(8)}, true),
            Triple("9", {calcModel?.newNumberForCalc(9)}, true),
            Triple("/", {calcModel?.newOperatorForCalc("/")}, true),
            Triple("4", {calcModel?.newNumberForCalc(4)}, true),
            Triple("5", {calcModel?.newNumberForCalc(5)}, true),
            Triple("6", {calcModel?.newNumberForCalc(6)}, true),
            Triple("*", {calcModel?.newOperatorForCalc("*")}, true),
            Triple("1", {calcModel?.newNumberForCalc(1)}, true),
            Triple("2", {calcModel?.newNumberForCalc(2)}, true),
            Triple("3", {calcModel?.newNumberForCalc(3)}, true),
            Triple("-", {calcModel?.newOperatorForCalc("-")}, true),
            Triple(".", {}, if(floatingPointNumber) false else false), //TODO: Implement functionality for "." and set first false to true
            Triple("0", {calcModel?.newNumberForCalc(0)}, true),
            Triple("CE", {calcModel?.deleteLastCharacter()}, true),
            Triple("+", {calcModel?.newOperatorForCalc("+")}, true),
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
fun CalcButton(text : String, onClick: () -> Unit, enabled : Boolean, color: ButtonColors = ButtonDefaults.buttonColors(backgroundColor = ColorsUtil.get(DropdownColors.BUTTON_BACKGROUND))){
    Button(onClick = onClick,
        modifier = Modifier.height(84.dp).width(84.dp).padding(6.dp),
        enabled = enabled,
        colors = color,
    ){Text(text)}
}

