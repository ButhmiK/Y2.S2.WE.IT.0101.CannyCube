package com.example.c_login.income

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.c_login.R

class IncomeAdapter:RecyclerView.Adapter<IncomeAdapter.StudentViewHolder>(){

     private var incList:ArrayList<IncomeModel> = ArrayList()
     private var onClickItem:((IncomeModel)->Unit)? = null
    private var onClickDeleteItem:((IncomeModel)->Unit)? = null

    fun addItems(items: ArrayList<IncomeModel>){
        this.incList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (IncomeModel)->Unit){
        this.onClickItem = callback
    }

    fun setOnClickDelete(callback: (IncomeModel) -> Unit){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StudentViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_inc, parent, false)
    )



    override fun getItemCount(): Int {
      return incList.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val inc = incList[position]
        holder.bindView(inc)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(inc) }
        holder.btnDelete.setOnClickListener{ onClickDeleteItem?.invoke(inc) }
    }

    class StudentViewHolder(var view: View): RecyclerView.ViewHolder(view){

        private var id = view.findViewById<TextView>(R.id.tvId)
        private var category = view.findViewById<TextView>(R.id.tvName)
        private var month = view.findViewById<TextView>(R.id.tvEmail)
        private var amount = view.findViewById<TextView>(R.id.tvAmount)
         var btnDelete = view.findViewById<Button>(R.id.btnDelete)


        fun bindView(inc: IncomeModel){
            id.text = inc.inc_id
            category.text= inc.inc_category
            month.text = inc.inc_month
            amount.text = inc.inc_amount

        }
    }

}