package com.example.myapplication.presentation.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.auth.data.LoginState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _loginState.value = LoginState.Error(
            msg = "Email and Password cannot be empty",
            isVerified = 0
            )
            return
        }

        _loginState.value = LoginState.Loading

        // Simulate API request
        viewModelScope.launch {
          // Simulate network delay

            if (email == "test@example.com" && password == "password123") {
                _loginState.value = LoginState.Success("Login successful!")
            } else {
                _loginState.value = LoginState.Error("Invalid email or password")
            }
        }
    }
}
//sealed class LoginState{
//    object Loading:LoginState()
//
//    data class Success(val message:String):LoginState()
//    data class Error(val message:String):LoginState()
//}