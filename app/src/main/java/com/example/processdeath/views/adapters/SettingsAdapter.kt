package com.example.processdeath.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.processdeath.databinding.LayoutSeetingsRowItemBinding

class SettingsAdapter(private val items:List<String>,private val onItemClick:(String)->Unit):RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {


    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutSeetingsRowItemBinding.inflate(
        LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
           holder.bind(items[position])
    }

    inner class ViewHolder(private val settingsRowItemBinding: LayoutSeetingsRowItemBinding):RecyclerView.ViewHolder(settingsRowItemBinding.root){

        fun bind(item:String){
            settingsRowItemBinding.apply {
                  txtItem.text = item
                   root.setOnClickListener {
                       onItemClick(items[adapterPosition])
                   }
            }
        }
    }
}