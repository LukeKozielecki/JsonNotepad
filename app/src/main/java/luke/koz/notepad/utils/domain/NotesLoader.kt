package luke.koz.notepad.utils.domain

import android.content.Context
import kotlinx.serialization.json.Json
import luke.koz.notepad.notes_list.model.NotesListResponse

interface NotesLoader {
    fun loadNotes(context: Context, fileName: String): NotesListResponse
}

class DefaultNotesLoader : NotesLoader {
    override fun loadNotes(context: Context, fileName: String): NotesListResponse {
        val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        return Json.decodeFromString(jsonString) // Assuming Json is defined elsewhere
    }
}