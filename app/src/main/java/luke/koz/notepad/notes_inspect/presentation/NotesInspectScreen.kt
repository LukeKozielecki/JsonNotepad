package luke.koz.notepad.notes_inspect.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Serializable
import luke.koz.notepad.notes_list.domain.NotesViewModel
import luke.koz.notepad.notes_list.model.NotesDataClass
import luke.koz.notepad.notes_list.presentation.NotesSingleNote
import luke.koz.notepad.ui.theme.NotepadTheme
import luke.koz.notepad.utils.presentation.ScaffoldTopAppBar
import java.util.UUID

@Serializable data class NotesInspectScreenRoute(val uuidAsString: String )

/**
 * Screen for displaying a Specific note fullscreen
 *  Why UUID as string: because it is serializable as default,
 *  && we can get it to UUID just by invoking UUID.fromString()
 *  && it is less work than creating custom nav deserializer
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesInspectScreen(modifier: Modifier = Modifier, uuidAsString: String) {
    Scaffold (
        topBar = {
            ScaffoldTopAppBar(
                title = "Notes",
                canNavigateBack = false,
                modifier = modifier,
                scrollBehavior = null
            )
        }
    ) { innerPadding ->
        // todo this needs it's own view model
        // todo && that means move loadNotes to interface
        val tempViewModel = NotesViewModel()
        val context = LocalContext.current
        val listOfNotes = tempViewModel.loadNotes(context,"notes.json").notesResponse.filter { it.id == UUID.fromString(uuidAsString) }
        var matchedNote : NotesDataClass = tempViewModel.loadNotes(context,"notes.json").notesResponse[0]
        if (listOfNotes.isNotEmpty()) {
            // Technically this implementation means there could be many with same id
            matchedNote = listOfNotes.first() // if we get more than one than take first, because this is +-ad hoc
        } else {
            // because this is ad hoc
            Column (Modifier.padding(innerPadding)) {
                Text("Because this is ad hoc")
                Text("The passed string was $uuidAsString")
            }
        }
        Box (modifier = Modifier.padding(innerPadding)) {
            Box (modifier = Modifier.padding(bottom = 8.dp, start = 8.dp, end = 8.dp)) {
                NotesSingleNote(
                    modifier.fillMaxWidth(),
                    note = matchedNote,
                    truncateNoteBoolean = false,
                    onClick = {}
                )
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
private fun NotesInspectScreenPreview() {
    NotepadTheme {
        NotesInspectScreen(uuidAsString = "550e8400-e29b-41d4-a716-446655440000")
    }
}