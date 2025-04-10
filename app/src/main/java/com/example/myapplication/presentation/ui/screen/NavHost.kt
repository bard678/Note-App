package com.example.myapplication.presentation.ui.screen

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.auth.presentation.ui.LoginNavHost
import com.example.myapplication.auth.viewmodel.UserViewModel
import com.example.myapplication.presentation.ui.screen.add.AddCodeNoteScreen
import com.example.myapplication.presentation.ui.screen.add.MindMapNoteScreen
import com.example.myapplication.presentation.ui.screen.add.TaskManagementScreen
import com.example.myapplication.presentation.ui.screen.edit.EditCodeNote
import com.example.myapplication.presentation.ui.screen.view.ReadCodeScreen
import com.example.myapplication.RoomDb.viewmodel.NoteViewModel

@Composable
//TODO Navigation  Host
fun AppNavHost(context:Context, userViewModel:UserViewModel,  viewModel: NoteViewModel) {
    val navController = rememberNavController()

    val isLogin by userViewModel.isLogin.collectAsState()
    NavHost(
        navController = navController, startDestination = if (isLogin) "home" else "auth") {
       composable("auth"){


               LoginNavHost(
                   userViewModel = userViewModel,
                   navHome = navController
               )

       }
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
