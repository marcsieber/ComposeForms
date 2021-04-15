package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp
import model.FormModel
import model.util.attribute.*
import javax.swing.border.Border


class Form {

    @Composable
    fun of(model: FormModel){
        with(model) {
            Column {
                Text(model.getTitle())


                LazyColumn {
                    items(model.getAttributes()) { attribute ->
                        attributeElement(attribute)
                    }
                }

                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                        enabled = isChangedForAll(),
                        onClick = {
                            saveAll()
                        }) {
                        Text("Undo")
                    }

                    Button(
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                        enabled = isValidForAll() && isChangedForAll(),
                        onClick = {
                            saveAll()
                        }) {
                        Text("Save")
                    }
                }
            }
        }

    }

    @Composable
    private fun attributeElement(attr: Attribute<*,*>){

        Row(Modifier.fillMaxWidth(0.8f).padding(5.dp)
            ) {

            Column() {
                Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween) {
                    // Variante 1
                    when (attr) {
                        is StringAttribute -> stringAttributeElement(attr)
                        is LongAttribute -> longAttributeElement(attr)
                        is IntegerAttribute -> integerAttributeElement(attr)
                        is ShortAttribute -> shortAttributeElement(attr)
                        is DoubleAttribute -> doubleAttributeElement(attr)
                        is FloatAttribute -> floatAttributeElement(attr)
                    }

                }

                //Variante 2
//        when(attr){
//            is IntegerAttribute -> testAttributeElement(attr)
//            is StringAttribute -> testAttributeElement(attr)
//        }

                Divider(Modifier.fillMaxWidth())
            }

        }
    }


    @Composable
    private fun testAttributeElement(intAttr: IntegerAttribute){
        Text("Integer")
    }

    @Composable
    private fun testAttributeElement(strAttr: StringAttribute){
        Text("String")
    }



    @Composable
    private fun stringAttributeElement(strAttr: StringAttribute){
        OutlinedTextField(          //stringValue
            modifier = Modifier,
            value = strAttr.getValueAsText(),
            onValueChange = {strAttr.setValueAsText(it)},
            label = {Text(strAttr.getLabel())} ,
            readOnly = strAttr.isReadOnly(),
            isError = !strAttr.isValid()
        )
    }

    @Composable
    private fun NumberAttributeElement(numAttr: NumberAttribute<*, *>){
        OutlinedTextField(          //intValue1
            modifier = Modifier.onKeyEvent{event ->
                if (event.key == Key.DirectionUp) {
                    numAttr.setValueAsText( (numAttr.getValue().toDouble() + numAttr.getStepSize().toDouble()).toString())
                }
                if(event.key == Key.DirectionDown){
                    numAttr.setValueAsText( (numAttr.getValue().toDouble() - numAttr.getStepSize().toDouble()).toString())
                }
                return@onKeyEvent true},
            value = numAttr.getValueAsText(),
            onValueChange = {numAttr.setValueAsText(it)},
            label = {Text(numAttr.getLabel())},
            readOnly = numAttr.isReadOnly(),
            isError = !numAttr.isValid()
        )
    }

    @Composable
    private fun longAttributeElement(longAttr: LongAttribute){
        NumberAttributeElement(longAttr)
    }

    @Composable
    private fun integerAttributeElement(intAttr: IntegerAttribute){
        NumberAttributeElement(intAttr)
    }

    @Composable
    private fun shortAttributeElement(shortAttr: ShortAttribute){
        NumberAttributeElement(shortAttr)
    }

    @Composable
    private fun floatAttributeElement(floatAttr: FloatAttribute){
        NumberAttributeElement(floatAttr)
    }

    @Composable
    private fun doubleAttributeElement(doubleAttr: DoubleAttribute){
        NumberAttributeElement(doubleAttr)
    }
}