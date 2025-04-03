package com.example.myapplication.auth.presentation.ui

import VerificationScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.auth.presentation.ui.login.LoginScreen
import com.example.myapplication.auth.presentation.ui.login.SmallLoginScreen
import com.example.myapplication.auth.presentation.ui.register.RegisterScreen
import com.example.myapplication.auth.presentation.ui.register.SmallRegisterScreen
//import com.example.myapplication.auth.viewmodel.AuthViewModel
import com.example.myapplication.auth.viewmodel.UserViewModel


@Composable

fun LoginNavHost(userViewModel: UserViewModel,navHome: NavController){
    val navController = rememberNavController()
    val config = LocalConfiguration.current

    val screenWidth = config.screenWidthDp
    val height = config.screenHeightDp
    NavHost(navController = navController, startDestination =when {
        screenWidth <390 -> "addsmall"
        else ->"add"
    }
    ) {
        composable("login") {
            LoginScreen(


                navController = navController,
                navHome=navHome
            )
        }
        composable("loginsmall") {
            SmallLoginScreen(


                navController = navController,
                navHome=navHome
            )
        }
        composable("add") {
            RegisterScreen(
                userViewModel=userViewModel,
                navHome = navHome,
//                registerViewModel = authViewModel,
                navController = navController
            )
        }
     composable("addsmall") {
            SmallRegisterScreen(
                userViewModel=userViewModel,
                navHome = navHome,
//                registerViewModel = authViewModel,
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

