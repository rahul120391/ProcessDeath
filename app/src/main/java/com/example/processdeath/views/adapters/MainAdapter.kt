package com.example.processdeath.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.example.mylibrary.main.Article
import com.example.processdeath.R
import com.example.processdeath.databinding.LayoutRowNewsItemBinding
import com.example.processdeath.views.extensions.visible
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
                  txtSource.text = ""
                  val title = stringResource.getString(R.string.title)
                  txtTitle.text = ""
                  cpi.visible(true)
                  if(article.language!=utility.getCurrentSelectedLanguage()) {
                      utility.getTransLater(Pair(position,(article.source?.name?:"").plus("|").plus(article.title?:"")),
                          sourceLanguage = article.language){
                          cpi.visible(false)
                          if(list.getOrNull(it.first)!=null){
                              val obj = list[it.first]
                              val text = it.second.split("|")
                              val sourceText = text.getOrNull(0)?:""
                              val titleText = text.getOrNull(1)?:""
                              val lang = if(sourceText == article.source?.name){
                                  article.language
                              }
                              else{
                                  utility.getCurrentSelectedLanguage()
                              }
                              list[it.first] = list[it.first].copy(source = obj.source?.copy(name = sourceText), title = titleText, language = lang)
                              notifyItemChanged(it.first)
                          }
                      }
                  }
                  else{
                      cpi.visible(false)
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

    fun removeAllItems(){
        with(list){
            clear()
            notifyItemRangeRemoved(0,size)
        }
    }
}