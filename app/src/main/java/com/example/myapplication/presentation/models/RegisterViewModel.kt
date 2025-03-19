package com.example.myapplication.presentation.models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _registerState = MutableLiveData<RegisterState>()
    val registerState: LiveData<RegisterState> = _registerState

    private var isRequestInProgress = false

    fun register(name: String, email: String, password: String, profilePicture: String? = null) {
        if (isRequestInProgress) {
            Log.d("RegisterViewModel", "Request already in progress, ignoring duplicate request.")
            return
        }

        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            _registerState.value = RegisterState.Error("All fields are required")
            return
        }

        _registerState.value = RegisterState.Loading
        isRequestInProgress = true  // Mark request as in progress

        viewModelScope.launch {
            try {
                Log.d("RegisterViewModel", "Sending request with Retrofit...")

                val response = RetrofitInstance.api.registerUser(
                    RegisterRequest(name, email, password, profilePicture)
                )

                if (response.isSuccessful) {
                    _registerState.value = RegisterState.Success(response.body()?.msg ?: "Registration successful")
                } else {
                    val errorMessage = response.errorBody()?.string() ?: "Unknown error occurred"
                    _registerState.value = RegisterState.Error(errorMessage)
                }

            } catch (e: Exception) {
                Log.e("RegisterViewModel", "Error during registration", e)
                _registerState.value = RegisterState.Error("Registration failed: ${e.localizedMessage}")
            } finally {
                isRequestInProgress = false  // Reset flag after completion
            }
        }
    }
}


// Define different register states
sealed class RegisterState {
    object Loading : RegisterState()
    data class Success(val message: String) : RegisterState()
    data class Error(val message: String) : RegisterState()
}
