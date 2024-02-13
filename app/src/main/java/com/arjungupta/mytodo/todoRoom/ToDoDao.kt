package com.arjungupta.mytodo.todoRoom

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.arjungupta.mytodo.models.ToDoData

@Dao
interface ToDoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertData(toDoData: ToDoData)

    @Delete
    suspend fun deleteData(toDoData: ToDoData)

    @Update
    suspend fun updateData(toDoData: ToDoData)

    @Query("SELECT * FROM todo")
    fun getData() : LiveData<List<ToDoData>>

}