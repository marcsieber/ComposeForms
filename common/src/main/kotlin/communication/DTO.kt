package communication


import kotlinx.serialization.Serializable


@Serializable
class DTOText(val id: Long, val text: String, val onRightTrack: Boolean = true, val isValid: Boolean = true,
              val attrType: AttributeType = AttributeType.OTHER)

@Serializable
class DTOCommand(val command: Command)



enum class Command(){
    NEXT,
    PREVIOUS,
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
