package com.example.myapplication.auth.presentation.ui.register

import android.content.Context
import android.widget.Toast
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.myapplication.auth.viewmodel.RegisterState

@Composable
fun HandleRegisterState(
    context: Context,
    registerState: RegisterState?
) {
    when (registerState) {
        is RegisterState.Loading -> CircularProgressIndicator()

        is RegisterState.Success -> {
            Text(registerState.message, color = Color.Green)
            Toast.makeText(context, registerState.message, Toast.LENGTH_LONG).show()
        }

        is RegisterState.Error -> {
            Text(registerState.message, color = Color.Red)
            Toast.makeText(context, registerState.message, Toast.LENGTH_LONG).show()
        }

        else -> {
            Text("Register", fontSize = 18.sp, color = Color.White)
        }
    }
}
