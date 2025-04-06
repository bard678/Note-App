package com.example.myapplication.navigation

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.auth.presentation.ui.LoginNavHost
import com.example.myapplication.auth.viewmodel.UserViewModel
import com.example.myapplication.navigation.screen.add.AddCodeNoteScreen
import com.example.myapplication.navigation.screen.HomeScreen
import com.example.myapplication.navigation.screen.NoteReadScreen
import com.example.myapplication.navigation.screen.NoteAddScreen
import com.example.myapplication.navigation.screen.NoteEditScreen
import com.example.myapplication.navigation.screen.add.MindMapNoteScreen
import com.example.myapplication.navigation.screen.add.TaskManagementScreen
import com.example.myapplication.navigation.screen.auth.LoginScreen
import com.example.myapplication.navigation.screen.edit.EditCodeNote
import com.example.myapplication.navigation.screen.view.ReadCodeScreen
import com.example.myapplication.presentation.models.LoginViewModel
import com.example.myapplication.presentation.models.RegisterViewModel
import com.example.myapplication.presentation.register.RegisterScreen
import com.example.myapplication.viewmodel.NoteViewModel
import kotlinx.coroutines.runBlocking

@Composable
//TODO Navigation  Host
fun AppNavHost(context:Context,userViewModel:UserViewModel,registerViewModel: RegisterViewModel,viewModel: NoteViewModel,loginViewModel: LoginViewModel) {
    val navController = rememberNavController()
    val isLoaded by userViewModel.isLoaded.collectAsState()

    userViewModel.getLoginInfoFromStorage(context)
    NavHost(
        navController = navController, startDestination = if (userViewModel.secureData.value==null) "auth" else "home") {
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
        composable("register") {
            RegisterScreen (
              registerViewModel = registerViewModel,
                navController = navController

        ) }
        composable("login") {
            com.example.myapplication.presentation.login.LoginScreen (
                loginViewModel,
           navController = navController,

        ) }
    }
}
