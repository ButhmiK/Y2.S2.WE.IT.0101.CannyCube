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
    private lateinit var incId: EditText
    private lateinit var incCategory: EditText
    private lateinit var incMonth: EditText
    private lateinit var incAmount: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private lateinit var btnUpdate: Button



    private lateinit var sqliteHelper: DataBaseHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: IncomeAdapter? = null
    private var inc: IncomeModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_income)

        initView()
        initRecyclerView()
        sqliteHelper = DataBaseHelper(this)

        btnAdd.setOnClickListener{ addIncome() }
        btnView.setOnClickListener{ getIncome() }
        btnUpdate.setOnClickListener{ updateIncome() }


        adapter?.setOnClickItem {
            Toast.makeText(this,it.inc_category, Toast.LENGTH_SHORT).show()

            incId.setText(it.inc_id)
            incCategory.setText(it.inc_category)
            incMonth.setText(it.inc_month)
            incAmount.setText(it.inc_amount)

            inc = it
        }

        adapter?.setOnClickDelete {
            deleteIncome(it.inc_id)
        }
    }

    private fun getIncome(){
        val incList = sqliteHelper.getAllIncome()
        Log.e("pppp","${incList.size}")
        adapter?.addItems(incList)
    }



    private fun addIncome() {
        val id = incId.text.toString()
        val category = incCategory.text.toString()
        val month = incMonth.text.toString()
        val amount = incAmount.text.toString()

        if (id.isEmpty() || category.isEmpty() || month.isEmpty() || amount.isEmpty()) {

            Toast.makeText(this, "Please enter required field", Toast.LENGTH_SHORT).show()

        } else {
            val inc = IncomeModel(inc_id = id, inc_category = category, inc_month = month, inc_amount = amount)
            val status = sqliteHelper.insertIncome(inc)

            if (status > -1) {
                Toast.makeText(this, "Income details Added", Toast.LENGTH_SHORT).show()
                clearEditText()
                getIncome()


            } else {

                Toast.makeText(this,"Record not saved", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun updateIncome(){
        val id = incId.text.toString()
        val category = incCategory.text.toString()
        val month = incMonth.text.toString()
        val amount = incAmount.text.toString()

        if(id == inc?.inc_id && category == inc?.inc_category && month == inc?.inc_month && amount == inc?.inc_amount){
            Toast.makeText(this,"Record not changed", Toast.LENGTH_SHORT).show()
            return
        }
        if(inc == null) return

        val inc = IncomeModel(inc_id = id, inc_category = category, inc_month = month, inc_amount = amount)
        val status = sqliteHelper.updateIncome(inc)
        if (status > -1){
            clearEditText()
            getIncome()
        }else{
            Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteIncome(id: String){
        //if(id == null)return

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure to delete item?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){ dialog, _->
            sqliteHelper.deleteIncomeById(id)
            getIncome()
            dialog.dismiss()
        }
        builder.setNegativeButton("No"){ dialog, _->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()

    }

    private fun clearEditText(){
        incId.setText("")
        incCategory.setText("")
        incMonth.setText("")
        incAmount.setText("")
        incCategory.requestFocus()
    }


    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = IncomeAdapter()
        recyclerView.adapter = adapter

    }

    private fun initView(){
        incId = findViewById(R.id.edId)
        incCategory = findViewById(R.id.edName)
        incMonth = findViewById(R.id.edEmail)
        incAmount = findViewById(R.id.edAmount)
        btnAdd = findViewById(R.id.btnAdd)
        btnView = findViewById(R.id.btnView)
        btnUpdate = findViewById(R.id.btnUpdate)
        recyclerView = findViewById(R.id.recyclerView)

    }




}