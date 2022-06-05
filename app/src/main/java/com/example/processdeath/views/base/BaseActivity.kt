package com.example.processdeath.views.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat

abstract class BaseActivity :AppCompatActivity(){

    abstract val statusBarColor:Int
    abstract fun getView(): View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getView())
        window?.let {
            WindowCompat.setDecorFitsSystemWindows(it,false)
            it.statusBarColor = statusBarColor
        }
    }
}