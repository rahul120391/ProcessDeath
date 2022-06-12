package com.example.processdeath.views.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.data.login.repositories.LoginDataStoreRepository
import com.domain.MainUseCase
import com.domain.base.Result
import com.example.mylibrary.main.Article
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
                                        private val mainUseCase: MainUseCase,
                                        private val utility: Utility,
                                        private val savedStateHandle: SavedStateHandle):BaseViewModel(){

    private val _onLogout = Channel<Unit>()
    val onLogout = _onLogout.receiveAsFlow()

    private val _headlines = MutableLiveData<MutableList<Article>>()
    val headlines:LiveData<MutableList<Article>> = _headlines

    private val _onFetchError = MutableLiveData<Pair<String?,Boolean>>()
    val onFetchError:LiveData<Pair<String?,Boolean>> = _onFetchError

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

    fun fetchLatestHeadlines() = viewModelScope.launch {
        _showProgressBar.send(true)
        val result = mainUseCase.executeUseCase()
        _showProgressBar.send(false)
        if(result is Result.Success){
             val list = result.data
            if(list.isNotEmpty()){
                _headlines.value = list.toMutableList()
            }
            else{
                _onFetchError.value = Pair(utility.getString(R.string.no_data_found),false)
            }

        }
        else if (result is Result.Error){
            _onFetchError.value = Pair(result.errorMessage,true)
        }
    }



}