package luke.koz.notepad.notes_list.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import luke.koz.notepad.R
import luke.koz.notepad.notes_list.domain.NotesViewModel
import luke.koz.notepad.notes_list.model.NoteStatus
import luke.koz.notepad.notes_list.model.NotesDataClass
import luke.koz.notepad.notes_list.model.Priority
import luke.koz.notepad.ui.theme.NotepadTheme

@Composable
fun NotesSingleNote(
    modifier: Modifier = Modifier,
    note: NotesDataClass,
    truncateNoteBoolean : Boolean = true,
    onClick: (() -> Unit)?,
) {
    val notesViewModel: NotesViewModel = viewModel()
    // TODO these should be passed by reference but this is future me problem
    notesViewModel.updateContent(note.content)
    val truncatedNoteContent by notesViewModel
        .truncateNoteContent(note)
        .collectAsState("")
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .let {
                if (onClick != null) {
                    it.clickable { onClick() }
                } else {
                    it
                }
            }
    ) {
        NotesSingleNoteContent(
            modifier = modifier,
            note = note,
            truncatedNoteContent = if(truncateNoteBoolean) {
                truncatedNoteContent
            } else {
                note.content
            }
        )
    }
}

@Preview
@Composable
private fun NotesSingleNotePreview() {
    val notesViewModel: NotesViewModel = viewModel()
    val mockNote = NotesDataClass(
        title = stringResource(R.string.mock_note1_title),
        content = stringResource(R.string.mock_note1_content),
        tags = mutableListOf("meeting", "project"),
        category = "Work",
        priority = Priority.HIGH,
        noteStatus = NoteStatus.DRAFT,
        lastModifiedBy = "Luke",
        reminderAt = System.currentTimeMillis() + 86400000 // 1 day later
    )
    NotepadTheme {
        NotesSingleNote(
            modifier = Modifier,
            note = mockNote,
            onClick = {}
        )
    }
}