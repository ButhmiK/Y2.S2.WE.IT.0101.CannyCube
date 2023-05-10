package com.example.c_login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.example.c_login.company.CompanyActivity
import com.example.c_login.expense.ExpenseActivity
import com.example.c_login.income.IncomeActivity
import com.example.c_login.login_company.MainActivity

class FunctionNavigate : AppCompatActivity() {

    private lateinit var backbtn: Button

    private lateinit var incomebtn: Button
    private lateinit var expencesbtn: Button
    private lateinit var companybtn: Button
    private lateinit var employeebtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_function_navigate)


        backbtn = findViewById<Button>(R.id.navBack)

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
        employeebtn.setOnClickListener {
            val intent = Intent(this,CompanyActivity::class.java)
            startActivity(intent)
            finish()
        }
        companybtn.setOnClickListener {
            val intent = Intent(this,IncomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        backbtn.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}