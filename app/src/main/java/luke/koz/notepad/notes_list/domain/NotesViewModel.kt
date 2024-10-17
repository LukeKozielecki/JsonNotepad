package luke.koz.notepad.notes_list.domain

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import luke.koz.notepad.notes_list.model.NotesDataClass
import luke.koz.notepad.notes_list.model.NotesListResponse
import luke.koz.notepad.utils.domain.DefaultNotesManager
import luke.koz.notepad.utils.domain.NotesManager
import java.io.BufferedReader

class NotesViewModel : ViewModel () {

    private val _notesManager: NotesManager = DefaultNotesManager()
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
        loadNotesFromStorage(context, fileName)
        val notesListResponse = _notesManager.loadNotes(context, fileName)
        _notes.value = notesListResponse
        loadNotesFromStorage(context, fileName)?.let {
            _notes.value = loadNotesFromStorage(context, fileName)!!
        } ?: run () {
            Log.d("NotesStorage", "Loading notes from storage returned `null`")
        }
    }
    private val jsonDecoder = Json {
        serializersModule = SerializersModule {
            polymorphic(NotesListResponse::class)
        }
        // Use .ignoreUnknownKeys() to prevent failure if JSON has extra fields
        ignoreUnknownKeys = true
    }
    private fun loadNotesFromStorage(context: Context, fileName: String) : NotesListResponse?{
        return try {
            val jsonString = context.openFileInput(fileName).bufferedReader().use(BufferedReader::readText)
            jsonDecoder.decodeFromString(jsonString)
        } catch (e: Exception) {
            Log.e("NotesStorage", "Error loading notes from internal storage: ${e.message}")
            null
        }
    }

}