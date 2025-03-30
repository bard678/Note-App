package com.example.myapplication.auth.presentation.ui.login


import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.auth.utils.validateEmail
import com.example.myapplication.auth.utils.validatePass
import com.example.myapplication.auth.viewmodel.AuthViewModel
import com.example.myapplication.auth.viewmodel.LoginViewModel
import com.example.myapplication.auth.viewmodel.UserViewModel


@Composable
fun LoginScreen(
    userViewModel: UserViewModel,
    loginViewModel: LoginViewModel= viewModel(),
    viewModel: AuthViewModel,
    navController: NavController,
    navHome: NavController
) {
    val context = LocalContext.current
    val loginState by loginViewModel.loginState.observeAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var loginMessage by remember { mutableStateOf("") }
    var errorPassword =loginViewModel.errorPassword
    var errorEmail =loginViewModel.errorEmail

    Box(
       modifier = Modifier
           .fillMaxSize()
           .background(
               brush = Brush.verticalGradient(
                   colors = listOf(Color(0xFF3949AB), Color(0xFF283593))
               )
           )

   ) {
       Button(

           colors = ButtonDefaults.buttonColors(
               containerColor = Color.White.copy(alpha =0.2f)
           ),
           modifier = Modifier
               .padding(24.dp,)
               .align(Alignment.TopStart),
           onClick = {
               // Perform your login logic here
               // After a successful login, navigate to the home screen
               navHome.navigate("home") {
                   popUpTo("auth") { inclusive = true }
               }
           }
       ) {
           Text("Skip")

       }
     Card (
         modifier = Modifier
             .fillMaxWidth()
             .padding(horizontal = 24.dp)
             .align(Alignment.Center)
         ,
         shape = RoundedCornerShape(24.dp),
         elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
         colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f))

     )  {
           Column(
               modifier = Modifier
                   .padding(24.dp)
                   .verticalScroll(rememberScrollState())
                   .animateContentSize(
                       animationSpec = tween(
                           durationMillis = 500,
                           easing = LinearOutSlowInEasing
                       )
                   ),
               verticalArrangement = Arrangement.spacedBy(4.dp),
               horizontalAlignment = Alignment.CenterHorizontally
           ) {
               // App Logo
               Image(
                   painter = painterResource(id = R.drawable.app_icon), // Replace with your actual logo
                   contentDescription = "Register Logo",
                   modifier = Modifier
                       .size(70.dp)
                       .clip(RoundedCornerShape(16.dp))
               )

               Spacer(modifier = Modifier.height(20.dp))

               // Title
               Text(
                   text = "Welcome Back!",
                   fontSize = 24.sp,
                   style = MaterialTheme.typography.headlineMedium,
                   color = Color(0xFF3949AB)
               )

               Spacer(modifier = Modifier.height(4.dp))

               // Email Input
               OutlinedTextField(
                    supportingText = {
                        loginViewModel.errorEmail?.let { Text(it) }
                                     },
                   isError = errorEmail!=null,
                   value = email,
                   onValueChange = {
                       email = it
                       loginViewModel.onEmailChanged(it)
                                   },
                   label = {
                       Row (
                           verticalAlignment = Alignment.CenterVertically,
                           horizontalArrangement = Arrangement.spacedBy(6.dp)
                       ){
                           Text("email:", fontSize = 15.sp, color = Color.Blue, fontWeight = FontWeight.W500)
                           Text("example@gmail.com",fontSize = 15.sp)
                       }
                   },
                   leadingIcon = {
                       Icon(
                           imageVector = Icons.Default.Email,
                           contentDescription = "Email"
                       )
                   },
                   keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                   shape = RoundedCornerShape(12.dp),
                   modifier = Modifier.fillMaxWidth()

               )




               // Password Input
               OutlinedTextField(
                   isError = loginViewModel.errorPassword!=null,
                   value = password,
                   supportingText = {
                       errorPassword?.let {
                           Text(it)
                       }
                   },
                   onValueChange = {
                       password = it
                       loginViewModel.onPassChanged(it)

                                   },
                   label = {
                       Text("Password", fontSize = 15.sp)

                   },
                   leadingIcon = {
                       Icon(
                           imageVector = Icons.Default.Lock,
                           contentDescription = "Lock"
                       )
                   },
                   visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                   trailingIcon = {
                       IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                           Icon(
                               modifier = Modifier.size(30.dp),
                               painter = painterResource(id = if (isPasswordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off),
                               contentDescription = "Toggle Password Visibility"
                           )
                       }
                   },
                   shape = RoundedCornerShape(12.dp),
                   modifier = Modifier.fillMaxWidth()
               )

               Spacer(modifier = Modifier.height(9.dp))

               // Login Button with Animation
               Button(
                   onClick = {
                   if(errorEmail!=null&&errorPassword!=null){
                       loginViewModel.login(
                           email = email,
                           password = password,
                           profilePicture = "",
                           context = context
                       )
                   }
                       else {
                           loginViewModel.onEmailChanged(email)
                           loginViewModel.onPassChanged(password)
                   }

                 },
                   modifier = Modifier
                       .fillMaxWidth()
                       .height(50.dp)
                       .clip(RoundedCornerShape(12.dp)),
                   colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3949AB))
               ) {
                HandleLoginState(
                    navLogin = navController,
                    navHome = navHome,
                    context = context,
                    loginState = loginState
                )
               }

               Spacer(modifier = Modifier.height(12.dp))

               // Login Status Message
//               if (loginMessage.isNotEmpty()) {
//                   Text(text = loginMessage, color = Color.Gray, fontSize = 14.sp)
//               }

               Spacer(modifier = Modifier.height(12.dp))

               // Register Navigation
               TextButton(onClick = {
                   /*TODO Navigate to Register Screen */
                   navController.navigate("add")
               }) {
                   Text("Don't have an account? Sign Up", color = MaterialTheme.colorScheme.primary)
               }
           }
       }
    }
}
