package com.example.processdeath.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.mylibrary.main.Article
import com.example.processdeath.R
import com.example.processdeath.databinding.LayoutRowNewsItemBinding
import com.example.processdeath.views.extensions.setLayoutHeightUsingWidth

class MainAdapter(private val list:MutableList<Article> = mutableListOf(),private val onItemClick:(Article)->Unit):RecyclerView.Adapter<MainAdapter.ViewHolder>() {


    inner class ViewHolder(private val binding:LayoutRowNewsItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(article: Article){
            binding.apply {
                  with(imgNewsPoster){
                      setLayoutHeightUsingWidth(0.6F)
                      load(article.urlToImage){
                          placeholder(R.drawable.ic_launcher_foreground)
                          error(R.drawable.ic_launcher_foreground)
                      }
                  }
                  txtSource.apply {
                      text = context.getString(R.string.source).plus(":").plus(" ").plus(article.source?.name)
                  }
                  txtTitle.apply {
                      text = context.getString(R.string.title).plus(":").plus(" ").plus(article.title)
                  }
                  root.setOnClickListener {
                      onItemClick(list[adapterPosition])
                  }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutRowNewsItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newData:MutableList<Article>){
        with(list){
            clear()
            addAll(newData)
            notifyItemRangeInserted(0,newData.size)
        }
    }
}