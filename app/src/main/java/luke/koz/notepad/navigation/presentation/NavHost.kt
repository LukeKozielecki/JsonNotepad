package luke.koz.notepad.navigation.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import luke.koz.notepad.notes_inspect.presentation.NotesInspectScreen
import luke.koz.notepad.notes_inspect.presentation.NotesInspectScreenRoute
import luke.koz.notepad.notes_list.presentation.NotesScreen
import luke.koz.notepad.notes_list.presentation.NotesScreenRoute

@Composable
fun ApplicationNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NotesScreenRoute
    ) {
        composable<NotesScreenRoute> {
            NotesScreen(navController = navController)
        }
        composable<NotesInspectScreenRoute> {
            val args = it.toRoute<NotesInspectScreenRoute>()
            NotesInspectScreen(modifier = modifier, uuidAsString = args.uuidAsString)
        }
    }
}