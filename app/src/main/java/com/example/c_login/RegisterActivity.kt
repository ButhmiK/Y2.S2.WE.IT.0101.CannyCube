package com.example.c_login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerationBtn: Button
    private lateinit var backBtn: Button
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etComfirmPassword: EditText
    private lateinit var db:DataBaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        backBtn = findViewById<Button>(R.id.button2)
        registerationBtn = findViewById<Button>(R.id.button3)
        etUsername = findViewById(R.id.editTextTextPersonName)
        etPassword = findViewById(R.id.editTextTextPassword)
        etComfirmPassword = findViewById(R.id.editTextTextPassword2)
        db = DataBaseHelper(this)


        registerationBtn.setOnClickListener {
            val usernameText = etUsername.text.toString()
            val passwordText = etPassword.text.toString()
            val comPasswordText = etComfirmPassword.text.toString()

            val saveData = db.insertUserFunction(usernameText,passwordText,comPasswordText)

            if(TextUtils.isEmpty(usernameText)||TextUtils.isEmpty(passwordText)||TextUtils.isEmpty(comPasswordText)){
                Toast.makeText(this@RegisterActivity,"Enter data to continue!",Toast.LENGTH_SHORT).show()
            }else{
                if (passwordText.equals(comPasswordText)){
                    if (saveData==true){
                        Toast.makeText(this@RegisterActivity,"User Registered Successfully!",Toast.LENGTH_SHORT).show()
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this@RegisterActivity,"User Already Exist!",Toast.LENGTH_SHORT).show()
                    }
                }else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Given Password Mismatch with The Confirm Password!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        backBtn.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
            }
        }
}



