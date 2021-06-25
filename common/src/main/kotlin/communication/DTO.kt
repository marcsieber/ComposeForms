package communication


import kotlinx.serialization.Serializable

@Serializable
class DTOAttribute(val id: Int, val label: String, val attrType: AttributeType = AttributeType.OTHER,
                   val possibleSelections: Set<String> = emptySet())

@Serializable
class DTOText(val id: Int, val text: String)

@Serializable
class DTOValidation(val onRightTrack: Boolean = true, val isValid: Boolean = true,
                    val readOnly: Boolean = false, val errorMessages : List<String> = emptyList())

@Serializable
class DTOCommand(val command: Command)



enum class Command {
    NEXT,
    PREVIOUS,
    REQUEST,
    SAVE,
    RESET
}

enum class AttributeType {
    STRING,
    INTEGER,
    SHORT,
    DOUBLE,
    FLOAT,
    LONG,
    SELECTION,
    OTHER
}
