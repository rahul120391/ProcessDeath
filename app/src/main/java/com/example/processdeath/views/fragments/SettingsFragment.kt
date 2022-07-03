package com.example.processdeath.views.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.processdeath.R
import com.example.processdeath.databinding.FragmentSettingsBinding
import com.example.processdeath.databinding.LayoutToolbarCommonBinding
import com.example.processdeath.views.adapters.SettingsAdapter
import com.example.processdeath.views.base.BaseFragment
import com.example.processdeath.views.extensions.viewBinding
import com.example.processdeath.views.extensions.visible
import com.example.processdeath.views.utils.LocalLanguageChangeHelper
import com.example.processdeath.views.utils.Utility
import com.example.processdeath.views.viewModels.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment:BaseFragment(R
    .layout.fragment_settings) {

    private val binding by viewBinding(FragmentSettingsBinding::bind)

    private val viewModel by viewModels<SettingsViewModel>()

    @Inject
    lateinit var utility: Utility

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            initToolBar()
            initRecyclerView()
            initObservers()
        }
    }

    private fun FragmentSettingsBinding.initToolBar(){
        LayoutToolbarCommonBinding.bind(root).apply {
            title.text = getString(R.string.settings)
            toolBar.apply {
                navigationIcon = context?.let { ContextCompat.getDrawable(it,R.drawable.ic_baseline_arrow_back_ios_24) }
                setNavigationOnClickListener {
                    activity?.onBackPressed()
                }
            }
        }
    }

    private fun FragmentSettingsBinding.initRecyclerView(){
          with(rvSettings){
              setHasFixedSize(true)
              layoutManager = LinearLayoutManager(context)
              addItemDecoration(DividerItemDecoration(context,RecyclerView.VERTICAL))
              adapter = SettingsAdapter(resources.getStringArray(R.array.settings_items).toList(),::onItemClick)
          }
    }

    private fun onItemClick(item:String){
         if(item == getString(R.string.change_language)){
              findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToChooseLanguageFragment())
         }
         else if(item == getString(R.string.logout)){
             showLogoutDialog()
         }
    }

    private fun FragmentSettingsBinding.initObservers(){
        with(viewModel) {
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                    launch {
                        onLogout.collectLatest {
                            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToLoginFragment())
                        }
                    }
                    launch {
                        showOverlay.collectLatest {
                            layoutOverlay.root.visible(it)
                        }
                    }
                }
            }
        }
    }

    private fun showLogoutDialog(){
        context?.let {
            utility.showDialog(utility.getString(R.string.logout_confirm),utility.getString(R.string.are_u_sure_u_want_to_logout),
                WeakReference(it)
            ){
                viewModel.setLoggedInFalse()
            }
        }
    }

}