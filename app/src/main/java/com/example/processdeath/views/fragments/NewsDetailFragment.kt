package com.example.processdeath.views.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.processdeath.R
import com.example.processdeath.databinding.FragmentNewsDetailBinding
import com.example.processdeath.databinding.LayoutToolbarCommonBinding
import com.example.processdeath.views.base.BaseFragment
import com.example.processdeath.views.extensions.bold
import com.example.processdeath.views.extensions.plus
import com.example.processdeath.views.extensions.spannable
import com.example.processdeath.views.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailFragment : BaseFragment(R.layout.fragment_news_detail) {

      private val binding by viewBinding(FragmentNewsDetailBinding::bind)
      private val args:NewsDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            initToolBar()
            setData()
        }
    }

    private fun FragmentNewsDetailBinding.initToolBar(){
        LayoutToolbarCommonBinding.bind(root).apply {
            title.text = args.article.source?.name
            toolBar.apply {
                navigationIcon = context?.let {
                    ContextCompat.getDrawable(
                        it,
                        R.drawable.ic_baseline_arrow_back_ios_24
                    )
                }
                setNavigationOnClickListener {
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun FragmentNewsDetailBinding.setData(){
        imgNewsPoster.load(args.article.urlToImage){
            placeholder(R.drawable.ic_launcher_foreground)
            error(R.drawable.ic_launcher_foreground)
        }
        txtTitle.text =  spannable {
            bold(getString(R.string.title).plus(":"))
        }.plus(" ").plus(args.article.title?:"")
        txtDesc.text =   spannable {
            bold(getString(R.string.desc).plus(":"))
        }.plus(" ").plus(args.article.description?:"")
    }
}

