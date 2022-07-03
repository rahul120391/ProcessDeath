package com.example.processdeath.views.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.processdeath.R
import com.example.processdeath.databinding.FragmentChooseLanguageBinding
import com.example.processdeath.databinding.LayoutToolbarCommonBinding
import com.example.processdeath.views.adapters.LanguageAdapter
import com.example.processdeath.views.base.BaseFragment
import com.example.processdeath.views.extensions.viewBinding
import com.example.processdeath.views.utils.LocalLanguageChangeHelper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChooseLanguageFragment : BaseFragment(R.layout.fragment_choose_language) {

    private val binding by viewBinding(FragmentChooseLanguageBinding::bind)



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            initToolBar()
            initRecyclerView()
        }
    }

    private fun FragmentChooseLanguageBinding.initToolBar(){
        LayoutToolbarCommonBinding.bind(root).apply {
            title.text = getString(R.string.choose_language)
            toolBar.apply {
                navigationIcon = context?.let { ContextCompat.getDrawable(it,R.drawable.ic_baseline_arrow_back_ios_24) }
                setNavigationOnClickListener {
                    activity?.onBackPressed()
                }
            }
        }
    }

    private fun FragmentChooseLanguageBinding.initRecyclerView(){
        with(rvSettings){
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = LanguageAdapter(listOf(Pair("Hindi","hi"), Pair("Punjabi","pa"),
                Pair("Arabic","ar")
            ),::onItemClick)
        }
    }

    private fun onItemClick(item:Pair<String,String>){
        showSnackBar("selected language ${item.first}")
        context?.let { LocalLanguageChangeHelper.setLocale(it,item.second)
            activity?.recreate()
        }
    }

}