package com.example.processdeath.views.viewModels

import androidx.lifecycle.SavedStateHandle
import com.example.processdeath.views.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle):BaseViewModel(){


}