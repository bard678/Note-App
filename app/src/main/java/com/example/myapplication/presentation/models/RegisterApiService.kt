package com.example.myapplication.presentation.models


import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

// Define the request model
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val profilePicture: String?
)

// Define the response model
data class RegisterResponse(
    val msg: String
)

interface RegisterApiService {
    @POST("register")
    suspend fun registerUser(@Body request: RegisterRequest): Response<RegisterResponse>
}
