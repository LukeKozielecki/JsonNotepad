package luke.koz.notepad.notes_list.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import luke.koz.notepad.notes_list.model.NotesDataClass
import java.util.Date

@Composable
fun NotesSingleNoteContent(
    modifier: Modifier = Modifier,
    note: NotesDataClass,
    truncatedNoteContent: String
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = note.title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = truncatedNoteContent,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        // Metadata row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Priority: ${note.priority}", style = MaterialTheme.typography.bodyMedium)

            Text(text = "Status: ${note.noteStatus}", style = MaterialTheme.typography.bodyMedium)
        }
        if (note.tags.isNotEmpty()) {
            Text(
                text = "Tags: ${note.tags.joinToString(", ")}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        if (note.category != null) {
            Text(
                text = "Category: ${note.category}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        if (note.lastModifiedBy != null) {
            Text(
                text = "Last Modified By: ${note.lastModifiedBy}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        if (note.reminderAt != null) {
            val reminderDate = Date(note.reminderAt).toString()
            Text(
                text = "Reminder: $reminderDate",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}