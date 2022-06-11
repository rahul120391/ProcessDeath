package com.example.processdeath.views.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.data.login.repositories.LoginDataStoreRepository
import com.example.processdeath.R
import com.example.processdeath.views.base.BaseViewModel
import com.example.processdeath.views.utils.Utility
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val loginDataStoreRepository: LoginDataStoreRepository,
                                        private val savedStateHandle: SavedStateHandle):BaseViewModel(){

    private val _onLogout = Channel<Unit>()
    val onLogout = _onLogout.receiveAsFlow()

    companion object{
        private const val IS_DIALOG_SHOWING = "IsDialogShowing"
    }

    val isDialogShowing = savedStateHandle.getLiveData(IS_DIALOG_SHOWING,false)

    fun setLoggedInFalse(){
            viewModelScope.launch {
                loginDataStoreRepository.saveData(false)
                _onLogout.send(Unit)
            }
    }

    fun setIsDialogShowing(isShowing:Boolean){
        savedStateHandle[IS_DIALOG_SHOWING] = isShowing
    }



}