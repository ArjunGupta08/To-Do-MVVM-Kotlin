package com.arjungupta.mytodo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.arjungupta.mytodo.models.ToDoData

class ToDoAdapter(val context: Context, var list: List<ToDoData>) : RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> () {

    private var toDoListener :OnItemClick ?= null

    fun setOnItemClickListener(listner : OnItemClick) {
        toDoListener = listner
    }

    interface OnItemClick{
        fun onEditToDo(toDoData: ToDoData)
        fun onDeleteToDo(toDoData: ToDoData)
    }

    class ToDoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title : TextView = itemView.findViewById(R.id.title_txt)
        val desc : TextView = itemView.findViewById(R.id.txt_description)
        val dateTxt : TextView = itemView.findViewById(R.id.dateTxt)

        val edit : CardView = itemView.findViewById(R.id.editBtn)
        val delete : CardView = itemView.findViewById(R.id.deleteBtn)

        val priorityCard : CardView = itemView.findViewById(R.id.cardPri)
        val priorityTxt : TextView = itemView.findViewById(R.id.priorityTxt)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view : View = layoutInflater.inflate(R.layout.todo_layout, parent, false)
        return ToDoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val data = list[position]

        // Set Data to views
        holder.title.text = data.title
        holder.desc.text = data.description
        holder.dateTxt.text = data.date

        if (data.priority == "High") {
            holder.priorityCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.red))
        } else if (data.priority == "Medium") {
            holder.priorityCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light_red))
        } else if (data.priority == "Low") {
            holder.priorityCard.setCardBackgroundColor(ContextCompat.getColor(context, R.color.green))
        }

        holder.priorityTxt.text = data.priority

        holder.edit.setOnClickListener {
            toDoListener?.onEditToDo(data)
        }

        holder.delete.setOnClickListener {
            toDoListener?.onDeleteToDo(data)
        }

    }

    fun onSearch(filteredData : List<ToDoData>) {
        list = filteredData
        notifyDataSetChanged()
    }
}