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
        if(!getIsDataLoaded()){
            setIsDataLoaded()
            /**
             * Note:- For Demo Purpose i have used savedStateHandle to store the list of articles since the list size is not big, its ok to store the list size less than 50 records in savedStateHandle
             * but for larger data , it should be kept in local storage either in private internal storage or db, coz savedStateHandle is used to hold only small amount of data, its very much similar to sharedPref and it also stores the
             * data in key value pair but only to keep the data to byePass process death, in case we are not using viewmodel, plz save the data in onSavedInstanceState of either fragment or activity
             * and fetch it in the oncreate method.
             */
            when (val result = savedStateHandle.get<Any>(HEADLINE_LIST_RESULT)) {
                is List<*> -> {
                    _message.send("fetched result from savedStateHandle")
                    _headlines.value = result.filterIsInstance(Article::class.java).toMutableList()
                }
                is Pair<*, *> -> {
                    _message.send("fetched error from savedStateHandle")
                    _onFetchError.send(Pair(result.first as String,result.second as Boolean))
                }
                else -> {
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
                 savedStateHandle[HEADLINE_LIST_RESULT] = list
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