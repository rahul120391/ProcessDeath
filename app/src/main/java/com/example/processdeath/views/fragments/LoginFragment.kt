package com.example.processdeath.views.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.processdeath.R
import com.example.processdeath.databinding.FragmentLoginBinding
import com.example.processdeath.views.base.BaseFragment
import com.example.processdeath.views.extensions.hideKeyboard
import com.example.processdeath.views.extensions.viewBinding
import com.example.processdeath.views.extensions.visible
import com.example.processdeath.views.viewModels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment(R.layout.fragment_login){


    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel by viewModels<LoginViewModel>()
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
            setTextValues()
            setListeners()
        }
        initObservers()
    }


    private fun FragmentLoginBinding.setListeners(){
        btnLogin.setOnClickListener {
            context?.let { it1 -> it.hideKeyboard(it1) }
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            viewModel.performLogin(email, password)
        }
        btnSignUp.setOnClickListener {
            val action =  LoginFragmentDirections.actionLoginFragmentToSignUpFragment2()
            findNavController().navigate(action)
        }
        etEmail.doOnTextChanged { text, _, _, _ ->
            imgClearEmail.visible(text?.isNotBlank()==true)
            val isValid = viewModel.isValidEmail(text.toString())
            imgCorrectEmail.visible(isValid)
            if(isValid){
                etEmail.setPaddingRelative(resources.getDimension(R.dimen._40sdp).toInt(),resources.getDimension(R.dimen._20sdp).toInt(),
                    resources.getDimension(R.dimen._40sdp).toInt(),resources.getDimension(R.dimen._20sdp).toInt())
            }
            else{
                etEmail.setPaddingRelative(resources.getDimension(R.dimen._15sdp).toInt(),resources.getDimension(R.dimen._20sdp).toInt(),
                    resources.getDimension(R.dimen._40sdp).toInt(),resources.getDimension(R.dimen._20sdp).toInt())
            }
        }
        txtLink.setOnClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse(txtLink.text.toString())).also {
                this@LoginFragment.startActivity(it)
            }
        }
        etPassword.doOnTextChanged { text, _, _, _ ->
            imgClearPassword.visible(text?.isNotBlank()==true)
            val isValid = viewModel.isValidPassword(text.toString())
            imgCorrectPassword.visible(isValid)
            if(isValid){
                etPassword.setPaddingRelative(resources.getDimension(R.dimen._40sdp).toInt(),resources.getDimension(R.dimen._20sdp).toInt(),
                    resources.getDimension(R.dimen._40sdp).toInt(),resources.getDimension(R.dimen._20sdp).toInt())
            }
            else{
                etPassword.setPaddingRelative(resources.getDimension(R.dimen._15sdp).toInt(),resources.getDimension(R.dimen._20sdp).toInt(),
                    resources.getDimension(R.dimen._40sdp).toInt(),resources.getDimension(R.dimen._20sdp).toInt())
            }
        }


        imgClearEmail.setOnClickListener {
            etEmail.setText("")
        }

        imgClearPassword.setOnClickListener {
            etPassword.setText("")
        }
    }

    private fun initObservers(){
        with(viewModel){
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                        launch{
                             showProgressBar.collectLatest {
                                   binding.layoutPbBar.root.visible(it)
                             }
                        }
                        launch {
                             message.collectLatest {
                                 showSnackBar(it?:getString(R.string.something_went_wrong))
                             }
                        }
                       launch {
                           onLogin.collectLatest {
                               message->
                               showSnackBar(message)
                               findNavController().navigate(R.id.action_global_mainFragment)
                           }
                       }
                }

            }
        }
    }

    private fun FragmentLoginBinding.setTextValues(){
           txtLogin.text = stringResource.getString(R.string.login)
           tilEmail.hint = stringResource.getString(R.string.email)
           tilPassword.hint = stringResource.getString(R.string.password)
           btnLogin.text = stringResource.getString(R.string.login)
           btnSignUp.text = stringResource.getString(R.string.sign_up)
    }

}