package luke.koz.notepad.notes_inspect.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import luke.koz.notepad.notes_list.presentation.NotesSingleNote
import luke.koz.notepad.notes_modify.domain.NoteInputEditViewModel
import luke.koz.notepad.notes_modify.presentation.NotesInsertEditScreenRoute
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
fun NotesInspectScreen(modifier: Modifier = Modifier, uuidAsString: String, navHostController: NavHostController) {
    Scaffold (
        topBar = {
            ScaffoldTopAppBar(
                title = "Notes",
                canNavigateBack = false,
                modifier = modifier,
                scrollBehavior = null
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navHostController.navigate(NotesInsertEditScreenRoute(uuidAsString = uuidAsString))
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .padding(
                        end = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateEndPadding(LocalLayoutDirection.current)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit/insert note",
                )
            }
        }
    ) { innerPadding ->
        // todo this needs it's own view model
        // completed move loadNotes to interface
        val noteInputEditViewModel = NoteInputEditViewModel()
        val context = LocalContext.current
        val uuid = UUID.fromString(uuidAsString)
        noteInputEditViewModel.loadNotes(context, "notes.json")

        LaunchedEffect(Unit) {
            noteInputEditViewModel.loadNotes(context, "notes.json")
        }

        val notesListResponse by noteInputEditViewModel.notes.observeAsState()
        notesListResponse?.let { response ->
            val listOfNotes = response.notesResponse.filter { it.id == uuid }
            val matchedNote = if (listOfNotes.isNotEmpty()) listOfNotes.first() else null

            if (matchedNote != null) {
                Box(modifier = Modifier.padding(innerPadding)) {
                    Box(modifier = Modifier.padding(bottom = 8.dp, start = 8.dp, end = 8.dp)) {
                        NotesSingleNote(
                            modifier = Modifier.fillMaxWidth(),
                            note = matchedNote,
                            truncateNoteBoolean = false,
                            onClick = null
                        )
                    }
                }
            } else {
                // Handle case when no note is matched
                Column(modifier = Modifier.padding(innerPadding)) {
                    Text("Because this is ad hoc")
                    Text("The passed string was $uuidAsString")
                }
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
private fun NotesInspectScreenPreview() {
    val navController = rememberNavController()
    NotepadTheme {
        NotesInspectScreen(uuidAsString = "550e8400-e29b-41d4-a716-446655440000", navHostController = navController)
    }
}