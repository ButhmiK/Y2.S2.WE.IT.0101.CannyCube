package com.example.c_login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.example.c_login.expense.ExpenseActivity
import com.example.c_login.income.IncomeActivity

class FunctionNavigate : AppCompatActivity() {

    private lateinit var incomebtn: Button
    private lateinit var expencesbtn: Button
    private lateinit var companybtn: Button
    private lateinit var employeebtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_function_navigate)


        incomebtn = findViewById<Button>(R.id.button8)
        expencesbtn = findViewById<Button>(R.id.button9)
        companybtn = findViewById<Button>(R.id.button5)
        employeebtn = findViewById<Button>(R.id.button10)

        incomebtn.setOnClickListener {
            val intent = Intent(this,IncomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        expencesbtn.setOnClickListener {
            val intent = Intent(this,ExpenseActivity::class.java)
            startActivity(intent)
            finish()
        }
        companybtn.setOnClickListener {
            val intent = Intent(this,IncomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        employeebtn.setOnClickListener {
            val intent = Intent(this,IncomeActivity::class.java)
            startActivity(intent)
            finish()
        }



    }
}