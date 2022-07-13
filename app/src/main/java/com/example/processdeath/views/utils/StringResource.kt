package com.example.processdeath.views.utils

import android.content.Context
import android.content.res.Resources
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StringResource @Inject constructor(@ApplicationContext context: Context)  {

    private var resource:Resources = context.resources

    fun setResource(resource: Resources){
        this.resource = resource
    }

    fun getString(stringId:Int) = resource.getString(stringId)

    fun getStringArray(arrayId:Int):Array<String> = resource.getStringArray(arrayId)
}