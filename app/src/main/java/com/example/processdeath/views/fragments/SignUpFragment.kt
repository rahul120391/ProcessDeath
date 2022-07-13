package com.example.processdeath.views.fragments

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.processdeath.R
import com.example.processdeath.databinding.FragmentSignUpBinding
import com.example.processdeath.databinding.LayoutToolbarCommonBinding
import com.example.processdeath.views.base.BaseFragment
import com.example.processdeath.views.extensions.hideKeyboard
import com.example.processdeath.views.extensions.viewBinding
import com.example.processdeath.views.extensions.visible
import com.example.processdeath.views.viewModels.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {

     private val binding by viewBinding(FragmentSignUpBinding::bind)
     private val viewModel   by viewModels<SignUpViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            initToolBar()
            setUpViews()
            setListeners()
            setUpAdapter()
            initObservers()
        }
    }

    private fun FragmentSignUpBinding.initToolBar(){
        LayoutToolbarCommonBinding.bind(root).apply {
            title.text = stringResource.getString(R.string.sign_up)
            toolBar.apply {
            navigationIcon = context?.let {
                ContextCompat.getDrawable(
                    it,
                    R.drawable.ic_baseline_arrow_back_ios_24
                )
            }
            setNavigationOnClickListener {
                activity?.onBackPressed()
            }
        }
        }
    }


    private fun FragmentSignUpBinding.setListeners(){
        btnSignUp.setOnClickListener {
            context?.let { it1 -> it.hideKeyboard(it1) }
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val hobby = txtHobby.text.toString()
            viewModel.performSignUp(name, hobby, email, password)
        }

        etName.doOnTextChanged { text, _, _, _ ->
            imgClearName.visible(text?.isNotBlank()==true)
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

    private fun FragmentSignUpBinding.setUpAdapter(){
          val list = stringResource.getStringArray(R.array.hobbies).toList()
          val adapter = context?.let { ArrayAdapter(it,android.R.layout.simple_spinner_dropdown_item,list) }
          txtHobby.setAdapter(adapter)
          txtHobby.setText(list[0])
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
                        onSignUp.collectLatest {
                                message->
                            showSnackBar(message)
                            findNavController().navigate(R.id.action_global_mainFragment)
                        }
                    }
                }

            }
        }
    }

    private fun FragmentSignUpBinding.setUpViews(){
          tilName.hint = stringResource.getString(R.string.name)
          tilHobby.hint = stringResource.getString(R.string.hobby)
          tilEmail.hint = stringResource.getString(R.string.email)
          tilPassword.hint = stringResource.getString(R.string.password)
          btnSignUp.text = stringResource.getString(R.string.sign_up)
    }

}