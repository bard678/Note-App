package com.example.myapplication.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
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
    fun addNote(title: String, content: String, color: Int){
      viewModelScope.launch {
          noteDao.insertNote(
              Note(
                  title = title,
                  content = content,
                  color = color
              )
          )

      }

    }
    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteDao.deleteNote(note)
        }
    }
}

class ViewModelFactory(private  val database: NoteDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(database.noteDao()) as T
    }
}