package com.example.myapplication.auth.data

import com.example.myapplication.presentation.models.RegisterRequest
import com.example.myapplication.presentation.models.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


data class LoginReqModel (
    val email:String,val password:String
)

data class RegisterReqModel (
    val email:String,val password:String,val name:String=""
)

data class  RegisterResModel(
    val msg: String
)

sealed class  RegisterState(){
    data object  Loading:RegisterState()
    data class  Error(val msg:String):RegisterState()
    data class  Success(val msg:String):RegisterState()
}


interface RegisterApiService {
    @POST("register")
    suspend fun registerUser(@Body request: RegisterReqModel): Response<RegisterResModel>
}