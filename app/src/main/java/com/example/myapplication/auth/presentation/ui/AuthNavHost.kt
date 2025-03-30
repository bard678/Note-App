package com.example.myapplication.auth.presentation.ui

import VerificationScreen
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.auth.presentation.ui.login.LoginScreen
import com.example.myapplication.auth.presentation.ui.register.RegisterScreen
import com.example.myapplication.auth.viewmodel.AuthViewModel
import com.example.myapplication.auth.viewmodel.UserViewModel


@Composable

fun LoginNavHost(userViewModel: UserViewModel,authViewModel: AuthViewModel = viewModel(),navHome: NavController){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "add") {
        composable("login") {
            LoginScreen(
                userViewModel=userViewModel,

                viewModel = authViewModel,
                navController = navController,navHome=navHome)
        }
        composable("add") {
            RegisterScreen(
                userViewModel=userViewModel,
                navHome = navHome,
                registerViewModel = authViewModel,
                navController = navController
            )
        }

         composable("verify") {
             VerificationScreen(
                 userViewModel=userViewModel,

                 navLogin =navController)
         }
            }
    }

