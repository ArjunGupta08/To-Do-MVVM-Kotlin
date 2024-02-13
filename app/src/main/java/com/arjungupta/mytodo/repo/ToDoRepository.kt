package com.arjungupta.mytodo.repo

import android.content.Context
import androidx.lifecycle.LiveData
import com.arjungupta.mytodo.models.ToDoData
import com.arjungupta.mytodo.todoRoom.ToDoDatabase

class ToDoRepository {

    companion object {

        var instance : ToDoDatabase?=null

        private fun getDataBase(context: Context) : ToDoDatabase? {
            instance = ToDoDatabase.initialiseDatabase(context)
            return instance
        }

        suspend fun insertData(toDoData: ToDoData, context: Context) {
            instance = getDataBase(context)
            instance!!.getAccess().insertData(toDoData)
        }

        suspend fun updateData(context: Context, toDoData: ToDoData)  {
            instance = getDataBase(context)
            instance!!.getAccess().updateData(toDoData)
        }

        suspend fun deleteData(toDoData: ToDoData, context: Context) {
            instance = getDataBase(context)
            instance!!.getAccess().deleteData(toDoData)
        }

        fun getData(context: Context) : LiveData<List<ToDoData>> {
            instance = getDataBase(context)
            return instance!!.getAccess().getData()
        }

    }

}