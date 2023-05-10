package com.example.c_login.company

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.c_login.FunctionNavigate
import com.example.c_login.R

class CompanyActivity : AppCompatActivity() {

    private lateinit var edCName: EditText
    private lateinit var edLocation: EditText
    private lateinit var edTelephone: EditText
    private lateinit var edYear: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnView: Button
    private  lateinit var btnUpdate:Button
    private  lateinit var btnbck:Button

    private lateinit var sqliteHelper: CompanyDatabase
    private lateinit var  recyclerView: RecyclerView
    private var adapter: CompanyAdapter? = null
    private var cmp: CompanyModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company)




        initView()
        initRecyclerView()
        sqliteHelper= CompanyDatabase(this )

        btnAdd.setOnClickListener { addCompany()}
        btnView.setOnClickListener { getCompany() }
        btnUpdate.setOnClickListener { updateCompany() }


        adapter?.setOnClickItem {
            Toast.makeText(this, it.cname,Toast.LENGTH_SHORT).show()

            edCName.setText(it.cname)
            edLocation.setText(it.location)
            edTelephone.setText(it.telephone)
            edYear.setText(it.year)
            cmp = it
        }
        adapter?.setOnClickDeleteItem{
            deleteCompany(it.cid)
        }

        btnbck.setOnClickListener {
            val intent = Intent(this, FunctionNavigate::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getCompany() {
        val cmpList =sqliteHelper.getAllCompany()
        Log.e("ppp","${cmpList.size}")

        adapter?.addItems(cmpList)

    }

    private fun addCompany() {
        val cname = edCName.text.toString()
        val location = edLocation.text.toString()
        val telephone = edTelephone.text.toString()
        val year = edYear.text.toString()

        if (cname.isEmpty()|| location.isEmpty()|| telephone.isEmpty()||year.isEmpty()){
            Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show()
        }else {
            val cmp = CompanyModel(cname=cname,location= location, telephone = telephone, year = year)
            val status = sqliteHelper.insertCompany(cmp)

            //Check insert success or not
            if(status > -1){
                Toast.makeText(this,"Company Added...",Toast.LENGTH_SHORT).show()
                clearEditText()
                getCompany()
            }else{
                Toast.makeText(this,"Company not Saved",Toast.LENGTH_SHORT).show()

            }
        }

    }

    private fun updateCompany(){
        val cname = edCName.text.toString()
        val location = edLocation.text.toString()
        val telephone = edTelephone.text.toString()
        val year = edYear.text.toString()

        //check record not change
        if (cname == cmp?.cname && location == cmp?.location && telephone == cmp?.telephone && year == cmp?.telephone) {
            Toast.makeText(this, "Record not changed", Toast.LENGTH_SHORT).show()
            return
        }

        if(cmp == null) return

        val cmp= CompanyModel( cid = cmp!!.cid,cname =cname,location=location,telephone=telephone, year=year)
        val status = sqliteHelper.updateCompany(cmp)
        if (status > -1){
            clearEditText()
            getCompany()

        } else{

            Toast.makeText(this,"Update failed", Toast.LENGTH_SHORT).show()

        }


    }
    private fun deleteCompany(cid:Int) {

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure want to delete item?")
        builder.setCancelable(true)
        builder.setPositiveButton("Yes") { dialog, _ ->
            sqliteHelper.deleteCompanyById(cid)
            getCompany()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog, _ ->

            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

    private fun clearEditText() {
        edCName.setText("")
        edLocation.setText("")
        edTelephone.setText("")
        edYear.setText("")
        edCName.requestFocus()
        edLocation.requestFocus()
        edTelephone.requestFocus()
        edYear.requestFocus()

    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CompanyAdapter()
        recyclerView.adapter = adapter

    }

    private fun initView() {
        edCName = findViewById(R.id.editTextTextPersonName)
        edLocation = findViewById(R.id.editTextTextPersonName7)
        edTelephone = findViewById(R.id.editTextTextPersonName2)
        edYear = findViewById(R.id.editTextTextPersonName3)
        btnAdd = findViewById(R.id.button)
        btnView = findViewById(R.id.button2)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnbck = findViewById(R.id.button3)
        recyclerView = findViewById(R.id.recyclerView)

    }
}




