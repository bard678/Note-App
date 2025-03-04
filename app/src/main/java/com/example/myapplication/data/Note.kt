package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis(),
    val color: Int,
    val alarmTime:Long?=null,
    val type:NoteType?=NoteType.CODE
)

enum class NoteType{
    CODE,TASK_MANAGEMENT,MIND_MAP
}
