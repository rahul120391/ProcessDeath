package com.example.processdeath.views.utils

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import java.util.*


class LocalLanguageChangeHelper {


    companion object{
        private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"
        private const val LANGUAGE_SHARED_PREF_FILE_NAME = "FileName"


        fun onAttach(context: Context): Context {
            val lang = getPersistedData(context, getLanguage(context))
            return setLocale(context, lang)
        }

        fun onAttach(context: Context, defaultLanguage: String): Context {
            val lang = getPersistedData(context, defaultLanguage)
            return setLocale(context, lang)
        }

        private fun getLanguage(context: Context): String {
            return getPersistedData(context, Locale.getDefault().language)
        }

        fun setLocale(context: Context, language: String): Context {
            persist(context, language)
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                updateResources(context, language)
            } else updateResourcesLegacy(context, language)
        }

        private fun getPersistedData(context: Context, defaultLanguage: String): String {
            val preferences = context.getSharedPreferences(LANGUAGE_SHARED_PREF_FILE_NAME,Context.MODE_PRIVATE)
            return preferences.getString(SELECTED_LANGUAGE, defaultLanguage)?:defaultLanguage
        }

        private fun persist(context: Context, language: String?) {
            val preferences = context.getSharedPreferences(LANGUAGE_SHARED_PREF_FILE_NAME,Context.MODE_PRIVATE)
            val editor = preferences.edit()
            editor.putString(SELECTED_LANGUAGE, language)
            editor.apply()
        }

        @TargetApi(Build.VERSION_CODES.N)
        private fun updateResources(context: Context, language: String): Context {
            val locale = Locale(language)
            Locale.setDefault(locale)
            val configuration = context.resources.configuration
            configuration.setLocale(locale)
            configuration.setLayoutDirection(locale)
            return context.createConfigurationContext(configuration)
        }

        private fun updateResourcesLegacy(context: Context, language: String): Context{
            val locale = Locale(language)
            Locale.setDefault(locale)
            val resources = context.resources
            val configuration = resources.configuration
            configuration.locale = locale
            configuration.setLayoutDirection(locale)
            resources.updateConfiguration(configuration, resources.displayMetrics)
            return context
        }
    }





}