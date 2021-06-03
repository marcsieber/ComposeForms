package model.convertables

import java.util.regex.Pattern

class CustomConvertable(private var replaceRegex : List<ReplacementPair>, private var convertUserView : Boolean = true, private var convertImmediately : Boolean = false){

    fun convertUserInput(valueAsText : String): ConvertableResult {
        var convertablePattern : Pattern
        replaceRegex.forEach{
            convertablePattern = Pattern.compile(it.convertable_regex)
            if(convertablePattern.matcher(valueAsText).matches()){
                val convertIntoPattern = convertablePattern.matcher(valueAsText).replaceAll(it.convert_into_regex)
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