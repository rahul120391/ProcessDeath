package com.example.processdeath.views.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel : ViewModel(){

    protected val _showProgressBar = Channel<Boolean>()
    val showProgressBar = _showProgressBar.receiveAsFlow()

    protected val _showOverlay = Channel<Boolean>()
    val showOverlay = _showOverlay.receiveAsFlow()

    protected val _message = Channel<String?>()
    val message = _message.receiveAsFlow()

    private var isLoading:Boolean = false

    fun setIsDataLoaded(){ isLoading = true }

    fun getIsDataLoaded() = isLoading
}