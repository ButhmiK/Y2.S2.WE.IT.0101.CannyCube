package com.example.c_login.expense

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.c_login.R

class ExpenseActivity : AppCompatActivity() {


    private lateinit var expId: EditText
    private lateinit var expCategory: EditText
    private lateinit var expMonth: EditText
    private lateinit var expAmount: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button



    private lateinit var sqliteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: ExpenseAdapter? = null
    private var std: ExpenseModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)


    initView()
    initRecyclerView()
    sqliteHelper = SQLiteHelper(this)

    btnAdd.setOnClickListener{ addStudent() }
    btnView.setOnClickListener{ getStudent() }
    btnUpdate.setOnClickListener{ updateStudent() }


    adapter?.setOnClickItem {
        Toast.makeText(this,it.exp_category, Toast.LENGTH_SHORT).show()

        expId.setText(it.exp_id)
        expCategory.setText(it.exp_category)
        expMonth.setText(it.exp_month)
        expAmount.setText(it.exp_amount)
        std = it
    }

    adapter?.setOnClickDelete {
        deleteStudent(it.exp_id)
    }
}

private fun getStudent(){
    val stdList = sqliteHelper.getAllStudent()
    Log.e("pppp","${stdList.size}")
    adapter?.addItems(stdList)
}



private fun addStudent() {
    val id = expId.text.toString()
    val category = expCategory.text.toString()
    val month = expMonth.text.toString()
    val amount = expAmount.text.toString()

    if (id.isEmpty() || category.isEmpty() || month.isEmpty() || amount.isEmpty()) {

        Toast.makeText(this, "Please enter required field", Toast.LENGTH_SHORT).show()

    } else {
        val std = ExpenseModel(exp_id = id, exp_category = category, exp_month = month, exp_amount = amount)
        val status = sqliteHelper.insertStudent(std)

        if (status > -1) {
            Toast.makeText(this, "Expense details Added", Toast.LENGTH_SHORT).show()
            clearEditText()
            getStudent()


        } else {

            Toast.makeText(this,"Record not saved", Toast.LENGTH_SHORT).show()

        }
    }
}

private fun updateStudent(){
    val id = expId.text.toString()
    val category = expCategory.text.toString()
    val month = expMonth.text.toString()
    val amount = expAmount.text.toString()

    if(id == std?.exp_id && category == std?.exp_category && month == std?.exp_month && amount == std?.exp_amount){
        Toast.makeText(this,"Record not changed", Toast.LENGTH_SHORT).show()
        return
    }
    if(std == null) return

    val std = ExpenseModel(exp_id = id, exp_category = category, exp_month = month, exp_amount = amount)
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
    expId.setText("")
    expCategory.setText("")
    expMonth.setText("")
    expAmount.setText("")
    expCategory.requestFocus()
}


private fun initRecyclerView(){
    recyclerView.layoutManager = LinearLayoutManager(this)
    adapter = ExpenseAdapter()
    recyclerView.adapter = adapter

}

private fun initView(){
    expId = findViewById(R.id.edId)
    expCategory = findViewById(R.id.edName)
    expMonth = findViewById(R.id.edEmail)
    expAmount = findViewById(R.id.edAmount)
    btnAdd = findViewById(R.id.btnAdd)
    btnView = findViewById(R.id.btnView)
    btnUpdate = findViewById(R.id.btnUpdate)
    recyclerView = findViewById(R.id.recyclerView)

}


}