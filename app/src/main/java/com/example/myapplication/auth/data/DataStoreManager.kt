package com.example.myapplication.auth.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

class SecureDataStoreServices(private  val context: Context) {

    private val TOKEN_KEY = stringPreferencesKey("token")
    private val ACCESS_TOKEN_KEY = stringPreferencesKey("accessToken")
    private val EMAIL_KEY = stringPreferencesKey("email")
    private val USER_KEY = stringPreferencesKey("id")

    suspend fun updateLoginInfo( token: String,email:String,id:String,accessToken: String) {
        context.dataStore.edit { prefs ->
            prefs[TOKEN_KEY] = token
            prefs[EMAIL_KEY] = email
            prefs[USER_KEY] = id
            prefs[ACCESS_TOKEN_KEY] = accessToken
        }
    }
     suspend fun updateAccessToken(accessToken:String){
         context.dataStore.edit {
             pre->
             pre[ACCESS_TOKEN_KEY]=accessToken
         }
     }
    suspend fun getNewAccessToken():String{
        val refreshTkn =context.dataStore.data.first()[TOKEN_KEY]?:""
       val response= RetrofitInstance(context).api.refreshToken(mapOf("refresh" to refreshTkn))
        if (response.isSuccessful){
            response.body()?.let { updateAccessToken(it.accessToken)
            }
        }
        return response.body()?.accessToken ?: ""
    }
     suspend fun getAccessToken():String{
         return context.dataStore.data.first()[ACCESS_TOKEN_KEY]?:""
     }

    suspend fun getLoginInfo(): LoginPrefModel {
        return LoginPrefModel(
            token = context.dataStore.data.first()[TOKEN_KEY]?:"",
            accessToken = context.dataStore.data.first()[ACCESS_TOKEN_KEY]?:"",
            email = context.dataStore.data.first()[EMAIL_KEY]?:"",
            id = context.dataStore.data.first()[USER_KEY]?:""
        )
    }
}

data class LoginPrefModel(
    val  token: String,
    val  accessToken: String,
    val  email: String,
    val  id: String
)