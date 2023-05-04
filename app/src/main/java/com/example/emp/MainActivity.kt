package com.example.emp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var EMPID1:EditText
    private lateinit var CompName:EditText
    private lateinit var ADDRESS:EditText


    private lateinit var SIGNUP:Button
    private lateinit var VIEW:Button

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter:EmployeeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_details)

        
        initView()
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)

        SIGNUP.setOnClickListener { addEmployee() }
        VIEW.setOnClickListener{getEmployee()}

    }

    private fun getEmployee(){
        val empList = sqLiteHelper.getALLEmployee()
        Log.e("gggg","$(empList.size)")

        adapter?.addItem(empList)
    }


    private fun addEmployee(){
        val eid = EMPID1.text.toString()
        val company_name = CompName.text.toString()
        val address = ADDRESS.text.toString()



        if (eid.isEmpty() || company_name.isEmpty()){
            Toast.makeText(this,"Please Enter required field", Toast.LENGTH_SHORT).show()
        }else{
            val emp = EmployeeModel(eid = eid, company_name = company_name,
                address = address)

            val status = sqLiteHelper.insertEmployee(emp)

            //Check insert success or not success
            if(status > -1){
                Toast.makeText(this,"Employee Added Success.", Toast.LENGTH_SHORT).show()
                clearEditText()
            }else{
                Toast.makeText(this,"Recode not saved.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun clearEditText() {
        EMPID1.setText("")
        CompName.setText("")
        ADDRESS.setText("")


        EMPID1.requestFocus()

    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = EmployeeAdapter()
        recyclerView.adapter = adapter
    }


    private fun initView() {
        EMPID1    = findViewById(R.id.EMPID1)
        CompName  = findViewById(R.id.CompName)
        ADDRESS   = findViewById(R.id.ADDRESS)


        recyclerView = findViewById(R.id.recyclerView)

    }

   


}

