package com.example.processdeath.views.viewModels

import androidx.lifecycle.viewModelScope
import com.data.login.repositories.LoginDataStoreRepository
import com.example.processdeath.views.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(private val loginDataStoreRepository: LoginDataStoreRepository):BaseViewModel(){

    private val _onLogout = Channel<Unit>()
    val onLogout = _onLogout.receiveAsFlow()

    fun setLoggedInFalse(){
        viewModelScope.launch {
            _showOverlay.send(true)
            loginDataStoreRepository.saveData(false)
            delay(3000)
            _showOverlay.send(false)
            _onLogout.send(Unit)
        }
    }
}