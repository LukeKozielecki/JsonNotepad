package luke.koz.notepad.notes_list.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable
import luke.koz.notepad.notes_inspect.presentation.NotesInspectScreenRoute
import luke.koz.notepad.notes_list.domain.NotesViewModel
import luke.koz.notepad.notes_list.model.NotesDataClass
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
//                    navController.navigate()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add note button"
                )
            }
        }
    ) { innerPadding ->
        val notesViewModel: NotesViewModel = viewModel()
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            notesViewModel.loadNotes(context, "notes.json")
        }
        val notesListResponse by notesViewModel.notes.observeAsState()

        //todo this could be remade into grid of item for flavour and make it look like other apps
        Column(modifier = Modifier.padding(innerPadding)) {
            notesListResponse?.let { response ->
                response.notesResponse.forEach { note ->
                    val onCardClickNavigate: (String) -> Unit = { uuidAsString ->
                        navController.navigate(NotesInspectScreenRoute(uuidAsString))
                    }
                    NotesSingleNote(
                        modifier = Modifier.fillMaxWidth().padding(8.dp),
                        note = note,
                        onClick = { onCardClickNavigate(note.id.toString()) }
                    )
                }
            } ?: run {
                Text("Loading notes...")
            }
        }
    }
}