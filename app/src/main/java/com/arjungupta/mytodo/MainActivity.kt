package com.arjungupta.mytodo

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arjungupta.mytodo.databinding.ActivityMainBinding
import com.arjungupta.mytodo.models.ToDoData
import com.arjungupta.mytodo.viewModel.MainViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity(), ToDoAdapter.OnItemClick {

    private var priority = "High"
    private var selectedDate = ""

    private lateinit var binding : ActivityMainBinding

    lateinit var toDoAdapter: ToDoAdapter

    private var mainViewModel: MainViewModel?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerToDo.layoutManager = LinearLayoutManager(this)


        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.addBtn.setOnClickListener {
            openAddDialog()
        }

        mainViewModel?.getData(this)?.observe(this) {
            toDoAdapter = ToDoAdapter(this, it)
            binding.recyclerToDo.adapter = toDoAdapter
            toDoAdapter.setOnItemClickListener(this)
            toDoAdapter.notifyItemChanged(0)

            binding.searchET.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val filterData = it.filter { toDoData ->
                        toDoData.title.contains(s.toString(), ignoreCase = true)
                    }
                    toDoAdapter.onSearch(filterData)
                }

                override fun afterTextChanged(s: Editable?) {

                }
            })

        }

    }

    private fun openAddDialog(isUpdating : Boolean ?= false, toDoData: ToDoData ?= null) {

        val dialog = Dialog(this)
        dialog.setContentView(R.layout.add_dialoge)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val titleET : TextInputEditText = dialog.findViewById(R.id.et_title)
        val titleTIL : TextInputLayout = dialog.findViewById(R.id.til_title)

        val descET : TextInputEditText = dialog.findViewById(R.id.et_desc)
        val descTIL : TextInputLayout = dialog.findViewById(R.id.til_desc)


        val high : CardView = dialog.findViewById(R.id.high)
        val medium : CardView = dialog.findViewById(R.id.medium)
        val low : CardView = dialog.findViewById(R.id.low)

        val dateTxt : TextView = dialog.findViewById(R.id.dateTxt)
        val selectDate : CardView = dialog.findViewById(R.id.selectDate)

        selectDate.setOnClickListener {
            openDateDialog(dateTxt)
        }

        high.setOnClickListener {
            priority = "High"
            high.setCardBackgroundColor(ContextCompat.getColor(this, R.color.red))
            medium.setCardBackgroundColor(ContextCompat.getColor(this, R.color.black))
            low.setCardBackgroundColor(ContextCompat.getColor(this, R.color.black))
        }

        medium.setOnClickListener {
            priority = "Medium"
            high.setCardBackgroundColor(ContextCompat.getColor(this, R.color.black))
            medium.setCardBackgroundColor(ContextCompat.getColor(this, R.color.light_red))
            low.setCardBackgroundColor(ContextCompat.getColor(this, R.color.black))
        }
        low.setOnClickListener {
            priority = "Low"
            high.setCardBackgroundColor(ContextCompat.getColor(this, R.color.black))
            medium.setCardBackgroundColor(ContextCompat.getColor(this, R.color.black))
            low.setCardBackgroundColor(ContextCompat.getColor(this, R.color.green))
        }

        val addBtn : Button = dialog.findViewById(R.id.addBtn)

        if (isUpdating!!) {
            titleET.setText(toDoData!!.title)
            descET.setText(toDoData.description)
            priority = toDoData.priority
            selectedDate = toDoData.date
            dateTxt.text = toDoData.date
        }

        addBtn.setOnClickListener {

            if (titleET.text!!.isEmpty()) {
                titleTIL.error = "Please input Title"
            } else if (descET.text!!.isEmpty()) {
                descTIL.error = "Please input Description"
                titleTIL.isEnabled = false
            } else if (selectedDate == "") {
                Toast.makeText(applicationContext, "PLease Select Date", Toast.LENGTH_SHORT).show()
            } else {
                titleTIL.isEnabled = false
                descTIL.isEnabled = false

                if (isUpdating) {
                    updateData(toDoData!!.id, titleET.text.toString(), descET.text.toString())
                } else {
                    insertData(titleET.text.toString(), descET.text.toString())
                }
                dialog.dismiss()
            }
        }
        dialog.show()
    }


    private fun updateData(id : Int, title : String, description : String) {
        CoroutineScope(IO).launch {
            mainViewModel!!.updateData(
                ToDoData(id, title, description, priority, selectedDate), this@MainActivity
            )
        }
    }

    private fun insertData(title : String, description : String) {
        CoroutineScope(IO).launch {
            mainViewModel!!.insertData(
                ToDoData(0, title, description, priority, selectedDate), this@MainActivity
            )
        }
    }

    override fun onEditToDo(toDoData: ToDoData) {
        openAddDialog(true, toDoData)
    }

    override fun onDeleteToDo(toDoData: ToDoData) {
        mainViewModel?.deleteData(toDoData, this)
    }

    private fun openDateDialog(dateTxt : TextView)  {
        val builder = MaterialDatePicker.Builder.datePicker()
        builder.setTitleText("Select a Date")

        builder.setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)

        val datePickerDialog = builder.build()

        datePickerDialog.addOnPositiveButtonClickListener { selectDate ->
            selectedDate = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date(selectDate))
            dateTxt.text = selectedDate
        }

        datePickerDialog.show(supportFragmentManager, "DatePickerDialog")

    }

}