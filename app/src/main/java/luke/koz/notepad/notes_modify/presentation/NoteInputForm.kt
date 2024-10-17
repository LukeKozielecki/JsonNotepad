package luke.koz.notepad.notes_modify.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import luke.koz.notepad.R
import luke.koz.notepad.notes_list.model.NoteStatus
import luke.koz.notepad.notes_list.model.NotesDataClass
import luke.koz.notepad.notes_list.model.Priority
import luke.koz.notepad.ui.theme.NotepadTheme
import java.util.Currency
import java.util.Locale

@Composable
fun ItemInputForm(
    modifier: Modifier = Modifier,
    noteEntry : NotesDataClass
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        OutlinedTextField(
            value = noteEntry.title,
            onValueChange = {  },
            label = { Text("Item name*") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = true,
            singleLine = true
        )
    }
}

@Preview
@Composable
private fun ItemInputFormPreview() {
    val mockNote = NotesDataClass(
        title = stringResource(R.string.mock_note1_title),
        content = stringResource(R.string.mock_note1_content),
        tags = listOf("meeting", "project"),
        category = "Work",
        priority = Priority.HIGH,
        noteStatus = NoteStatus.DRAFT,
        lastModifiedBy = "Luke",
        reminderAt = System.currentTimeMillis() + 86400000 // 1 day later
    )
    NotepadTheme {
        ItemInputForm(Modifier, noteEntry = mockNote)
    }
}