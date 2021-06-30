package ui

import androidx.compose.desktop.Window
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.IModel
import server.QRCodeService
import ui.theme.ColorsUtil.Companion.get
import ui.theme.DropdownColors
import ui.theme.FormColors

@Composable
fun header(model : IModel){
    with(model){
        TopAppBar(
            backgroundColor = get(FormColors.BACKGROUND_COLOR),
            elevation = 100.dp
        ){
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){

                //title
                Text(getTitle(), color = get(FormColors.FONT_ON_BACKGOUND), fontSize = 22.sp, modifier = Modifier.align(
                    Alignment.CenterVertically))

                Row {
                    LanguageDropDownButton(model)
                    HeaderButton(model = model, buttonText = "Reset", enabled = isChangedForAll(), onClick = {resetAll()})
                    HeaderButton(model = model, buttonText = "Save", enabled = isValidForAll() && isChangedForAll(), onClick = {saveAll()})
                    if(smartphoneOption()){
                        HeaderButton(model = model, buttonText = "Show QR-Code", enabled = true, onClick = { openQrCodeWindow(model, 500)})
                    }
                }
            }
        }
    }
}

//************************************************
//Internal Functions

@Composable
private fun HeaderButton(model: IModel, buttonText: String, enabled: Boolean, onClick : () -> Unit){
    Button(
        modifier = Modifier.padding(4.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = get(FormColors.VALID)),
        enabled = enabled,
        onClick = { onClick() } ) {
            Text(buttonText, color = if(enabled) Color.White else Color.Gray)
        }
}

@Composable
private fun LanguageDropDownButton(model: IModel){
    with(model){
        Column {
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
    }
}


private fun openQrCodeWindow(model: IModel, size : Int){
    with(model){
        Window(size = IntSize(size, size)) {
            val img = remember{ mutableStateOf(ImageBitmap(size,size)) }
            val ip = model.getIPAdress()
            QRCodeService().getQRCode("https://stevevogel1.github.io/ComposeForms/$ip", size){ img.value = it}

            Row(modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically){
                Image(img.value, "QR Code")
            }
        }
    }
}


