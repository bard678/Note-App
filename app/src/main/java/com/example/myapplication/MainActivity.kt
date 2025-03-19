package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollSource.Companion.SideEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.auth.viewmodel.AuthViewModel
import com.example.myapplication.auth.presentation.ui.LoginNavHost
import com.example.myapplication.data.NoteDatabase
import com.example.myapplication.navigation.AppNavHost
import com.example.myapplication.presentation.models.LoginViewModel
import com.example.myapplication.presentation.models.RegisterViewModel
import com.example.myapplication.viewmodel.NoteViewModel
import com.example.myapplication.viewmodel.ViewModelFactory
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val database = remember { NoteDatabase.getDatabase(this) }
            val systemUiController = rememberSystemUiController()
            val navigationBarColor = androidx.compose.ui.graphics.Color.Transparent  // Replace with your desired color

            SideEffect {
                systemUiController.setNavigationBarColor(
                    color = navigationBarColor,
                    darkIcons = false // Set to true if you want dark icons on a light background
                )
            }
            val viewModelFactory = ViewModelFactory(database)
            val loginViewModel=LoginViewModel()
            val authViewModel= AuthViewModel()
            val registerViewModel=RegisterViewModel()
            val viewModel: NoteViewModel = viewModel(factory = viewModelFactory)
         // viewModel.addNote(title = "Java","Hello",0x002F50)
         //AppNavHost(viewModel)
          // FakeNoteReadScreen()
            AppNavHost(
                registerViewModel = registerViewModel,
                viewModel = viewModel,
                loginViewModel = loginViewModel
            )
          //  val googleAuthViewModel: GoogleAuthViewModel = viewModel() // ✅ Provide GoogleAuthViewModel

            //AppNavHost(noteViewModel)
            //GoogleSignInButton(viewModel = googleAuthViewModel) // ✅ Pass ViewModel
        }
    }
}


