package com.example.myapplication.auth.presentation.ui.register

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.auth.viewmodel.AuthViewModel
import com.example.myapplication.auth.viewmodel.RegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
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
            modifier = Modifier.padding( 24.dp, ).align(Alignment.TopStart),
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
                .align(Alignment.Center),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.95f))
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .animateContentSize(animationSpec = tween(durationMillis = 500, easing = LinearOutSlowInEasing)),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // App Logo
                Image(
                    painter = painterResource(id = R.drawable.app_icon), // Replace with your actual logo
                    contentDescription = "Register Logo",
                    modifier = Modifier
                        .size(100.dp)
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
                    value = fullName,
                    onValueChange = { fullName = it },
                    label = { Text("Full Name") },
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
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
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
                    onValueChange = { password = it },
                    label = { Text("Password") },
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
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password") },
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
                        registerViewModel2.register(
                            name = fullName,
                            email = email,
                            password = password,
                            profilePicture = ""
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3949AB))
                ) {
                    HandleRegisterState(
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
