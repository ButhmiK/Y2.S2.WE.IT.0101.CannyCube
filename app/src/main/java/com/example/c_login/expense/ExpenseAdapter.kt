package com.example.c_login.expense

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.c_login.R

class ExpenseAdapter: RecyclerView.Adapter<ExpenseAdapter.StudentViewHolder>(){

     private var expList:ArrayList<ExpenseModel> = ArrayList()
     private var onClickItem:((ExpenseModel)->Unit)? = null
    private var onClickDeleteItem:((ExpenseModel)->Unit)? = null

    fun addItems(items: ArrayList<ExpenseModel>){
        this.expList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (ExpenseModel)->Unit){
        this.onClickItem = callback
    }

    fun setOnClickDelete(callback: (ExpenseModel) -> Unit){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StudentViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_exp, parent, false)
    )



    override fun getItemCount(): Int {
      return expList.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val exp = expList[position]
        holder.bindView(exp)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(exp) }
        holder.btnDelete.setOnClickListener{ onClickDeleteItem?.invoke(exp) }
    }

    class StudentViewHolder(var view: View): RecyclerView.ViewHolder(view){

        private var id = view.findViewById<TextView>(R.id.tvId)
        private var category = view.findViewById<TextView>(R.id.tvName)
        private var month = view.findViewById<TextView>(R.id.tvEmail)
        private var amount = view.findViewById<TextView>(R.id.tvAmount)
         var btnDelete = view.findViewById<Button>(R.id.btnDelete)


        fun bindView(std: ExpenseModel){
            id.text = std.exp_id
            category.text= std.exp_category
            month.text = std.exp_month
            amount.text = std.exp_amount

        }
    }

}