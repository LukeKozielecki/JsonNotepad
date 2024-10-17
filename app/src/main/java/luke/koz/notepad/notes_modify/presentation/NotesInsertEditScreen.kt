package luke.koz.notepad.notes_modify.presentation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import luke.koz.notepad.notes_list.model.NotesDataClass
import luke.koz.notepad.notes_modify.domain.NoteInputEditViewModel
import luke.koz.notepad.ui.theme.NotepadTheme

@Serializable data class NotesInsertEditScreenRoute (val uuidAsString: String)

@Composable
fun NotesInsertEditScreen(modifier: Modifier = Modifier, uuidAsString : String?, navHostController: NavHostController) {
    var uuidFetchedAsString by remember { mutableStateOf("") }
    var fetchNewNote by remember { mutableStateOf<NotesDataClass?>(null) }
    val noteInputEditViewModel = NoteInputEditViewModel()
    val context = LocalContext.current
    Scaffold (
        topBar = {
            NoteInputEditTopAppBar(
                modifier = Modifier,
                onBackClick = {navHostController.popBackStack()},
                onUndoClick = {},
                onRedoClick = {},
                onConfirmClick = {}
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {noteInputEditViewModel.saveNotes(context,"notes.json",uuidFetchedAsString, fetchNewNote)},
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
                Text(uuidFetchedAsString)
            }
        }
    ) {
        //todo need to introduce focus change when selected text where the keyboard is to appear, atm need to scroll manually
        NotesInsertEditBody(
            modifier = modifier.padding(it),
            uuidAsString = uuidAsString ?: null,
            uuidFetchMethod = { uuidFetched -> uuidFetchedAsString = uuidFetched },
            fetchNewNote = {noteFetched -> fetchNewNote = noteFetched}
        )
    }
}

@Preview
@Composable
private fun NotesInsertEditScreenPreview() {
    val navController = rememberNavController()
    NotepadTheme {
        NotesInsertEditScreen(modifier = Modifier, uuidAsString = null, navController )
    }
}