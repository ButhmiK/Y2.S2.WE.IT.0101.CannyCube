package com.example.emp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.TextView
import java.text.FieldPosition

class EmployeeAdapter:RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {
    private var empList:ArrayList<EmployeeModel> = ArrayList()

    fun addItem(items:ArrayList<EmployeeModel>){
        this.empList =items
//        notifyDatasetChanged()
    }




    override fun onCreateViewHolder(parent: ViewGroup,viewType:Int )=EmployeeViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_item_emp,parent,false)
    )
    override fun onBindViewHolder(holder:EmployeeViewHolder ,position:Int ){
        val emp = empList[position]
        holder.bindView(emp)
    }
    override fun getItemCount():Int{
        return empList.size
    }

    class EmployeeViewHolder(view: View): RecyclerView.ViewHolder(view){
        private var id = view.findViewById<TextView>(R.id.tvId)
        private var name = view.findViewById<TextView>(R.id.tvName)
        private var address = view.findViewById<TextView>(R.id.tvAddress)
        private var btnDelete = view.findViewById<Button>(R.id.btnDelete)

        fun bindView(emp:EmployeeModel){
            id.text = emp.id.toString()
            name.text = emp.name
            address.text = emp.address
        }
    }
}