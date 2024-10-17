package luke.koz.notepad.notes_list.domain

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import luke.koz.notepad.notes_list.model.NotesDataClass
import luke.koz.notepad.notes_list.model.NotesListResponse
import luke.koz.notepad.utils.domain.DefaultNotesLoader
import luke.koz.notepad.utils.domain.NotesLoader

class NotesViewModel : ViewModel () {

    private val _notesLoader: NotesLoader = DefaultNotesLoader()
    private val _contentMaxLength : Int = 100
    private val _noteContent  = MutableStateFlow<String?>(null)

    private val _notes = MutableLiveData<NotesListResponse>()
    val notes: LiveData<NotesListResponse> get() = _notes

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

    fun loadNotes(context: Context, fileName: String) {
        val notesListResponse = _notesLoader.loadNotes(context, fileName)
        _notes.value = notesListResponse
    }

}