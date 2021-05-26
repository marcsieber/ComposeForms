package model.convertables

import java.util.regex.Pattern

class CustomConvertable(private var replaceRegex : List<ReplacementPair>){

    fun convertUserInput(valueAsText : String): ConvertableResult {
        var isConvertable : Boolean
        var convertablePattern : Pattern
        replaceRegex.forEach{
            convertablePattern = Pattern.compile(it.convertable_regex)
            if(convertablePattern.matcher(valueAsText).matches()){
                val convertIntoPattern = Pattern.compile(it.convert_into_regex)
                return ConvertableResult(true, convertIntoPattern.toString())
            }
        }
        return ConvertableResult(false, "")
    }

}