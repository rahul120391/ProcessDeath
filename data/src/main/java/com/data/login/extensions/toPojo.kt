package com.data.login.extensions

import com.google.gson.Gson

inline fun <reified T> String.toPojo():T{
    val gson = Gson()
    return gson.fromJson(this,T::class.java)
}