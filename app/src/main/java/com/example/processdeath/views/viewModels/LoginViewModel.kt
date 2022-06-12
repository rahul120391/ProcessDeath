package com.example.processdeath.views.viewModels

import androidx.lifecycle.viewModelScope
import com.domain.base.Result
import com.domain.login.LoginUseCase
import com.example.processdeath.R
import com.example.processdeath.views.base.BaseViewModel
import com.example.processdeath.views.utils.Utility
import com.model.login.LoginBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase,
                                         private val utility: Utility):BaseViewModel() {


    private val _onLogin = Channel<String>()
    val onLogin = _onLogin.receiveAsFlow()

    fun isValidEmail(text: String): Boolean {
        return utility.isValidEmail(text)
    }

    fun isValidPassword(text: String): Boolean {
        return utility.isValidPassword(text)
    }

    fun performLogin(email: String, password: String) = viewModelScope.launch {
        val isValid = isValidEmail(email) && isValidPassword(password)
        if (isValid) {
            login(LoginBody(email, password))
        } else {
            _message.send(utility.getString(R.string.invalid_username_or_passsword))
        }
    }


    private suspend fun login(loginBody: LoginBody){
    _showProgressBar.send(true)
    val result = loginUseCase.executeUseCase(loginBody)
    _showProgressBar.send(false)
    if(result is Result.Success)
    {
        _onLogin.send(result.data.message ?: "")
    }
    else if(result is Result.Error)
    {
        _message.send(result.errorMessage)
    }
}
}