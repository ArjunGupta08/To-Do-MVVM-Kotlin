package com.arjungupta.mytodo.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class ToDoData(

    @PrimaryKey(autoGenerate = true)
    val id : Int,

    val title : String,
    val description : String,
    val priority : String,
    val date : String,
)
