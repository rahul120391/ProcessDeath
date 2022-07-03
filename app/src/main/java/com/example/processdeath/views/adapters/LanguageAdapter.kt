package com.example.processdeath.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.processdeath.databinding.LayoutRowItemBinding


class LanguageAdapter(private val languageList:List<Pair<String,String>>,private val onItemClick:(Pair<String,String>)->Unit):
    RecyclerView.Adapter<LanguageAdapter.ViewHolder>() {


    override fun getItemCount(): Int = languageList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutRowItemBinding.inflate(
            LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(languageList[position].first)
    }

    inner class ViewHolder(private val rowItemBinding: LayoutRowItemBinding):RecyclerView.ViewHolder(rowItemBinding.root){

        fun bind(item:String){
            rowItemBinding.apply {
                txtItem.text = item
                root.setOnClickListener {
                    onItemClick(languageList[adapterPosition])
                }
            }
        }
    }

}