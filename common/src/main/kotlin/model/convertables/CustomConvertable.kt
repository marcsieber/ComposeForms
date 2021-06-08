package model.convertables

import java.util.regex.Pattern

class CustomConvertable(private var replaceRegex : List<ReplacementPair>, private var convertUserView : Boolean = true, private var convertImmediately : Boolean = false){

    /**
     * Converts the string. Therefore replaces the string with the regex pattern given in the constructor
     * @param String: value that will be convertet
     * @return ConvertableResult
     */
    fun convertUserInput(valueAsText : String): ConvertableResult {
        var convertablePattern : Pattern
        replaceRegex.forEach{
            convertablePattern = Pattern.compile(it.convertibleRegex)
            if(convertablePattern.matcher(valueAsText).matches()){
                val convertIntoPattern = convertablePattern.matcher(valueAsText).replaceAll(it.convertIntoRegex)
                println(convertIntoPattern)
                return ConvertableResult(true, convertIntoPattern, convertUserView, convertImmediately)
            }
        }
        return ConvertableResult(false, "", convertUserView, convertImmediately)
    }

    //******************************************************************************************************
    //Getter

    fun getConvertUserView() : Boolean{
        return convertUserView
    }
}