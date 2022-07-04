package com.example.processdeath.views.viewModels

import androidx.lifecycle.viewModelScope
import com.domain.signup.SignUpUseCase
import com.example.processdeath.R
import com.example.processdeath.views.base.BaseViewModel
import com.example.processdeath.views.utils.Utility
import com.model.signup.SignUpBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.domain.base.Result
import com.example.processdeath.views.utils.StringResource

@HiltViewModel
class SignUpViewModel @Inject constructor(private val signUpUseCase: SignUpUseCase,
                                          private val utility: Utility,private val stringResource: StringResource):BaseViewModel() {


    private val _onSignUp = Channel<String>()
    val onSignUp  = _onSignUp.receiveAsFlow()


    fun isValidEmail(text:String):Boolean{
        return utility.isValidEmail(text)
    }

    fun isValidPassword(text:String):Boolean{
        return utility.isValidPassword(text)
    }

    fun performSignUp(name:String,hobby:String,email:String,password:String) = viewModelScope.launch {
        val isValid = isValidEmail(email) && isValidPassword(password)
        if (!isValid){
            _message.send(stringResource.getString(R.string.invalid_username_or_passsword))
        }
        else if(name.isBlank()){
            _message.send(stringResource.getString(R.string.name_should_not_be_blank))
        }
        else if(hobby.isBlank()){
            _message.send(stringResource.getString(R.string.hobby_not_blank))
        }
        else{
            signUp(SignUpBody(name,email,password, hobby))
        }
    }


    private suspend fun signUp(signUpBody: SignUpBody){
        _showProgressBar.send(true)
        val result = signUpUseCase.executeUseCase(signUpBody)
        _showProgressBar.send(false)
        if(result is Result.Success)
        {
            _onSignUp.send(result.data.message ?: "")
        }
        else if(result is Result.Error)
        {
            _message.send(result.errorMessage)
        }
    }





}