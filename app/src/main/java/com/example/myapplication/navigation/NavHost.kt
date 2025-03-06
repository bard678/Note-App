package com.example.myapplication.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navigation.screen.add.AddCodeNoteScreen
import com.example.myapplication.navigation.screen.HomeScreen
import com.example.myapplication.navigation.screen.NoteReadScreen
import com.example.myapplication.navigation.screen.NoteAddScreen
import com.example.myapplication.navigation.screen.NoteEditScreen
import com.example.myapplication.navigation.screen.add.MindMapNoteScreen
import com.example.myapplication.navigation.screen.add.TaskManagementScreen
import com.example.myapplication.navigation.screen.edit.EditCodeNote
import com.example.myapplication.navigation.screen.view.ReadCodeScreen
import com.example.myapplication.viewmodel.NoteViewModel

@Composable
//TODO Navigation  Host
fun AppNavHost(viewModel: NoteViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen( viewModel = viewModel,navController = navController) }
        composable("add") { NoteAddScreen(viewModel = viewModel, navController = navController) }
        composable("read") { NoteReadScreen(viewModel = viewModel, navController = navController) }
        composable("edit") { NoteEditScreen(viewModel = viewModel, navController = navController) }
        composable("addCode") { AddCodeNoteScreen(
            viewModel = viewModel, navController = navController,
            onSave = {}
        ) }
        composable("addTask") { TaskManagementScreen(
            viewModel = viewModel, navController = navController,
           onSaveNote = {}
        ) }
        composable("readCodeNote") { ReadCodeScreen(
            viewModel = viewModel, navController = navController,

        ) }
        composable("addMindMap") { MindMapNoteScreen (
            viewModel = viewModel, navController = navController,
           onSave = {}
        ) }
        composable("editCodeNote") { EditCodeNote (
            viewModel = viewModel, navController = navController,

        ) }
    }
}
