package luke.koz.notepad.notes_modify.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import luke.koz.notepad.R
import luke.koz.notepad.notes_list.model.NoteStatus
import luke.koz.notepad.notes_list.model.NotesDataClass
import luke.koz.notepad.notes_list.model.Priority
import luke.koz.notepad.ui.theme.NotepadTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NoteInputForm(
    modifier: Modifier = Modifier,
    noteEntry : NotesDataClass,
    fetchNewNote: (NotesDataClass) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        val localNoteEntry = remember { mutableStateOf(noteEntry.copy()) }
        fetchNewNote(localNoteEntry.value)

        val currentDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        val characterCount = localNoteEntry.value.content.length

        //todo cut it into tiny little pieces
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            LazyRow (Modifier.padding(horizontal = 16.dp)){
                items(localNoteEntry.value.tags.size) { index ->
                    TagInputChip(
                        text = localNoteEntry.value.tags[index],
                        modifier = Modifier.let {
                            if (index < localNoteEntry.value.tags.size) it.padding(
                                end = 18.dp
                            ) else it.padding(0.dp)
                        }
                    ) {
                        //todo this should be moved to viewmodel oh well
                        localNoteEntry.value.tags.filter { tag -> tag == localNoteEntry.value.tags[index] }
                    }
                }
            }
            TextField(
                value = localNoteEntry.value.title,
                onValueChange = {
                    localNoteEntry.value = localNoteEntry.value.copy(title = it)
                },
                placeholder = {
                    Text(text = "Title", color = Color.Gray, fontSize = 24.sp)
                },
                textStyle = TextStyle(fontSize = 24.sp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                keyboardOptions = KeyboardOptions.Default
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$currentDate | $characterCount characters",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = localNoteEntry.value.content,
                onValueChange = {
                    localNoteEntry.value = localNoteEntry.value.copy(content = it)
                },
                placeholder = {
                    Text(text = "Start typing...", color = Color.Gray)
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent),
                keyboardOptions = KeyboardOptions.Default
            )
        }
    }
}

@Preview (showBackground = true)
@Composable
private fun NoteInputFormPreview() {
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
        NoteInputForm(Modifier, noteEntry = mockNote, fetchNewNote = { })
    }
}