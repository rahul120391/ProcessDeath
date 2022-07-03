package com.example.processdeath.views.app

import android.app.Application
import android.content.Context
import com.example.processdeath.views.utils.LocalLanguageChangeHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ProcessDeathApp:Application(){


    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base?.let { LocalLanguageChangeHelper.onAttach(it) })
    }


}