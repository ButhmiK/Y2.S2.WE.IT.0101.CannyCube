package com.example.c_login.income

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.c_login.DataBaseHelper
import com.example.c_login.R

class IncomeActivity : AppCompatActivity() {
    private lateinit var edId: EditText
    private lateinit var edName: EditText
    private lateinit var edEmail: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button



    private lateinit var sqliteHelper: DataBaseHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: StudentAdapter? = null
    private var std: StudentModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_income)

        initView()
        initRecyclerView()
        sqliteHelper = DataBaseHelper(this)

        btnAdd.setOnClickListener{ addStudent() }
        btnView.setOnClickListener{ getStudent() }
        btnUpdate.setOnClickListener{ updateStudent() }


        adapter?.setOnClickItem {
            Toast.makeText(this,it.name, Toast.LENGTH_SHORT).show()

            edId.setText(it.id)
            edName.setText(it.name)
            edEmail.setText(it.email)
            std = it
        }

        adapter?.setOnClickDelete {
            deleteStudent(it.id)
        }
    }

    private fun getStudent(){
        val stdList = sqliteHelper.getAllStudent()
        Log.e("pppp","${stdList.size}")
        adapter?.addItems(stdList)
    }



    private fun addStudent() {
        val id = edId.text.toString()
        val name = edName.text.toString()
        val email = edEmail.text.toString()

        if (id.isEmpty() || name.isEmpty() || email.isEmpty()) {

            Toast.makeText(this, "Please enter required field", Toast.LENGTH_SHORT).show()

        } else {
            val std = StudentModel(id = id, name = name, email = email)
            val status = sqliteHelper.insertStudent(std)

            if (status > -1) {
                Toast.makeText(this, "Student Added", Toast.LENGTH_SHORT).show()
                clearEditText()
                getStudent()


            } else {

                Toast.makeText(this,"Record not saved", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun updateStudent(){
        val id = edId.text.toString()
        val name = edName.text.toString()
        val email = edEmail.text.toString()

        if(id == std?.id && name == std?.name && email == std?.email){
            Toast.makeText(this,"Record not changed", Toast.LENGTH_SHORT).show()
            return
        }
        if(std == null) return

        val std = StudentModel(id = id, name = name, email = email)
        val status = sqliteHelper.updateStudent(std)
        if (status > -1){
            clearEditText()
            getStudent()
        }else{
            Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteStudent(id: String){
        //if(id == null)return

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure to delete item?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){ dialog, _->
            sqliteHelper.deleteStudentById(id)
            getStudent()
            dialog.dismiss()
        }
        builder.setNegativeButton("No"){ dialog, _->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()

    }

    private fun clearEditText(){
        edId.setText("")
        edName.setText("")
        edEmail.setText("")
        edName.requestFocus()
    }


    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = StudentAdapter()
        recyclerView.adapter = adapter

    }

    private fun initView(){
        edId = findViewById(R.id.edId)
        edName = findViewById(R.id.edName)
        edEmail = findViewById(R.id.edEmail)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)

    }




}