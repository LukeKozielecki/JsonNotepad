package luke.koz.notepad.utils.domain

import android.content.Context
import android.util.Log
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import luke.koz.notepad.notes_list.model.NotesDataClass
import luke.koz.notepad.notes_list.model.NotesListResponse

interface NotesManager {
    fun loadNotes(context: Context, fileName: String): NotesListResponse
    fun saveNotes(context: Context, fileName: String, notes: NotesListResponse)
}

class DefaultNotesManager : NotesManager {
    override fun loadNotes(context: Context, fileName: String): NotesListResponse {
        val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        return Json.decodeFromString(jsonString)
    }

    override fun saveNotes(context: Context, fileName: String, notes: NotesListResponse) {
        val jsonString = Json.encodeToString(notes) // Serialize NotesDataClass to JSON
        Log.d("NoteStorage", "Serialized JSON: $jsonString") // Log serialized JSON

        try {
            context.openFileOutput(fileName, Context.MODE_PRIVATE).use { output ->
                output.write(jsonString.toByteArray())
            }
            Log.d("NoteStorage", "Note saved to file successfully.")
        } catch (e: Exception) {
            Log.e("NoteStorage", "Error saving note to file: ${e.message}")
        }
    }
}