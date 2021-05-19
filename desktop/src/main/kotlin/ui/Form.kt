package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.FormModel
import model.util.Utilities
import model.util.attribute.*
import ui.theme.ColorsUtil.Companion.get
import ui.theme.DropdownColors
import ui.theme.FormColors


class Form {

    @Composable
    fun of(model: FormModel){
        with(model) {
            Column() {
                header(model)

                Row(modifier = Modifier.fillMaxSize().background(get(FormColors.BODY_BACKGROUND))) {
                    LazyColumn(Modifier.fillMaxHeight()) {
                        items(getAttributes()) { attribute ->
                            AttributeElement(attribute)
                        }
                    }
                }
            }
        }

    }

    @Composable
    private fun AttributeElement(attr: Attribute<*,*,*>){

        Row(Modifier.fillMaxWidth().padding(5.dp)) {

            Column(Modifier.fillMaxHeight()) {
                Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                    when (attr) {
                        is StringAttribute -> AttributeElement(attr)
                        is LongAttribute -> AttributeElement(attr)
                        is IntegerAttribute -> AttributeElement(attr)
                        is ShortAttribute -> AttributeElement(attr)
                        is DoubleAttribute -> AttributeElement(attr)
                        is FloatAttribute -> AttributeElement(attr)
                        is SelectionAttribute -> AttributeElement(attr)
                    }
                }

                Divider(Modifier.fillMaxWidth())
            }

        }
    }




    @Composable
    private fun AttributeElement(strAttr: StringAttribute<*>){
        InputField(strAttr){return@InputField true}
    }

    @Composable
    private fun AttributeElement(longAttr: LongAttribute<*>){
        InputField(longAttr)
            {
//                if (it.key == Key.DirectionUp) {
//                    longAttr.setValueAsTextFromKeyEvent( (longAttr.getValue() as Long + longAttr.validators.
//                    getStepSize()).toString())
//                }
//                if(it.key == Key.DirectionDown){
//                    longAttr.setValueAsTextFromKeyEvent( (longAttr.getValue() as Long - longAttr.getStepSize()).toString())
//                }
                return@InputField true
        }
    }

    @Composable
    private fun AttributeElement(intAttr: IntegerAttribute<*>){
        InputField(intAttr){ return@InputField true}
    }

    @Composable
    private fun AttributeElement(shortAttr: ShortAttribute<*>){
        InputField(shortAttr){ return@InputField true}
    }

    @Composable
    private fun AttributeElement(floatAttr: FloatAttribute<*>){
        InputField(floatAttr){
//            if (it.key == Key.DirectionUp) {
//                floatAttr.setValueAsTextFromKeyEvent( (floatAttr.getValue() as Float + floatAttr.getStepSize()).toString())
//            }
//            if(it.key == Key.DirectionDown){
//                floatAttr.setValueAsTextFromKeyEvent( (floatAttr.getValue() as Float - floatAttr.getStepSize()).toString())
//            }
            return@InputField true}
    }

    @Composable
    private fun AttributeElement(doubleAttr: DoubleAttribute<*>){
        InputField(doubleAttr){
//            if (it.key == Key.DirectionUp) {
//                doubleAttr.setValueAsTextFromKeyEvent( (doubleAttr.getValue() as Double + doubleAttr.getStepSize()).toString())
//            }
//            if(it.key == Key.DirectionDown){
//                doubleAttr.setValueAsTextFromKeyEvent( (doubleAttr.getValue() as Double - doubleAttr.getStepSize()).toString())
//            }
            return@InputField true
        }
    }

    @Composable fun AttributeElement(selectionAttribute: SelectionAttribute<*>){ //todo: undo & save when dropdown is open
        val dropDownIsOpen          = remember {mutableStateOf(false)}
        val selectionString         = mutableStateOf(selectionAttribute.getValueAsText().substring(1, selectionAttribute.getValueAsText().length-1))
        val label                   = selectionAttribute.getLabel()

        Row {
            Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
                Column {
                    if (!selectionString.value.equals("")) {
                        Text(
                            label,
                            color = get(FormColors.LABEL),
                            modifier = Modifier.wrapContentSize().padding(4.dp),
                            fontSize = 12.sp
                        )
                    }
                    OutlinedButton(
                        modifier = Modifier.height(50.dp),
                        onClick = { dropDownIsOpen.value = true },
                        shape = RoundedCornerShape(12),
                        colors = ButtonDefaults.buttonColors(backgroundColor = DropdownColors.BUTTON_BACKGROUND.color),
                        border = BorderStroke(1.dp, if (selectionAttribute.isValid()) get(FormColors.RIGHTTRACK) else get(FormColors.ERROR))
                    ) {
                        if (selectionString.value.equals("")) {
                            Text(label, color = get(FormColors.LABEL))
                        } else {
                            Text(selectionString.value)
                        }
                    }
                    DropDownMenu(
                        dropDownIsOpen, selectionAttribute.getPossibleSelections(), Utilities<Set<String>>().stringToSetConverter(selectionAttribute.getValueAsText()),
                        selectionAttribute::addUserSelection, selectionAttribute::removeUserSelection
                    )

                }

            }
            Column {
                if (!selectionAttribute.isValid()) {
                    for (msg in selectionAttribute.getErrorMessages()) {
                        Text(msg, color = get(FormColors.ERROR), fontSize = 12.sp, modifier = Modifier.padding(4.dp))
                    }
                }
            }
        }
    }



    @Composable
    fun DropDownMenu(dropDownIsOpen: MutableState<Boolean>, selections: Set<String>, currentSelectionValue: Set<String>,
                     add :(String)-> Unit, remove : (String) -> Unit) {

        DropdownMenu(
            expanded = dropDownIsOpen.value,
            onDismissRequest = { dropDownIsOpen.value = false},
            modifier = Modifier.wrapContentSize(),
            content = {
                selections.forEachIndexed { index, string ->
                    val elementIsSelected       = currentSelectionValue.contains(string)
                    val elementIsSelectedBackgroundColor  = if(elementIsSelected) get(DropdownColors.BACKGROUND_ELEMENT_SEL) else get(DropdownColors.BACKGROUND_ELEMENT_NOT_SEL)
                    val elementIsSelectedTextColor = if(elementIsSelected) get(DropdownColors.TEXT_ELEMENT_SEL) else get(DropdownColors.TEXT_ELEMENT_NOT_SEL)
                    DropdownMenuItem(
                        modifier = Modifier.background(elementIsSelectedBackgroundColor),
                        onClick = {
                            if (!elementIsSelected) {
                                add(string)
                            } else { remove(string)}},
                        content = {Text( text = string, modifier = Modifier.background(elementIsSelectedBackgroundColor), color = elementIsSelectedTextColor)}
                    )
                }
            }
        )
    }
}