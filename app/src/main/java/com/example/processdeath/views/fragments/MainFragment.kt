package com.example.processdeath.views.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.processdeath.R
import com.example.processdeath.databinding.FragmentMainBinding
import com.example.processdeath.databinding.LayoutToolbarCommonBinding
import com.example.processdeath.views.base.BaseFragment
import com.example.processdeath.views.extensions.viewBinding
import com.example.processdeath.views.utils.Utility
import com.example.processdeath.views.viewModels.MainViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseFragment(R.layout.fragment_main), NavigationView.OnNavigationItemSelectedListener {

    private val binding by viewBinding(FragmentMainBinding::bind)

    private val viewModel   by viewModels<MainViewModel>()

    @Inject
    lateinit var utility:Utility


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            LayoutToolbarCommonBinding.bind(root).apply {
                title.text = getString(R.string.home)
                toolBar.apply {
                    initToolbar()
                    setUpDrawer(this)
                }
            }
            initObservers()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.setIsDialogShowing(utility.isDialogShowing())
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        utility.dismissDialog()
        super.onDestroyView()
    }

    private fun Toolbar.initToolbar(){
        inflateMenu(R.menu.main_options_menu)
        setOnMenuItemClickListener {
            item->
            when(item.itemId){
                R.id.logout->{
                    showLogoutDialog()
                    true
                }
                else->{
                    false
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

    private fun initObservers(){
        with(viewModel){
            lifecycleScope.launch {
                onLogout.flowWithLifecycle(lifecycle,Lifecycle.State.STARTED).collect{
                    findNavController().navigate(MainFragmentDirections.actionMainFragmentToLoginFragment())
                }
            }

            isDialogShowing.observe(viewLifecycleOwner){
                if(it){
                    showLogoutDialog()
                }
            }
        }
    }

    private fun FragmentMainBinding.setUpDrawer(toolbar: Toolbar){
        val drawerToggle = ActionBarDrawerToggle(activity,drawer, toolbar,R.string.open,R.string.close)
        drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        navView.setNavigationItemSelectedListener(this@MainFragment)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding.drawer.closeDrawer(GravityCompat.START)
        when(item.itemId){
             R.id.profile->{

            }
            R.id.logout->{
                showLogoutDialog()
            }
        }
        return true
    }

}