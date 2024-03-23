package com.arjungupta.mytodo.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
class ToDoData {

    @PrimaryKey(autoGenerate = true)
    var id : Int = 0

    var title : String = ""
    var description : String = ""
    var priority : String = ""
    var date : String = ""

    @Ignore
    constructor(title: String, description: String, priority: String, date: String) {
        this.title = title
        this.description = description
        this.priority = priority
        this.date = date
    }

    constructor(id:Int, title: String, description: String, priority: String, date: String) {
        this.id = id
        this.title = title
        this.description = description
        this.priority = priority
        this.date = date
    }

}
