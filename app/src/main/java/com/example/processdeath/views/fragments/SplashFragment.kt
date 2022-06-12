package com.example.processdeath.views.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.processdeath.R
import com.example.processdeath.views.base.BaseFragment
import com.example.processdeath.views.viewModels.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenCreated {
            val isLoggedIn = viewModel.checkIfLoggedIn()
            delay(5000)
            if(isLoggedIn){
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToMainFragment())
            }
            else{
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
            }
        }
    }

}