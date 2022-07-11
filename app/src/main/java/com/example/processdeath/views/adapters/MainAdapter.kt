package com.example.processdeath.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.mylibrary.main.Article
import com.example.processdeath.R
import com.example.processdeath.databinding.LayoutRowNewsItemBinding
import com.example.processdeath.views.extensions.setLayoutHeightUsingWidth
import com.example.processdeath.views.utils.LifecycleRecyclerAdapter
import com.example.processdeath.views.utils.LifecycleViewHolder
import com.example.processdeath.views.utils.StringResource
import com.example.processdeath.views.utils.Utility
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
                      setLayoutHeightUsingWidth(0.6F)
                      load(article.urlToImage){
                          placeholder(R.drawable.ic_launcher_foreground)
                          error(R.drawable.ic_launcher_foreground)
                      }
                  }
              /*  txtSource.apply {
                    val source = stringResource.getString(R.string.source)
                    text = source.plus(":").plus(" ").plus(article.source?.name?:"")
                }
                txtTitle.apply {
                    val title =  stringResource.getString(R.string.title)
                    text = title.plus(":").plus(" ").plus( article.title?:"")
                }*/
                val loading = stringResource.getString(R.string.loading)
                txtSource.apply {
                    val source = stringResource.getString(R.string.source)
                    text = source.plus(":").plus(" ").plus(loading)
                    val sourceName = article.source?.name?:""
                    if(sourceMap.containsKey(position)){
                        val value = sourceMap[position]
                        text = source.plus(":").plus(" ").plus(value)
                    }
                    else{
                        lifecycleScope.launch {
                            val languageMapper = withContext(Dispatchers.Default) {
                                utility.getTransLater(sourceName,position).first()
                            }
                            sourceMap[languageMapper.source] = languageMapper.value
                            notifyItemChanged(languageMapper.source)
                        }

                    }
                }
                txtTitle.apply {
                    val title =  stringResource.getString(R.string.title)
                    text = title.plus(":").plus(" ").plus(loading)
                    val titleName = article.title?:""
                    if(titleMap.containsKey(position)){
                        val value = titleMap[position]
                        text = title.plus(":").plus(value)
                    }
                    else{
                        lifecycleScope.launch {
                            val languageMapper = withContext(Dispatchers.Default) {
                                utility.getTransLater(titleName, position).first()
                            }
                            titleMap[languageMapper.source] = languageMapper.value
                            notifyItemChanged(languageMapper.source)
                        }
                    }
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