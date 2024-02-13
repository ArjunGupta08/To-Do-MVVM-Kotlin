package com.arjungupta.mytodo.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.arjungupta.mytodo.models.ToDoData
import com.arjungupta.mytodo.repo.ToDoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(val context: Context) : ViewModel() {

    // Returns LiveData from Room
    fun getData() : LiveData<List<ToDoData>> {
        return ToDoRepository.getData(context)
    }

    // Insert ToDoData to Room
    fun insertData(toDoData: ToDoData) {
        CoroutineScope(Dispatchers.IO).launch {
            ToDoRepository.insertData(toDoData, context)
        }
    }

    // update ToDoData to Room
    fun updateData(toDoData: ToDoData) {
        CoroutineScope(Dispatchers.IO).launch {
            ToDoRepository.updateData(context, toDoData)
        }
    }

    // Delete ToDoData to Room
    fun deleteData(toDoData: ToDoData) {
        CoroutineScope(Dispatchers.IO).launch {
            ToDoRepository.deleteData(toDoData, context)
        }
    }


}