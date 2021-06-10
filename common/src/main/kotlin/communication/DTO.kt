package communication


import kotlinx.serialization.Serializable


@Serializable
class DTOText(val id: Int, val text: String, val label: String, val attrType: AttributeType = AttributeType.OTHER,
             val possibleSelections: Set<String> = emptySet())

@Serializable
class DTOValidation(val onRightTrack: Boolean = true, val isValid: Boolean = true,
                    val readOnly: Boolean = false, val errorMessages : List<String> = emptyList())

@Serializable
class DTOCommand(val command: Command)



enum class Command(){
    NEXT,
    PREVIOUS,
    REQUEST,
    SAVE,
    UNDO
}

enum class AttributeType(){
    STRING,
    INTEGER,
    SHORT,
    DOUBLE,
    FLOAT,
    LONG,
    SELECTION,
    OTHER
}
