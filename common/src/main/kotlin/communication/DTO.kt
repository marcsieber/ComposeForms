package communication

import model.util.attribute.Attribute
import kotlinx.serialization.Serializable

@Serializable
class DTOText(val id: Long, val text: String)

@Serializable
class DTOCommand(val command: Command)



enum class Command(){
    NEXT,
    PREVIOUS,
    SAVE,
    UNDO
}
