package luke.koz.notepad.notes_list.model

//import com.google.firebase.dataconnect.serializers.UUIDSerializer
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable data class NotesListResponse(@Serializable val notesResponse : List<NotesDataClass>)

@Serializable
data class NotesDataClass(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val content: String,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long? = null,
    val tags: MutableList<String> = mutableListOf(),
    val category: String? = null,
    val priority: Priority = Priority.MEDIUM,
    val noteStatus: NoteStatus = NoteStatus.DRAFT,
    val attachments: List<String> = emptyList(),
    val sharedWith: List<String> = emptyList(),
    val lastModifiedBy: String? = null,
    val reminderAt: Long? = null
)

enum class Priority {
    LOW, MEDIUM, HIGH
}

enum class NoteStatus {
    DRAFT, IN_PROGRESS, COMPLETED
}
