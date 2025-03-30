package com.example.myapplication.auth.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.auth.data.LoginReqModel
import com.example.myapplication.auth.data.LoginState
import com.example.myapplication.auth.data.RegisterReqModel
import com.example.myapplication.auth.data.RetrofitInstance
import com.example.myapplication.auth.utils.validateEmail
import com.example.myapplication.auth.utils.validatePass
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import org.json.JSONObject

class LoginViewModel() : ViewModel() {

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState
    var errorPassword by mutableStateOf<String?>(null)
    var errorEmail by mutableStateOf<String?>(null)

    private var isRequestInProgress = false

    fun onEmailChanged(email:String){
        viewModelScope.launch {
            errorEmail= validateEmail(email)
        }
    }
   fun onPassChanged(password:String){
        viewModelScope.launch {
            errorPassword= validatePass(password)
        }
    }

    fun sendCodeVerification(code:String,context: Context){
        if (isRequestInProgress) {
            Log.d("LoginViewModel", "Request already in progress, ignoring duplicate request.")
            return
        }
        _loginState.postValue( LoginState.Loading)
        isRequestInProgress = true  // Mark request as in progress

        viewModelScope.launch (Dispatchers.IO){
            try{
                val response =RetrofitInstance(context).api.verifyEmail(code)

                if(response.isSuccessful){
                    _loginState.postValue(LoginState.Success(response.body()?.message?:"Code is sent"))
                }
                else {
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
                    _loginState.postValue(LoginState.Error(errorMessage))

                }
            }
            catch (e:Exception){
             Log.d("msg",e.message.toString())
            }
            finally {
                isRequestInProgress=false
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun login( email: String, password: String, profilePicture: String? = null,context: Context) {
        if (isRequestInProgress) {
            Log.d("LoginViewModel", "Request already in progress, ignoring duplicate request.")
            return
        }

        if (email.isBlank() || password.isBlank()) {
            _loginState.postValue(LoginState.Error("All fields are required",))
            return
        }

        _loginState.postValue( LoginState.Loading)
        isRequestInProgress = true  // Mark request as in progress

        viewModelScope.launch (Dispatchers.IO){
            try {
                Log.d("RegisterViewModel", "Sending request with Retrofit...")

                val response = RetrofitInstance(context).api.loginUser(
                    LoginReqModel(email = email, password = password)
                )

                if (response.isSuccessful) {
                    _loginState.postValue (LoginState.Success(response.body()?.message ?: "Registration successful"))
                    Log.e("Response"," ${ response.body() }")
                }
                else {
                    val errorBody=   response.errorBody()?.string() ?: "Unknown error occurred"

                    val errorMessage =

                        try{

                            JSONObject(errorBody ).getString("msg")

                        }
                        catch (
                            e:Exception
                        ){
                            "Unknown error occurred"
                        }
                    val isVerified =

                        try{

                            JSONObject(errorBody ).getInt("verified")

                        }
                        catch (
                            e:Exception
                        ){
                            1
                        }
                    println("Error Body is : ${errorMessage}")
                    println("Error Body is : ${errorBody}")


                    println("isVerified: $isVerified")


                    println(" isVerified : ${isVerified}")
                    Log.e("isVerified","${isVerified}")
                    Log.e("isVerified","${isVerified}")
                    Log.e("isVerified","${isVerified}")
                    Log.e("isVerified","${isVerified}")
                    _loginState.postValue( LoginState.Error(errorMessage,isVerified))

                }

            } catch (e: Exception) {
                Log.e("RegisterViewModel", "Error during registration", e)
                _loginState.postValue( LoginState.Error("Registration failed: ${e.localizedMessage}"))
            } finally {
                isRequestInProgress = false  // Reset flag after completion
            }
        }
    }
}


// Define different register states

