package com.domain.base

import kotlinx.coroutines.coroutineScope

interface BaseUseCaseWithRequestParam<in Params, R>:CommonUseCase<R>{
    suspend fun executeUseCase(params:Params):Result<R>
}

interface BaseUseCaseWithoutRequestParam<R>:CommonUseCase<R>{
    suspend fun executeUseCase():Result<R>
}

interface CommonUseCase<R>:CancelTask{
    suspend fun run(operation:suspend ()->Result<R>):Result<R>{
        return try {
            coroutineScope {
                operation()
            }
        }
        catch (e:Exception){
            Result.Error()
        }
    }
}

sealed class Result<out T>{
    data class Success<T>(val data:T):Result<T>()
    data class Error(val errorMessage:String?=null):Result<Nothing>()
}


interface CancelTask {
    fun cancelRequest(){}
}