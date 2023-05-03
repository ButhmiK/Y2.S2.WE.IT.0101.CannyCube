package com.example.c_login.income

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.c_login.R

class StudentAdapter:RecyclerView.Adapter<StudentAdapter.StudentViewHolder>(){

     private var stdList:ArrayList<StudentModel> = ArrayList()
     private var onClickItem:((StudentModel)->Unit)? = null
    private var onClickDeleteItem:((StudentModel)->Unit)? = null

    fun addItems(items: ArrayList<StudentModel>){
        this.stdList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (StudentModel)->Unit){
        this.onClickItem = callback
    }

    fun setOnClickDelete(callback: (StudentModel) -> Unit){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = StudentViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_std, parent, false)
    )



    override fun getItemCount(): Int {
      return stdList.size
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val std = stdList[position]
        holder.bindView(std)
        holder.itemView.setOnClickListener{ onClickItem?.invoke(std) }
        holder.btnDelete.setOnClickListener{ onClickDeleteItem?.invoke(std) }
    }

    class StudentViewHolder(var view: View): RecyclerView.ViewHolder(view){

        private var id = view.findViewById<TextView>(R.id.tvId)
        private var name = view.findViewById<TextView>(R.id.tvName)
        private var email = view.findViewById<TextView>(R.id.tvEmail)
         var btnDelete = view.findViewById<Button>(R.id.btnDelete)


        fun bindView(std: StudentModel){
            id.text = std.id
            name.text = std.name
            email.text = std.email

        }
    }

}