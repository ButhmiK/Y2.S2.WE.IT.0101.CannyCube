package com.example.c_login.company



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import com.example.c_login.R

class CompanyAdapter : RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder>() {
    private var cmpList:ArrayList<CompanyModel> =ArrayList()
    private var onClickItem: ((CompanyModel) -> Unit)? = null
    private var onClickDeleteItem: ((CompanyModel) -> Unit)? = null

    fun addItems(items: ArrayList<CompanyModel>){
        this.cmpList = items
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (CompanyModel) -> Unit){
        this.onClickItem = callback

    }

    fun setOnClickDeleteItem (callback: (CompanyModel) -> Unit){
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= CompanyViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_company,parent,  false)
    )

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        val cmp = cmpList [position]
        holder.bindView(cmp)
        holder.itemView.setOnClickListener { onClickItem?.invoke(cmp) }
        holder.btnDelete.setOnClickListener { onClickDeleteItem?.invoke(cmp) }
    }

    override fun getItemCount(): Int {
        return cmpList.size

    }


    class CompanyViewHolder(var view:View): RecyclerView.ViewHolder(view){

        private var cid=view.findViewById<TextView>(R.id.tvCId)
        private var cname=view.findViewById<TextView>(R.id.tvCName)
        private var location=view.findViewById<TextView>(R.id.tvCLocation)
        private var telephone=view.findViewById<TextView>(R.id.tvCTelephone)
        private var year=view.findViewById<TextView>(R.id.tvCYear)
        var btnDelete=view.findViewById<TextView>(R.id.btnDelete)



        fun bindView(cmp:CompanyModel){
            cid.text=cmp.cid.toString()
            cname.text=cmp.cname.toString()
            location.text=cmp.location.toString()
            telephone.text=cmp.telephone.toString()
            year.text=cmp.year.toString()

        }

    }
}



