package ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.unit.dp
import model.FormModel
import model.util.attribute.*
import model.util.presentationElements.Field
import model.util.presentationElements.FieldSize
import ui.theme.ColorsUtil.Companion.get
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

                            //lists with one or two fields (depending on field sizes) for cells in LazyVerticalGrid
                            val cellsWithFields : MutableList<MutableList<Field>> = mutableListOf()

                            it.getFields().forEach{
                                if(it.getFieldSize() === FieldSize.NORMAL){
                                    cellsWithFields.add(mutableStateListOf(it))
                                }
                                else{
                                    if(cellsWithFields.isEmpty() || cellsWithFields.get(cellsWithFields.size-1).size != 1){
                                        cellsWithFields.add(mutableStateListOf(it))
                                    }
                                    else{  //There is already 1 small element
                                        cellsWithFields.get(cellsWithFields.size-1).add(it)
                                    }
                                }
                            }

                            LazyVerticalGrid(cells = GridCells.Adaptive(300.dp)){
                                items(cellsWithFields){
                                    CellElement(model, it)
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    @Composable
    private fun CellElement(model: FormModel, listOfFields : MutableList<Field>){
        //Small sized fields
        if(listOfFields[0].getFieldSize() == FieldSize.SMALL){
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(modifier = Modifier.fillMaxWidth(0.5f)) {
                    AttributeElement(model, listOfFields[0].getAttribute())
                }

                if(listOfFields.size == 2){
                    Row(modifier = Modifier.fillMaxWidth(1f)){
                        AttributeElement(model, listOfFields[1].getAttribute())
                    }
                }
            }
        }

        //Normal sized fields
        else{
            AttributeElement(model, listOfFields[0].getAttribute())
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

    @Composable fun AttributeElement(model: FormModel, selectionAttribute: SelectionAttribute<*>){ //todo: reset & save when dropdown is open
        InputField(model, selectionAttribute){
            return@InputField true
        }
//        SelectionField(model, selectionAttribute)
    }




}