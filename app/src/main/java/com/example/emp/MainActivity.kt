package com.example.emp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var EMPID1:EditText
    private lateinit var CompName:EditText
    private lateinit var ADDRESS:EditText
    private lateinit var TP:EditText
    private lateinit var PASSWORD:EditText
    private lateinit var SIGNUP:Button

    private lateinit var sqLiteHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        
        initView()
        sqLiteHelper = SQLiteHelper(this)

        SIGNUP.setOnClickListener { addEmployee() }
    }

//    private fun getEmployee(){
//        val stdList = sqLiteHelper.getALLEmployee()
//        Log.e("gggg","$(empList.size)")
//    }


    private fun addEmployee(){
        val eid = EMPID1.text.toString()
        val company_name = CompName.text.toString()
        val address = ADDRESS.text.toString()
        val tp = TP.text.toString()
        val password = PASSWORD.text.toString()

        if (eid.isEmpty() || company_name.isEmpty()){
            Toast.makeText(this,"Please Enter required field", Toast.LENGTH_SHORT).show()
        }else{
            val emp = EmployeeModel(eid = eid, company_name = company_name, address = address, tp = tp, password = password )
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
        TP.setText("")
        PASSWORD.setText("")
//        PASSWORD2.setText("")
        EMPID1.requestFocus()
        PASSWORD.requestFocus()
//        PASSWORD2.requestFocus()
    }

    private fun initView() {
        EMPID1    = findViewById(R.id.EMPID1)
        CompName  = findViewById(R.id.CompName)
        ADDRESS   = findViewById(R.id.ADDRESS)
        TP        = findViewById(R.id.TP)
        PASSWORD  = findViewById(R.id.PASSWORD)
//        PASSWORD2 = findViewById(R.id.PASSWORD2)
        SIGNUP    = findViewById(R.id.SIGNUP)
    }

   


}

//LOGIN.setOnClickListener{
//    if(EMPID1.text.isNullOrBlank()&&PASSWORD.text.isNullOrBlank()){
//        Toast.makeText(this, "Please fill the required fields", Toast.LENGTH_SHORT).show()
//    }else{
//        Toast.makeText(this,"${EMPID1.text}is logged in", Toast.LENGTH_SHORT).show()
//    }
//}