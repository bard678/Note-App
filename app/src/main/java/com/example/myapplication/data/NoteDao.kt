package com.example.myapplication.data
import androidx.room.*


@Dao
interface NoteDao {
    @Query("SELECT * FROM note ORDER BY timestamp DESC")
    fun getAllNotes(): kotlinx.coroutines.flow.Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)
}
