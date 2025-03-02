package com.example.myapplication.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.navigation.screen.HomeScreen
import com.example.myapplication.navigation.screen.NoteReadScreen
import com.example.myapplication.navigation.screen.NoteAddScreen
import com.example.myapplication.viewmodel.NoteViewModel

@Composable
//TODO Navigation  Host
fun AppNavHost(viewModel: NoteViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") { HomeScreen( viewModel = viewModel,navController = navController) }
        composable("add") { NoteAddScreen(viewModel = viewModel, navController = navController) }
        composable("read") { NoteReadScreen(viewModel = viewModel, navController = navController) }
    }
}
