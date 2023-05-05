package com.example.emp
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var ADD1:Button
    private lateinit var VIEW:Button
    private lateinit var UPDATE:Button
    private lateinit var Expenses:EditText
    private lateinit var Week:EditText
    private lateinit var Month:EditText

    private lateinit var sqliteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: EmployeeAdapter? = null
    private var emp: EmployeeModel? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_details)
        initView()

        initRecyclerView()
        sqliteHelper = SQLiteHelper(this)


        ADD1.setOnClickListener{addEmployee()}
        VIEW.setOnClickListener{getEmployee()}
        UPDATE.setOnClickListener{updateEmployee()}

        adapter?.setonClickItem {
            Toast.makeText(this,it.week,Toast.LENGTH_SHORT).show()

            Expenses.setText(it.expenses)
            Week.setText(it.week)
            Month.setText(it.month)
            emp = it
        }

        adapter?.setOnClickDelete {
            deleteEmployee(it.expenses)
        }

    }
    private fun getEmployee(){
        val empList = sqliteHelper.getALLEmployee()
        Log.e("ggg","${empList.size}")

        adapter?.addItems(empList)
    }

    private fun addEmployee(){

        val expenses= Expenses.text.toString()
        val week    = Week.text.toString()
        val month   = Month.text.toString()


        if (expenses.isEmpty() || week.isEmpty() || month.isEmpty()) {
            Toast.makeText(this,"Please Enter required field", Toast.LENGTH_SHORT).show()
        }else{
            val emp = EmployeeModel(expenses = expenses, week = week, month = month)
            val status = sqliteHelper.insertEmployee(emp)

            //Check insert success or not success
            if(status > -1){
                Toast.makeText(this,"Recode Added Successfully.", Toast.LENGTH_SHORT).show()
                clearEditText()
                getEmployee()
            }else{
                Toast.makeText(this,"Recode not saved.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Delete Employee details

    private fun deleteEmployee(expenses: String){
        if(expenses == null)return

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure to delete item?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes"){ dialog, _->
            sqliteHelper.deleteEmployeeById(expenses)
            getEmployee()
            dialog.dismiss()
        }
        builder.setNegativeButton("No"){ dialog, _->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()

    }


    private fun updateEmployee(){
        val expenses= Expenses.text.toString()
        val week    = Week.text.toString()
        val month   = Month.text.toString()

        // check recode change or not
        if(expenses == emp?.expenses && week == emp?.week && month == emp?.month){
            Toast.makeText(this,"Record not change..", Toast.LENGTH_SHORT).show()
            return
        }

        if(emp == null)return

        val emp = EmployeeModel(expenses = expenses, week = week , month = month)
        val status = sqliteHelper.updateEmployee(emp)
        if(status > -1){
            clearEditText()
            getEmployee()
        }else{
            Toast.makeText(this,"Update faild", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearEditText() {
        Expenses.setText("")
        Week.setText("")
        Month.setText("")
        Expenses.requestFocus()

    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = EmployeeAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView() {
        Expenses     = findViewById(R.id.Expenses)
        Week         = findViewById(R.id.Week)
        Month        = findViewById(R.id.Month)
        ADD1         = findViewById(R.id.ADD1)
        VIEW         = findViewById(R.id.VIEW)
        UPDATE       = findViewById(R.id.UPDATE)
        recyclerView = findViewById(R.id.recyclerView)
    }
}

