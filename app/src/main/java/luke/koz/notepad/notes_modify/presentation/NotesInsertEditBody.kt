package luke.koz.notepad.notes_modify.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import luke.koz.notepad.notes_list.model.NotesDataClass
import luke.koz.notepad.notes_modify.domain.NoteInputEditViewModel
import java.util.UUID

@Composable
fun NotesInsertEditBody(modifier: Modifier = Modifier, uuidAsString : String? = null, uuidFetchMethod: (String) -> Unit, fetchNewNote: (NotesDataClass) -> Unit) {
    uuidFetchMethod(uuidAsString ?: "")
    val noteInputEditViewModel = NoteInputEditViewModel()
    val context = LocalContext.current
    noteInputEditViewModel.loadNotes(context, "notes.json")
    val uuid = UUID.fromString(uuidAsString)
    LaunchedEffect(Unit) {
        noteInputEditViewModel.loadNotes(context, "notes.json")
    }
    val notesListResponse by noteInputEditViewModel.notes.observeAsState()

    notesListResponse?.let { response ->
        val listOfNotes = response.notesResponse.filter { it.id == uuid }
        val matchedNote = if (listOfNotes.isNotEmpty()) listOfNotes.first() else null

        if (matchedNote != null) {
            Box(modifier = modifier) {
                Box(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
                    NoteInputForm(
                        modifier = Modifier.fillMaxWidth(),
                        noteEntry = matchedNote,
                        fetchNewNote = fetchNewNote
                    )
                }
            }
        } else {
            // Handle case when no note is matched
            Column(modifier = Modifier) {
                Text("Because this is ad hoc")
                Text("The passed string was $uuidAsString")
            }
        }
    }
}