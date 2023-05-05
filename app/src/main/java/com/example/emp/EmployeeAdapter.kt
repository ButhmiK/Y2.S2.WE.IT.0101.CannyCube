
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
    private var onClickItem:((EmployeeModel) -> Unit)? = null
    private var onClickDeleteItem:((EmployeeModel) -> Unit)? = null

    fun addItems(items: ArrayList<EmployeeModel>){
        this.empList =items
        notifyDataSetChanged()
    }

    fun setonClickItem(callback:(EmployeeModel) -> Unit){
        this.onClickItem = callback
    }

    fun setOnClickDelete(callback: (EmployeeModel) -> Unit){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType:Int )= EmployeeViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_item_emp,parent,false)
    )
    override fun onBindViewHolder(holder:EmployeeViewHolder ,position:Int ){
        val emp = empList[position]
        holder.bindView(emp)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(emp) }
        holder.btndelete.setOnClickListener{ onClickDeleteItem?.invoke(emp) }
    }
    override fun getItemCount():Int{
        return empList.size
    }

    class EmployeeViewHolder(var view: View): RecyclerView.ViewHolder(view){

        private var expenses = view.findViewById<TextView>(R.id.Expenses)
        private var week = view.findViewById<TextView>(R.id.Week)
        private var month = view.findViewById<TextView>(R.id.Month)
        var btndelete = view.findViewById<Button>(R.id.btnDelete)

        fun bindView(emp:EmployeeModel){

            expenses.text = emp.expenses
            week.text = emp.week
            month.text = emp.month
        }
    }
}