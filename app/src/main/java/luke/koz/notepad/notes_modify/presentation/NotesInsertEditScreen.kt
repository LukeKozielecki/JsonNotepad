package luke.koz.notepad.notes_modify.presentation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.serialization.Serializable
import luke.koz.notepad.ui.theme.NotepadTheme
import luke.koz.notepad.utils.presentation.ScaffoldTopAppBar

@Serializable data class NotesInsertEditScreenRoute (val uuidAsString: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesInsertEditScreen(modifier: Modifier = Modifier, uuidAsString : String?) {
    Scaffold (
        topBar = {
            NoteInputEditTopAppBar(
                modifier = TODO(),
                onBackClick = TODO(),
                onUndoClick = TODO(),
                onRedoClick = TODO(),
                onConfirmClick = TODO()
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {},
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
    ) {
        NotesInsertEditBody(modifier = modifier.padding(it), uuidAsString = uuidAsString ?: null)
    }
}

@Preview
@Composable
private fun NotesInsertEditScreenPreview() {
    NotepadTheme {
        NotesInsertEditScreen(modifier = Modifier, uuidAsString = null)
    }
}