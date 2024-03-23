package com.arjungupta.mytodo.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arjungupta.mytodo.models.ToDoData
import com.arjungupta.mytodo.repo.ToDoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val context: Context) : ViewModel() {

    private val toDoRepository : ToDoRepository
        get() = ToDoRepository()

    // Returns LiveData from Room
    fun getData() : LiveData<List<ToDoData>>? {
        return toDoRepository.getData(context)
    }

    // Insert ToDoData to Room
    fun insertData(toDoData: ToDoData) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                toDoRepository.insertData(toDoData, context)
            }
        }
    }

    // update ToDoData to Room
    fun updateData(toDoData: ToDoData) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                toDoRepository.updateData(context, toDoData)
            }
        }
    }

    // Delete ToDoData to Room
    fun deleteData(toDoData: ToDoData) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                toDoRepository.deleteData(toDoData, context)
            }
        }
    }


}