package com.example.myapplication.RoomDb.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.RoomDb.CodeBlock
import com.example.myapplication.RoomDb.Note
import com.example.myapplication.RoomDb.NoteDao
import com.example.myapplication.RoomDb.NoteDatabase
import com.example.myapplication.RoomDb.NoteType
import com.example.myapplication.auth.data.LoginPrefModel
import com.example.myapplication.auth.data.RetrofitInstance
import com.example.myapplication.auth.data.SecureLoginDataStoreServices
import com.example.myapplication.auth.data.SqlNote
import com.example.myapplication.auth.data.userrepo.UserRepository
import com.example.myapplication.auth.data.toSqlNote
import com.example.myapplication.auth.domain.usecase.user.GetLoginInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class NoteViewModel(private  val noteDao: NoteDao,private val context: Context, private val userRepo: UserRepository):ViewModel() {
    @SuppressLint("StaticFieldLeak")
   // val context = getApplication<Application>().applicationContext!!
    val allNote=noteDao.getAllNotes()
    val loginInfoUseCase= GetLoginInfoUseCase(userRepo)
    private val _loginInfo = MutableStateFlow<LoginPrefModel?>(null)
    val loginInfo: StateFlow<LoginPrefModel?>  = _loginInfo


     init{
        setLoginInfo()
    }
    fun setLoginInfo() {
        viewModelScope.launch {
            try {
                val info = loginInfoUseCase() // Invoking the use case
                _loginInfo.value = info // Updating StateFlow
            } catch (e: Exception) {
                _loginInfo.value = null // Handle error
            }
        }
    }
    //TODO Get Notes By Type
    fun getNotesByType(type:NoteType):Flow<List<Note>>{
        return noteDao.getNotesByType(type)
     }
 var selectedNote: Note? = null
 var selectedNoteType: NoteType? = null

   suspend fun trySyncNotes(){

       if (loginInfo.value != null) {
           try {


               withContext(Dispatchers.Main) {
                   Toast.makeText(context, "Syncing now ...", Toast.LENGTH_LONG).show()

               }

               val userId = SecureLoginDataStoreServices(context).getUserID()
               val unSyncedNotes = noteDao.getUnsyncedNotes()

               val sqlNotes: List<SqlNote> = unSyncedNotes.map { note ->
                   note.toSqlNote(userId)
               }
               val result = RetrofitInstance(context).api.pushNotes(
                   sqlNotes
               )


               if (result.isSuccessful) {

                   unSyncedNotes.forEach { note ->
                       noteDao.updateNote(note.copy(isSynced = true))
                   }

                   val message = result.body()?.message
                   val notesCount = result.body()?.affectedRows
                   withContext(Dispatchers.Main) {

                       Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                   }
                   if (message != null) {
                       Log.d("success", message)
                       Log.d("success", message)
                       Log.d("success", message)
                       Log.d("success", message)
                   }
               } else {
                   val errorBody = result.errorBody()?.string() ?: "Unknown error occurred"
                   val errorMessage = try {
                       JSONObject(errorBody).getString("msg")
                   } catch (e: Exception) {
                       "Unknown error occurred"
                   }
                   withContext(Dispatchers.Main) {

                       Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                   }
                   Log.e("success", errorMessage)
                   Log.e("success", errorMessage)
                   Log.e("success", errorMessage)
                   Log.e("success", errorMessage)

               }

           } catch (e: Exception) {
               withContext(Dispatchers.Main) {
                   Toast.makeText(
                       context,
                       "Syncing your notes failed, Error: ${e.message}",
                       Toast.LENGTH_LONG
                   ).show()
               }
           }
       }
       else{
           withContext(Dispatchers.Main) {
               Toast.makeText(
                   context,
                   "You are not logged in yet. ",
                   Toast.LENGTH_LONG
               ).show()
           }

       }

    }

    fun addNote(title: String, content: String,color: Int,type: NoteType=NoteType.TASK_MANAGEMENT){
     println("Note Type is : ${type.name}")
      viewModelScope.launch {
          noteDao.insertNote(
              Note(
                  title = title,
                  content = content,
                  color = color,
                  type = type
              ).copy(isSynced = false)


          )
          trySyncNotes()
      }

    }

    fun addCodeNote(title: String, content: String,color: Int,type: NoteType=NoteType.CODE,codeBlocks:List<CodeBlock>){
     println("Note Type is : ${type.name}")
      viewModelScope.launch(Dispatchers.IO) {
          noteDao.insertNote(

              Note(
                  title = title,
                  content = content,
                  color = color,
                  type = type,
                  isSynced = false,
                  codeBlocks = codeBlocks
              )
          )

              trySyncNotes()

      }

    }
   fun updateNote(id: Int,title: String, content: String,color: Int,type: NoteType=NoteType.TASK_MANAGEMENT){
     println("Note Type is : ${type.name} ${id}")

      viewModelScope.launch {
          noteDao.updateNote(
              Note(
                  id =id ,
                  title = title,
                  content = content,
                  color = color,
                  type = type
              ).copy(isSynced = false)
          )
          trySyncNotes()

      }

    }

    fun deleteNote(note: Note?) {
        viewModelScope.launch {
            if (note != null) {
                noteDao.deleteNote(note)
            }
        }
    }

    fun updateCodeNote(note: Note?) {
        viewModelScope.launch {
            if (note != null) {
                noteDao.updateCodeNote(note)
            }
        }
    }
}

class ViewModelFactory(private  val database: NoteDatabase, private  val context: Context, private val userRepo: UserRepository,) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(database.noteDao(),context,userRepo) as T
    }
}