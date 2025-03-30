package com.example.myapplication.auth.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class TokenManager(context: Context):ViewModel(){
    val secureDataStoreServices=SecureDataStoreServices(context)

    fun updateAccessToken(accessToken:String){
     viewModelScope.launch {
         secureDataStoreServices.updateAccessToken(accessToken = accessToken)

     }
    }

    fun getAccessToken():String{
        var token=""
        viewModelScope.launch {
            token= secureDataStoreServices.getAccessToken()

        }
         return token
    }

}