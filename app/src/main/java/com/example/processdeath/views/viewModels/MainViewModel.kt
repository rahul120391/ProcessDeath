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

    private val _onFetchError = Channel<Pair<String?,Boolean>>()
    val onFetchError = _onFetchError.receiveAsFlow()

    companion object{
        private const val IS_DIALOG_SHOWING = "IsDialogShowing"
        private const val HEADLINE_LIST_RESULT = "HeadlineListResult"
    }

    val isDialogShowing = savedStateHandle.getLiveData(IS_DIALOG_SHOWING,false)

    private var isLoading:Boolean = false

    private fun setIsLoading(){ isLoading = true }

    private fun getIsLoading() = isLoading

    fun setLoggedInFalse(){
            viewModelScope.launch {
                loginDataStoreRepository.saveData(false)
                _onLogout.send(Unit)
            }
    }

    fun setIsDialogShowing(isShowing:Boolean){
        savedStateHandle[IS_DIALOG_SHOWING] = isShowing
    }

    fun fetchHeadlines() = viewModelScope.launch{
        if(!getIsLoading()){
            setIsLoading()
            val result = savedStateHandle.get<List<Article>>(HEADLINE_LIST_RESULT)
            if(result!=null){
                 _message.send("fetched result from savedStateHandle")
                _headlines.value = result.toMutableList()
            }
            else{
                val errorResult =  savedStateHandle.get<Pair<String,Boolean>>(HEADLINE_LIST_RESULT)
                if(errorResult!=null){
                    _message.send("fetched error from savedStateHandle")
                    _onFetchError.send(errorResult)
                }
                else{
                    println("perform fetchLatestHeadlines")
                    fetchLatestHeadlines()
                }
            }

        }
    }

    suspend fun fetchLatestHeadlines() {
        _showProgressBar.send(true)
        val result = mainUseCase.executeUseCase()
        _showProgressBar.send(false)
        setResultData(result)
    }

    private suspend fun setResultData(result:Result<List<Article>>){
        if(result is Result.Success){
            val list = result.data
            if(list.isNotEmpty()){
                 val resultList = list.toMutableList()
                 savedStateHandle[HEADLINE_LIST_RESULT] = resultList
                _headlines.value = resultList
            }
            else{
                val errorResult = Pair(utility.getString(R.string.no_data_found),false)
                savedStateHandle[HEADLINE_LIST_RESULT] = errorResult
                _onFetchError.send(errorResult)
            }

        }
        else if (result is Result.Error){
            val errorResult = Pair(result.errorMessage,true)
            savedStateHandle[HEADLINE_LIST_RESULT] = errorResult
            _onFetchError.send(errorResult)
        }
    }



}