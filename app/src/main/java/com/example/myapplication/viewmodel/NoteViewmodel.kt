package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.CodeBlock
import com.example.myapplication.data.Note
import com.example.myapplication.data.NoteDao
import com.example.myapplication.data.NoteDatabase
import com.example.myapplication.data.NoteType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteViewModel(private  val noteDao: NoteDao):ViewModel() {
 val allNote=noteDao.getAllNotes()

    //TODO Get Notes By Type
    fun getNotesByType(type:NoteType):Flow<List<Note>>{

        return noteDao.getNotesByType(type)
     }
 var selectedNote: Note? = null
 var selectedNoteType: NoteType? = null
    fun addNote(title: String, content: String,color: Int,type: NoteType=NoteType.TASK_MANAGEMENT){
     println("Note Type is : ${type.name}")
      viewModelScope.launch {
          noteDao.insertNote(
              Note(
                  title = title,
                  content = content,
                  color = color,
                  type = type
              )
          )

      }

    }
    fun addCodeNote(title: String, content: String,color: Int,type: NoteType=NoteType.CODE,codeBlocks:List<CodeBlock>){
     println("Note Type is : ${type.name}")
      viewModelScope.launch {
          noteDao.insertNote(
              Note(
                  title = title,
                  content = content,
                  color = color,
                  type = type,
                  codeBlocks = codeBlocks
              )
          )

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
              )
          )

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

class ViewModelFactory(private  val database: NoteDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(database.noteDao()) as T
    }
}