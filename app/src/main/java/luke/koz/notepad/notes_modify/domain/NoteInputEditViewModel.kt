package luke.koz.notepad.notes_modify.domain

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import luke.koz.notepad.notes_list.model.NotesDataClass
import luke.koz.notepad.notes_list.model.NotesListResponse
import luke.koz.notepad.utils.domain.DefaultNotesManager
import luke.koz.notepad.utils.domain.NotesManager
import java.io.BufferedReader
import java.util.UUID

class NoteInputEditViewModel : ViewModel() {
    private val _notesManager: NotesManager = DefaultNotesManager()
    private val _notes = MutableLiveData<NotesListResponse>()

    val notes: LiveData<NotesListResponse> get() = _notes

    fun loadNotes(context: Context, fileName: String) {
        loadPredefinedNotes(context, fileName)
        Log.d("NotesInputEditViewModel","${_notes.value?.notesResponse?.size}")
        val notesListResponse = _notesManager.loadNotes(context, fileName)
        _notes.value = notesListResponse
    }

    private fun loadPredefinedNotes(context: Context, fileName: String) {
        val notesListResponse = _notesManager.loadNotes(context, fileName)
        _notes.value = notesListResponse
    }

    private fun loadNotesFromStorage(context: Context, fileName: String): NotesListResponse? {
        return try {
            context.openFileInput(fileName).bufferedReader().use { reader ->
                val jsonData = reader.readText() // Read the file text
                Json.decodeFromString<NotesListResponse>(jsonData) // Deserialize to NotesListResponse
            }
        } catch (e: Exception) {
            Log.e("NotesStorage", "Error loading notes from internal storage: ${e.message}")
            null
        }
    }

    fun saveNotes(context: Context, fileName: String, uuidAsString: String?, fetchNewNote: NotesDataClass?) {
        loadNotes(context, fileName)

        val listOfNotes = _notes.value?.notesResponse?.filter { it.id == UUID.fromString(uuidAsString) }
        val matchedNote = listOfNotes?.firstOrNull()
        val updatedNotes = _notes.value?.notesResponse?.toMutableList() ?: mutableListOf()

        if (matchedNote != null) {
            if (fetchNewNote != null) {
                updatedNotes.remove(matchedNote)
                updatedNotes.add(fetchNewNote)
            } else {
                Log.e("NotesInputEditViewModel", "fetchNewNote is null, not saving changes.")
            }
        } else {
            Log.e("NotesInputEditViewModel", "No notes matched with the given UUID.")
        }

        val notesListResponse = NotesListResponse(updatedNotes)
        _notesManager.saveNotes(context, fileName, notesListResponse)
    }
}