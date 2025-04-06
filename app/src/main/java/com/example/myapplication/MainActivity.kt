package com.example.myapplication

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollSource.Companion.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.auth.data.SecureDataStoreServices
//import com.example.myapplication.auth.viewmodel.AuthViewModel
import com.example.myapplication.auth.presentation.ui.LoginNavHost
import com.example.myapplication.auth.viewmodel.UserViewModel
import com.example.myapplication.data.NoteDatabase
import com.example.myapplication.navigation.AppNavHost
import com.example.myapplication.presentation.models.LoginViewModel
import com.example.myapplication.presentation.models.RegisterViewModel
import com.example.myapplication.viewmodel.NoteViewModel
import com.example.myapplication.viewmodel.ViewModelFactory
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val database = remember { NoteDatabase.getDatabase(this) }
            val systemUiController = rememberSystemUiController()
            val navigationBarColor =
                androidx.compose.ui.graphics.Color.Transparent  // Replace with your desired color

            SideEffect {
                systemUiController.setNavigationBarColor(
                    color = navigationBarColor,
                    darkIcons = false // Set to true if you want dark icons on a light background
                )
            }
//            val serviceIntent = Intent(this, ForegroundService::class.java)
//            startForegroundService(serviceIntent)

            val viewModelFactory = ViewModelFactory(database)
            val loginViewModel = LoginViewModel()
            //val authViewModel= AuthViewModel()
            val registerViewModel = RegisterViewModel()
            val viewModel: NoteViewModel = viewModel(factory = viewModelFactory)


            // viewModel.addNote(title = "Java","Hello",0x002F50)
            //AppNavHost(viewModel)
            // FakeNoteReadScreen()
            val context = LocalContext.current


            val userViewModel: UserViewModel = viewModel()
            val isLoaded by userViewModel.isLoaded.collectAsState()
            val secure = userViewModel.secureData.observeAsState()
            userViewModel.getLoginInfoFromStorage(context)



            if (isLoaded) {
                AppNavHost(
                    registerViewModel = registerViewModel,
                    viewModel = viewModel,
                    loginViewModel = loginViewModel,
                    userViewModel = userViewModel,
                    context = this
                )

            }
        else {
                // Loading UI while waiting for suspend function to finish
              LoadingScreen()
            }

            //  val googleAuthViewModel: GoogleAuthViewModel = viewModel() // ✅ Provide GoogleAuthViewModel

            //AppNavHost(noteViewModel)
            //GoogleSignInButton(viewModel = googleAuthViewModel) // ✅ Pass ViewModel
        }
    }


}

@Composable
fun LoadingScreen() {
    val infiniteTransition = rememberInfiniteTransition()

    val scale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF101010)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Replace with your app logo if you want
            Icon(
                painter = painterResource(id = R.drawable.event_note),
                contentDescription = "Loading Logo",
                tint = androidx.compose.ui.graphics.Color.White.copy(alpha = alpha),
                modifier = Modifier
                    .size(80.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                    }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Loading...",
                color = androidx.compose.ui.graphics.Color.White.copy(alpha = alpha),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            CircularProgressIndicator(
                color = androidx.compose.ui.graphics.Color.White,
                strokeWidth = 4.dp,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

