package com.example.myapplication.data


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [Note::class],
    exportSchema = false
    , version =5
)
abstract class NoteDatabase :RoomDatabase(){
    abstract fun noteDao():NoteDao

    companion object{
        @Volatile
        private  var INSTANCE :NoteDatabase?=null

        fun getDatabase(context: Context):NoteDatabase{
            return INSTANCE ?: synchronized(this){
                var instance =Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java
                    ,"note_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE=instance
                instance
            }
        }
    }
}