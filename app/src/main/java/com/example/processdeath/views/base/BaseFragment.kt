package com.example.processdeath.views.base


import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment(layoutId:Int):Fragment(layoutId) {


    protected fun showSnackBar(message:String){
        activity?.run {
            Snackbar.make(findViewById(android.R.id.content),message,Snackbar.LENGTH_SHORT).show()
        }
    }
}