package com.arjungupta.mytodo.todoRoom

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arjungupta.mytodo.models.ToDoData

@Database(entities = [ToDoData::class], version = 1)
abstract class ToDoDatabase : RoomDatabase(){

    abstract fun getAccess() : ToDoDao

    companion object {

        @Volatile
        var INSTANCE : ToDoDatabase ? = null

        private const val DATABASE_NAME = "to_do_database"

        fun initialiseDatabase(context: Context) : ToDoDatabase? {

            if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context, ToDoDatabase::class.java, DATABASE_NAME).build()

            }

            return INSTANCE
        }

    }

}