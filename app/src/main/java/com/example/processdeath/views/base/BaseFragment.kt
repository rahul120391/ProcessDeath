package com.example.processdeath.views.base


import androidx.fragment.app.Fragment
import com.example.processdeath.views.utils.StringResource
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

abstract class BaseFragment(layoutId:Int):Fragment(layoutId) {

    @Inject
    lateinit var stringResource: StringResource
    protected fun showSnackBar(message:String){
        activity?.run {
            Snackbar.make(findViewById(android.R.id.content),message,Snackbar.LENGTH_SHORT).show()
        }
    }
}