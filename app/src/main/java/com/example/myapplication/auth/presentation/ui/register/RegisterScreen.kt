package com.example.myapplication.auth.presentation.ui.register

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
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
import com.example.myapplication.auth.data.RegisterState
import com.example.myapplication.auth.viewmodel.AuthViewModel
import com.example.myapplication.auth.viewmodel.RegisterViewModel
import com.example.myapplication.auth.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    userViewModel: UserViewModel,

    navHome:NavController,
    navController: NavController,
    registerViewModel: AuthViewModel,
    registerViewModel2: RegisterViewModel = viewModel()
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val registerMessage by remember { mutableStateOf("") }
    val registerState by registerViewModel2.registerState.observeAsState()
    var errorPassword =registerViewModel2.errorPassword
    var errorEmail =registerViewModel2.errorEmail
    var errorName =registerViewModel2.errorName

    // Outer container with a vertical gradient background.
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
        // Card container for the form.
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .align(Alignment.Center)
                ,
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f))
        ) {
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

                // Title
                Text(
                    text = "Create an Account",
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF3949AB)
                )

                // Full Name Input
                OutlinedTextField(
                    supportingText = {
                        registerViewModel2.errorName?.let { Text(it) }
                    },
                    isError = errorName!=null,

                    value = fullName,
                    onValueChange = {
                        fullName = it
                        registerViewModel2.onNameChanged(it)
                    },
                  label = { Text("Full Name",fontSize = 15.sp) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Person",
                            tint = Color(0xFF757575)
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF3949AB),
                        unfocusedBorderColor = Color(0xFFBDBDBD),
                        focusedTextColor = Color.Black,
                        cursorColor = Color(0xFF3949AB)
                    )
                )

                // Email Input
                OutlinedTextField(
                    supportingText = {
                        registerViewModel2.errorEmail?.let { Text(it) }
                    },
                    isError = errorEmail!=null,

                    value = email,
                    onValueChange = {
                        email = it
                        registerViewModel2.onEmailChanged(it)
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
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),

                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email",
                            tint = Color(0xFF757575)
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF3949AB),
                        unfocusedBorderColor = Color(0xFFBDBDBD),
                        focusedTextColor = Color.Black,
                        cursorColor = Color(0xFF3949AB)
                    )
                )

                // Password Input
                OutlinedTextField(
                    value = password,
                    isError = errorPassword!=null,
                    supportingText = {
                        errorPassword?.let {
                            Text(it)
                        }
                    },
                    onValueChange = {
                        password = it
                        registerViewModel2.onPassChanged(it)
                    },
                    label = {
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ){
                            Text("password:", fontSize = 15.sp, color = Color.Blue, fontWeight = FontWeight.W500)
                            Text("Op21+1",fontSize = 15.sp)
                        }
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Lock",
                            tint = Color(0xFF757575)
                        )
                    },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                modifier = Modifier.size(30.dp),
                                painter = painterResource(
                                    id = if (isPasswordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off
                                ),
                                contentDescription = "Toggle Password Visibility"
                            )
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF3949AB),
                        unfocusedBorderColor = Color(0xFFBDBDBD),
                        focusedTextColor = Color.Black,
                        cursorColor = Color(0xFF3949AB)
                    )
                )

                // Confirm Password Input
                OutlinedTextField(
                    isError = errorPassword!=null,
                    supportingText = {
                        errorPassword?.let {
                            Text(it)
                        }
                    },
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password",fontSize = 15.sp) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Lock",
                            tint = Color(0xFF757575)
                        )
                    },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF3949AB),
                        unfocusedBorderColor = Color(0xFFBDBDBD),
                        focusedTextColor = Color.Black,
                        cursorColor = Color(0xFF3949AB)
                    )
                )

                // Register Button with Animation
                Button(
                    onClick = {
                   if(password==confirmPassword){
                       if(errorPassword!=null&&errorName!=null&&errorEmail!=null)
                       {
                           registerViewModel2.register(
                               context=context,
                               userViewModel=userViewModel,
                               name = fullName,
                               email = email,
                               password = password,
                               profilePicture = ""
                           )
                       }

                       else{
                           registerViewModel2.onNameChanged(fullName)
                           registerViewModel2.onPassChanged(password)
                           registerViewModel2.onEmailChanged(email)

                       }
                   }
                        else{
                            registerViewModel2.errorPassword="Password fields are not the same"
                   }



                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3949AB))
                ) {
                    HandleRegisterState(
                        userViewModel=userViewModel,
                        navController=navController,
                        context = context,
                        registerState = registerState
                    )
                }

                // Register Status Message
                if (registerMessage.isNotEmpty()) {
                    Text(
                        text = registerMessage,
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }

                // Login Navigation
                TextButton(onClick = {
                    navController.navigate("login")
                }) {
                    Text(
                        "Already have an account? Log in",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
