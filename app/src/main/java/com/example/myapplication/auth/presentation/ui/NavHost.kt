package com.example.myapplication.auth.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.auth.presentation.ui.register.RegisterScreen
import com.example.myapplication.auth.viewmodel.AuthViewModel


@Composable

fun LoginNavHost(authViewModel: AuthViewModel = viewModel(),navHome: NavController){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "add") {
        composable("home") {
            LoginScreen( viewModel = authViewModel,navController = navController) }
        composable("add") {
            RegisterScreen(navHome=navHome,registerViewModel = authViewModel, navController = navController) }
      }
    }

