package com.example.c_login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var database: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val registerButton = findViewById<Button>(R.id.button)
        val loginButton = findViewById<Button>(R.id.button4)
        etUsername = findViewById<EditText>(R.id.editTextTextPersonName2)
        etPassword = findViewById<EditText>(R.id.editTextTextPassword3)
        database = DataBaseHelper(this)

        registerButton.setOnClickListener{

            val intent = Intent(applicationContext, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        loginButton.setOnClickListener {
            val usernameEditText = etUsername.text.toString()
            val passwordEditText = etPassword.text.toString()
            val result = database.userValidationFunction(usernameEditText,passwordEditText)

//            Toast.makeText(this@MainActivity,"Hello!",Toast.LENGTH_SHORT).show()


            if(TextUtils.isEmpty(usernameEditText)|| TextUtils.isEmpty(passwordEditText)){
                Toast.makeText(this@MainActivity,"Enter your username and password!", Toast.LENGTH_SHORT).show()
            }else {

                if (result == true){
                    Toast.makeText(this@MainActivity, "Login Successful!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, FunctionNavigate::class.java)
                    startActivity(intent)
                    finish()
                }else {
                    Toast.makeText(this@MainActivity, "Something Went Wrong!", Toast.LENGTH_SHORT).show()
                }
            }


        }

    }

}

