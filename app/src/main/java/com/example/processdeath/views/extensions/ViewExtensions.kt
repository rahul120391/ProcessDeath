package com.example.processdeath.views.extensions

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding


inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }


fun View.visible(visible: Boolean){
    visibility = if(visible){
        View.VISIBLE
    }
    else{
        View.GONE
    }
}

fun View.hideKeyboard(context: Context){
    val manager: InputMethodManager? = context.getSystemService(
        Context.INPUT_METHOD_SERVICE
    ) as InputMethodManager?
    manager?.hideSoftInputFromWindow(
        windowToken, 0
        )
}