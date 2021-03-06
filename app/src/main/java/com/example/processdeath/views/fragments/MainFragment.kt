package com.example.processdeath.views.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.forEachIndexed
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mylibrary.main.Article
import com.example.processdeath.R
import com.example.processdeath.databinding.FragmentMainBinding
import com.example.processdeath.databinding.LayoutErrorBinding
import com.example.processdeath.databinding.LayoutToolbarCommonBinding
import com.example.processdeath.views.adapters.MainAdapter
import com.example.processdeath.views.base.BaseFragment
import com.example.processdeath.views.extensions.viewBinding
import com.example.processdeath.views.extensions.visible
import com.example.processdeath.views.utils.CustomOutlineProvider
import com.example.processdeath.views.utils.Utility
import com.example.processdeath.views.viewModels.MainViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseFragment(R.layout.fragment_main), NavigationView.OnNavigationItemSelectedListener {

    private val binding by viewBinding(FragmentMainBinding::bind)

    private val viewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var utility: Utility

    private val mainAdapter:MainAdapter by lazy {  MainAdapter(utility = utility, stringResource = stringResource, onItemClick = ::onItemClick) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            LayoutToolbarCommonBinding.bind(root).apply {
                title.text = stringResource.getString(R.string.home)
                toolBar.apply {
                    initToolbar()
                    setUpDrawer(this)
                }
            }
            initRecyclerView()
            context?.let { it1 ->
                ContextCompat.getColor(
                    it1,R.color.md_blue_grey_800)
            }?.let { it2 -> pbBar.pbBar.setIndicatorColor(it2) }
            initObservers()
            viewModel.fetchHeadlines()
        }
    }

    private fun onItemClick(article: Article){
        findNavController().navigate(MainFragmentDirections.actionMainFragmentToNewsDetailFragment(article))
    }

    private fun FragmentMainBinding.initRecyclerView(){
        with(rvView){
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            val itemDecorator = DividerItemDecoration(context,LinearLayoutManager.VERTICAL)
            ContextCompat.getDrawable(context,R.drawable.vertical_transparent_divider_20dp)
                ?.let { itemDecorator.setDrawable(it) }
            addItemDecoration(itemDecorator)
            adapter = mainAdapter
            itemAnimator = null
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.setIsDialogShowing(utility.isDialogShowing())
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        mainAdapter.removeAllItems()
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
            utility.showDialog(stringResource.getString(R.string.logout_confirm),stringResource.getString(R.string.are_u_sure_u_want_to_logout),
                WeakReference(it)
            ){
                viewModel.setLoggedInFalse()
            }
        }
    }

    private fun LayoutErrorBinding.setData(showRetry:Boolean,text:String){
        btnRetry.apply {
            visible(showRetry)
            outlineProvider = CustomOutlineProvider(10F)
            setOnClickListener {
                lifecycleScope.launch {
                    viewModel.fetchLatestHeadlines()
                }
            }
        }
        txtError.text = text
    }
    private fun FragmentMainBinding.initObservers(){
        with(viewModel){
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED){
                    launch {
                        onLogout.collectLatest {
                            findNavController().navigate(R.id.action_global_loginFragment)
                        }
                    }
                    launch {
                        showOverlay.collectLatest {
                             layoutOverlay.root.visible(it)
                        }
                    }
                    launch {
                        showProgressBar.collectLatest {
                            layoutError.root.visible(false)
                            pbBar.root.visible(it)
                        }
                    }
                    launch {
                        message.collectLatest {
                            it?.let { it1 -> showSnackBar(it1) }
                        }
                    }
                    launch {
                        onFetchError.collectLatest {
                            layoutError.root.visible(true)
                            pbBar.root.visible(false)
                            val message = it.first?:stringResource.getString(R.string.something_went_wrong)
                            val showRetry = it.second
                            layoutError.setData(showRetry,message)
                        }
                    }
                }
            }
            isDialogShowing.observe(viewLifecycleOwner){
                if(it){
                    showLogoutDialog()
                }
            }
            headlines.observe(viewLifecycleOwner){
                  pbBar.root.visible(false)
                  mainAdapter.updateData(it)
            }
        }
    }

    private fun FragmentMainBinding.setUpDrawer(toolbar: Toolbar){
        val titles = arrayOf(stringResource.getString(R.string.profile),stringResource.getString(R.string.settings))
        val drawerToggle = ActionBarDrawerToggle(activity,drawer, toolbar,R.string.open,R.string.close)
        drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        navView.setNavigationItemSelectedListener(this@MainFragment)
        navView.menu.forEachIndexed { index, item ->
            item.title = titles[index]
        }
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding.drawer.closeDrawer(GravityCompat.START)
        when(item.itemId){
             R.id.profile->{

             }
             R.id.settings->{
                findNavController().navigate(MainFragmentDirections.actionMainFragmentToSettingsFragment())
             }
        }
        return true
    }

}