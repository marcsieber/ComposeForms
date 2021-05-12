package ui.theme

import androidx.compose.ui.graphics.Color

class Colors {



    companion object {
        fun getColor(colorString: String): Color {
            val r = java.lang.Long.parseLong(colorString.subSequence(0, 2).toString(), 16).toFloat() / 255
            val g = java.lang.Long.parseLong(colorString.subSequence(2, 4).toString(), 16).toFloat() / 255
            val b = java.lang.Long.parseLong(colorString.subSequence(4, 6).toString(), 16).toFloat() / 255
            return Color(r, g, b)
        }
    }
}