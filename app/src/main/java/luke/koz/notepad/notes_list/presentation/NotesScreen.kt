package luke.koz.notepad.notes_list.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable
import luke.koz.notepad.notes_inspect.presentation.NotesInspectScreenRoute
import luke.koz.notepad.notes_list.domain.NotesViewModel
import luke.koz.notepad.utils.presentation.ScaffoldTopAppBar

@Serializable object NotesScreenRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(modifier: Modifier = Modifier, navController: NavHostController) {
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
        val notesViewModel: NotesViewModel = viewModel()
        val context = LocalContext.current
        val listOfNotes = notesViewModel.loadNotes(context, "notes.json").notesResponse
        val onCardClickNavigate : (String) -> Unit = { uuidAsString ->
            navController.navigate(NotesInspectScreenRoute(uuidAsString))
        }
        //todo this could be remade into grid of item for flavour and make it look like other apps
        Column(modifier.padding(innerPadding)) {
            listOfNotes.forEach() { note ->
                NotesSingleNote(modifier, note) { onCardClickNavigate(note.id.toString()) }
            }
        }
    }
}