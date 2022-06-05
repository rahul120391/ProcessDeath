package com.model

sealed class Result<in R>{
    data class Success(val r:R):Result<R>()
    data class Error(val errorMessage:String?=null):Result<R>()
}
