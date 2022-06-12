package com.example.processdeath.views.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.processdeath.R
import com.example.processdeath.databinding.FragmentNewsDetailBinding
import com.example.processdeath.databinding.FragmentSignUpBinding
import com.example.processdeath.databinding.LayoutToolbarCommonBinding
import com.example.processdeath.views.base.BaseFragment
import com.example.processdeath.views.extensions.viewBinding

class NewsDetailFragment : BaseFragment(R.layout.fragment_news_detail) {

      private val binding by viewBinding(FragmentNewsDetailBinding::bind)
      private val args:NewsDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            initToolBar()
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
}