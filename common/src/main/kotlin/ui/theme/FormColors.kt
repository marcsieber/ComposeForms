package ui.theme

import androidx.compose.ui.graphics.Color
import ui.theme.ColorsUtil.Companion.getColor

enum class FormColors(val color : Color) {

    ERROR(getColor("9F2C13")),
    VALID(getColor("2e7d32")),
    RIGHTTRACK(Color.Gray),

    BACKGROUND_COLOR(getColor("0E325E")),
    BACKGROUND_COLOR_LIGHT(getColor("ECEFF2")),
    FONT_ON_BACKGOUND(Color.White),
    BODY_BACKGROUND(Color.White),
    LABEL(Color.DarkGray)
}

enum class DropdownColors(val color : Color) {

    BUTTON_BACKGROUND(Color.Transparent),
    BACKGROUND_ELEMENT_SEL(getColor("E8E8E8")),
    BACKGROUND_ELEMENT_NOT_SEL(getColor("F8F8F8")),
    TEXT_ELEMENT_SEL(Color.Black),
    TEXT_ELEMENT_NOT_SEL(Color.Black)
}



class ColorsUtil(){
    companion object {
        fun getColor(colorString: String): Color {
            val r = java.lang.Long.parseLong(colorString.subSequence(0, 2).toString(), 16).toFloat() / 255
            val g = java.lang.Long.parseLong(colorString.subSequence(2, 4).toString(), 16).toFloat() / 255
            val b = java.lang.Long.parseLong(colorString.subSequence(4, 6).toString(), 16).toFloat() / 255
            return Color(r, g, b)
        }

        fun get(color : FormColors) : Color{
            return color.color
        }

        fun get(color : DropdownColors) : Color{
            return color.color
        }
    }




}