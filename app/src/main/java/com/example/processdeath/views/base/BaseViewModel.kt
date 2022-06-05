package com.example.processdeath.views.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel : ViewModel(){

    val _showProgressBar = Channel<Boolean>()
    val showProgressBar = _showProgressBar.receiveAsFlow()

    val _message = Channel<String?>()
    val message = _message.receiveAsFlow()
}