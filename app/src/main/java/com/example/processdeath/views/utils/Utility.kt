package com.example.processdeath.views.utils

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Utility @Inject constructor(@ApplicationContext  val context:Context){

    companion object{
        private const val MIN_PASSWORD_LENGTH = 6
    }

    fun isValidEmail(email:String):Boolean{
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password:String):Boolean{
        return password.length>= MIN_PASSWORD_LENGTH
    }

    fun getString(stringId:Int) = context.resources.getString(stringId)

}