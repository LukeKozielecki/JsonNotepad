package luke.koz.notepad.notes_list.domain

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.serialization.json.Json
import luke.koz.notepad.notes_list.model.NotesDataClass
import luke.koz.notepad.notes_list.model.NotesListResponse

class NotesViewModel () : ViewModel () {

    private val _contentMaxLength : Int = 100
    private val _noteContent  = MutableStateFlow<String?>(null)

/*
    val displayedContent: StateFlow<String> = _noteContent
        .map { content ->
            content?.let {
                if (it.length > _contentMaxLength) {
                    it.take(_contentMaxLength) + "..."
                } else {
                    it
                }
            } ?: ""
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, "")
*/

    /**
     * Function to truncate note to length size of given int.
     * && this is sketchy if it were to ever be translated to arabian, because how they write words
     */
    fun truncateNoteContent(note: NotesDataClass) : Flow<String> {
        val noteContent  = MutableStateFlow<String?>(note.content)
        return noteContent.map { content ->
            content?.let {
                if (it.length > _contentMaxLength) {
                    it.take(_contentMaxLength) + "..."
                } else {
                    it
                }
            } ?: ""
        }
    }
    fun updateContent(updatedContent: String) {
        _noteContent.value = updatedContent
    }

    fun loadNotes(context: Context, fileName: String): NotesListResponse {
        val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        return Json.decodeFromString(jsonString)
    }

}