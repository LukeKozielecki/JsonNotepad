package luke.koz.notepad.notes_modify.domain

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import luke.koz.notepad.notes_list.model.NotesListResponse
import luke.koz.notepad.utils.domain.DefaultNotesLoader
import luke.koz.notepad.utils.domain.NotesLoader

class NoteInputEditViewModel : ViewModel() {
    private val _notesLoader: NotesLoader = DefaultNotesLoader()
    private val _notes = MutableLiveData<NotesListResponse>()
    val notes: LiveData<NotesListResponse> get() = _notes
    fun loadNotes(context: Context, fileName: String) {
        val notesListResponse = _notesLoader.loadNotes(context, fileName)
        _notes.value = notesListResponse
    }
}