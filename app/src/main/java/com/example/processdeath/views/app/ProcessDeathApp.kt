package com.example.processdeath.views.app

import android.app.Application
import android.content.Context
import com.example.processdeath.views.utils.LocaleHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ProcessDeathApp:Application(){

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base?.let {
            LocaleHelper.getLanguage(it)
                ?.let { language->LocaleHelper.onAttach(base, language) }
        })
    }
}