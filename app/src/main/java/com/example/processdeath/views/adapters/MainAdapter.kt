package com.example.processdeath.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.example.mylibrary.main.Article
import com.example.processdeath.R
import com.example.processdeath.databinding.LayoutRowNewsItemBinding
import com.example.processdeath.views.utils.LifecycleRecyclerAdapter
import com.example.processdeath.views.utils.LifecycleViewHolder
import com.example.processdeath.views.utils.StringResource
import com.example.processdeath.views.utils.Utility

class MainAdapter(private val list:MutableList<Article> = mutableListOf(),
                  private val stringResource: StringResource,
                  private val utility: Utility,private val onItemClick:(Article)->Unit):LifecycleRecyclerAdapter<MainAdapter.ViewHolder>() {

    private val sourceMap = hashMapOf<Int,String>()
    private val titleMap = hashMapOf<Int,String>()
    init {
        sourceMap.clear()
        titleMap.clear()
    }
    inner class ViewHolder(private val binding:LayoutRowNewsItemBinding):LifecycleViewHolder(binding.root){
        fun bind(article: Article,position: Int){
            binding.apply {
                  with(imgNewsPoster){
                      load(article.urlToImage){
                          placeholder(R.drawable.ic_launcher_foreground)
                          error(R.drawable.ic_launcher_foreground)
                      }
                  }
                  val source = stringResource.getString(R.string.source)
                  txtSource.text = source.plus(":").plus(" ").plus(stringResource.getString(R.string.loading))
                  val title = stringResource.getString(R.string.title)
                  txtTitle.text = title.plus(":").plus(" ").plus(stringResource.getString(R.string.loading))
                  if(article.language!=utility.getCurrentSelectedLanguage()) {
                      utility.getTransLater(Pair(position,article.source?.name?:""), sourceLanguage = article.language){
                          val obj = list[it.first]
                          val lang = if(it.second == article.source?.name){
                              article.language
                          }
                          else{
                              utility.getCurrentSelectedLanguage()
                          }
                          list[it.first] = list[it.first].copy(source = obj.source?.copy(name = it.second), language = lang)
                          notifyItemChanged(it.first)
                      }
                      utility.getTransLater(Pair(position,article.title?:""), sourceLanguage = article.language){
                          list[it.first] = list[it.first].copy(title = it.second)
                          notifyItemChanged(it.first)
                      }
                  }
                  else{
                      txtSource.text = source.plus(":").plus(" ").plus(article.source?.name?:"")
                      txtTitle.text = title.plus(":").plus(" ").plus(article.title)
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
        holder.bind(list[position],position)
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