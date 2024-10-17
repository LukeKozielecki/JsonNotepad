package luke.koz.notepad.notes_modify.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import luke.koz.notepad.R
import luke.koz.notepad.ui.theme.NotepadTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteInputEditTopAppBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    onUndoClick: () -> Unit,
    onRedoClick: () -> Unit,
    onConfirmClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {},
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                )
            }
        },
        actions = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 8.dp) // Adjust padding as needed
            ) {
                IconButton(onClick = onUndoClick) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_undo_24),
                        contentDescription = "Undo",
                    )
                }
                IconButton(onClick = onRedoClick) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_undo_24),
                        contentDescription = "Redo",
                        modifier = Modifier.graphicsLayer(scaleX = -1f)
                    )
                }
                IconButton(onClick = onConfirmClick) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Confirm and save"
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun NoteInputEditTopAppBarPreview() {
    NotepadTheme {
        NoteInputEditTopAppBar(
            modifier = Modifier,
            onBackClick = {},
            onUndoClick = {},
            onRedoClick = {},
            onConfirmClick = {}
        )
    }
}