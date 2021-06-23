package model.convertibles

import java.util.regex.Pattern

class CustomConvertible(private var replaceRegex : List<ReplacementPair>, private var convertUserView : Boolean = true, private var convertImmediately : Boolean = false){

    /**
     * Converts the string. Therefore replaces the string with the regex pattern given in the constructor
     * @param String: value that will be convertet
     * @return ConvertibleResult
     */
    fun convertUserInput(valueAsText : String): ConvertibleResult {
        var convertiblePattern : Pattern
        replaceRegex.forEach{
            convertiblePattern = Pattern.compile(it.convertibleRegex)
            if(convertiblePattern.matcher(valueAsText).matches()){
                val convertIntoPattern = convertiblePattern.matcher(valueAsText).replaceAll(it.convertIntoRegex)
                println(convertIntoPattern)
                return ConvertibleResult(true, convertIntoPattern, convertUserView, convertImmediately)
            }
        }
        return ConvertibleResult(false, "", convertUserView, convertImmediately)
    }

    //******************************************************************************************************
    //Getter

    fun getConvertUserView() : Boolean{
        return convertUserView
    }
}