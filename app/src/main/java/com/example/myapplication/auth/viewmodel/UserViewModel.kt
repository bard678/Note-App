package com.example.myapplication.auth.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.auth.data.LoginPrefModel
import com.example.myapplication.auth.data.MediaPost
import com.example.myapplication.auth.data.MediaPostResponse
import com.example.myapplication.auth.data.RetrofitInstance
import com.example.myapplication.auth.data.SecureDataStoreServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject


class UserViewModel:ViewModel(){
    val contentLoadMsg= MutableStateFlow("")
    private val _email = MutableStateFlow("")
    val email:StateFlow<String> =_email
    var posts = MutableLiveData<List<MediaPost>>()
    var postsString = MutableLiveData<String>()
    private  val _token = MutableStateFlow("")
    val token:StateFlow<String> =_email

   private  val _secureData= MutableStateFlow<LoginPrefModel?>(null)


//   private  val _token = MutableStateFlow("")
//    val token:StateFlow<String> =_email

    fun  setEmail(email:String){
       _email.value=email
    }

    fun setToken(token:String,context: Context){
        _token.value=token

    }
    var secureData= MutableLiveData<LoginPrefModel?>()
    fun getLoginInfoFromStorage(context: Context){
       viewModelScope.launch {
           val loginInfo = SecureDataStoreServices(context).getLoginInfo()
           secureData.value=loginInfo

       }

    }
    fun loadData(context: Context, accessTok: String) {
        viewModelScope.launch(Dispatchers.IO) {
          runBlocking { Log.e("LOG","Start data...")  }
              println("HI")
            try {
                val response = RetrofitInstance(context).api.getProtectedData("Bearer $accessTok")
                Log.e("LOG","Fetching data...")

                withContext(Dispatchers.Main) { // Switch to main thread
                    Log.e("LOG","Fetching data...")

                }

                if (response.isSuccessful) {
                    withContext(Dispatchers.Main) {
                        Log.d("LOG",response.body().toString())
                        posts.postValue(response.body())
                        postsString.value=response.body()?.toString() ?: "Success"
                        Toast.makeText(
                            context,
                            response.body()?.toString() ?: "Success",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                  //  contentLoadMsg.value = response.body()?.data?.get(0)?.user_id ?: "Empty"
                } else {
                    val errorMessage = try {
                        val errorBody = response.errorBody()?.string() ?: "Unknown error occurred"
                        JSONObject(errorBody).getString("error")
                    } catch (e: Exception) {
                        "Unknown error occurred"
                    }

                    withContext(Dispatchers.Main) {

                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                    }

                    contentLoadMsg.value = errorMessage
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    e.message?.let { Log.e("Error", it) }
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}