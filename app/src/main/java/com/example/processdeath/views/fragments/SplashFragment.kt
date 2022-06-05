package com.example.processdeath.views.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.processdeath.R
import com.example.processdeath.views.base.BaseFragment
import kotlinx.coroutines.delay

class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenCreated {
            delay(5000)
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
        }
    }

}