package com.example.processdeath.views.viewModels

import com.domain.base.Result
import com.domain.login.LoginAvailabilityUseCase
import com.example.processdeath.views.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val loginAvailabilityUseCase: LoginAvailabilityUseCase):BaseViewModel() {


    suspend fun checkIfLoggedIn():Boolean{
        val result  = loginAvailabilityUseCase.executeUseCase()
        return if(result is Result.Success) result.data else false
    }


}