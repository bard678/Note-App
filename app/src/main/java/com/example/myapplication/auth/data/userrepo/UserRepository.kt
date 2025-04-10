package com.example.myapplication.auth.data.userrepo

import android.content.Context
import android.util.Log
import com.example.myapplication.auth.data.LoginPrefModel
import com.example.myapplication.auth.data.MediaPost
import com.example.myapplication.auth.data.RetrofitInstance
import com.example.myapplication.auth.data.SecureDataStoreServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class UserRepository (
    private  val context: Context
) {

    suspend fun getLoginInfo(): LoginPrefModel? {

        return SecureDataStoreServices(context).getLoginInfo()
    }

    suspend fun logout(){
        SecureDataStoreServices(context).updateLoginInfo(
            token = "",
            email = "",
            id = "",
            accessToken = ""
        )
    }

    suspend fun getProtectedData(token: String): List<MediaPost>? {
        val response = RetrofitInstance(context).api.getProtectedData("Bearer $token")
        Log.e("LOG", "Fetching data...")

        withContext(Dispatchers.Main) { // Switch to main thread
            Log.e("LOG", "Fetching data...")

        }

        if (response.isSuccessful) {
            withContext(Dispatchers.Main) {
                response.body()
            }

            //  contentLoadMsg.value = response.body()?.data?.get(0)?.user_id ?: "Empty"
        } else {

            val errorBody = response.errorBody()?.string() ?: "Unknown error occurred"

            val errorMessage = try {
                JSONObject(errorBody).getString("error")

            } catch (e: Exception) {
                "Unknown error"
            }
            throw Exception(errorMessage)

        }
        return null
    }

}

