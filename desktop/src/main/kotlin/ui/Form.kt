package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.FormModel
import model.util.Utilities
import model.util.attribute.*
import ui.theme.ColorsUtil.Companion.get
import ui.theme.DropdownColors
import ui.theme.FormColors


class Form {

    @ExperimentalFoundationApi
    @Composable
    fun of(model: FormModel){
        with(model) {
            Column {
                header(model)

                Row(modifier = Modifier.fillMaxSize().background(get(FormColors.BODY_BACKGROUND))) {
                    Column {
                        if(getGroups().isEmpty()){
                            NoGroupsMessage()
                        }
                        getGroups().forEach {
                            GroupTitle(it.title)

                            LazyVerticalGrid(cells = GridCells.Adaptive(300.dp)){
                                items(it.getAttributes()){ attribute ->
                                    AttributeElement(model, attribute)
                                }
                            }
                        }
                    }
                }
            }
        }

    }



    @Composable
    private fun AttributeElement(model: FormModel, attr: Attribute<*,*,*>){
        when (attr) {
            is StringAttribute -> AttributeElement(model, attr)
            is LongAttribute -> AttributeElement(model, attr)
            is IntegerAttribute -> AttributeElement(model, attr)
            is ShortAttribute -> AttributeElement(model, attr)
            is DoubleAttribute -> AttributeElement(model, attr)
            is FloatAttribute -> AttributeElement(model, attr)
            is SelectionAttribute -> AttributeElement(model, attr)
        }
    }




    @Composable
    private fun AttributeElement(model: FormModel, strAttr: StringAttribute<*>){
        InputField(model, strAttr){return@InputField true}
    }

    @Composable
    private fun AttributeElement(model: FormModel, longAttr: LongAttribute<*>){
        InputField(model, longAttr)
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
    private fun AttributeElement(model: FormModel, intAttr: IntegerAttribute<*>){
        InputField(model, intAttr){ return@InputField true}
    }

    @Composable
    private fun AttributeElement(model: FormModel, shortAttr: ShortAttribute<*>){
        InputField(model, shortAttr){ return@InputField true}
    }

    @Composable
    private fun AttributeElement(model: FormModel, floatAttr: FloatAttribute<*>){
        InputField(model, floatAttr){
//            if (it.key == Key.DirectionUp) {
//                floatAttr.setValueAsTextFromKeyEvent( (floatAttr.getValue() as Float + floatAttr.getStepSize()).toString())
//            }
//            if(it.key == Key.DirectionDown){
//                floatAttr.setValueAsTextFromKeyEvent( (floatAttr.getValue() as Float - floatAttr.getStepSize()).toString())
//            }
            return@InputField true}
    }

    @Composable
    private fun AttributeElement(model: FormModel, doubleAttr: DoubleAttribute<*>){
        InputField(model, doubleAttr){
//            if (it.key == Key.DirectionUp) {
//                doubleAttr.setValueAsTextFromKeyEvent( (doubleAttr.getValue() as Double + doubleAttr.getStepSize()).toString())
//            }
//            if(it.key == Key.DirectionDown){
//                doubleAttr.setValueAsTextFromKeyEvent( (doubleAttr.getValue() as Double - doubleAttr.getStepSize()).toString())
//            }
            return@InputField true
        }
    }

    @Composable fun AttributeElement(model: FormModel, selectionAttribute: SelectionAttribute<*>){ //todo: undo & save when dropdown is open
        SelectionField(model, selectionAttribute)
    }




}