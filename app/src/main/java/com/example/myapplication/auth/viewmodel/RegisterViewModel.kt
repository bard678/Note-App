package com.example.myapplication.auth.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.auth.data.RegisterReqModel
import com.example.myapplication.auth.data.RetrofitInstance
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import org.json.JSONObject

class RegisterViewModel : ViewModel() {

    private val _registerState = MutableLiveData<RegisterState>()
    val registerState: LiveData<RegisterState> = _registerState

    private var isRequestInProgress = false



    @SuppressLint("SuspiciousIndentation")
    fun register(name: String, email: String, password: String, profilePicture: String? = null) {
        if (isRequestInProgress) {
            Log.d("RegisterViewModel", "Request already in progress, ignoring duplicate request.")
            return
        }

        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            _registerState.postValue(RegisterState.Error("All fields are required"))
            return
        }

        _registerState.postValue( RegisterState.Loading)
        isRequestInProgress = true  // Mark request as in progress

        viewModelScope.launch (Dispatchers.IO){
            try {
                Log.d("RegisterViewModel", "Sending request with Retrofit...")

                val response = RetrofitInstance.api.registerUser(
                    RegisterReqModel(email = email, name = name, password = password)
                )

                if (response.isSuccessful) {
                    _registerState.postValue (RegisterState.Success(response.body()?.msg ?: "Registration successful"))

                } else {
                    val errorMessage =

                        try{
                         val errorBody=   response.errorBody()?.string() ?: "Unknown error occurred"

                            JSONObject(errorBody ).getString("msg")

                        }
                        catch (
                            e:Exception
                        ){
                            "Unknown error occurred"
                        }
                    _registerState.postValue( RegisterState.Error(errorMessage))

                }

            } catch (e: Exception) {
                Log.e("RegisterViewModel", "Error during registration", e)
                _registerState.postValue( RegisterState.Error("Registration failed: ${e.localizedMessage}"))
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
