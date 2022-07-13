package com.example.processdeath.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.processdeath.databinding.LayoutRowItemBinding

class SettingsAdapter(private val items:MutableList<String> = mutableListOf(),private val onItemClick:(String)->Unit):RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {


    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutRowItemBinding.inflate(
        LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
           holder.bind(items[position])
    }

    inner class ViewHolder(private val rowItemBinding: LayoutRowItemBinding):RecyclerView.ViewHolder(rowItemBinding.root){

        fun bind(item:String){
            rowItemBinding.apply {
                  txtItem.text = item
                   root.setOnClickListener {
                       onItemClick(items[adapterPosition])
                   }
            }
        }
    }

    fun updateData(list: List<String>){
        with(items){
            clear()
            addAll(list)
            notifyItemRangeInserted(0,size)
        }
    }

}