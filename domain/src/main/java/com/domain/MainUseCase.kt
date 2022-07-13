package com.domain

import com.data.main.repositories.MainDataRepository
import com.domain.base.BaseUseCaseWithoutRequestParam
import com.domain.base.Result
import com.example.mylibrary.main.Article
import javax.inject.Inject

interface MainUseCase :BaseUseCaseWithoutRequestParam<List<Article>>


class MainUseCaseImpl @Inject constructor(private val mainDataRepository: MainDataRepository):MainUseCase{


    override suspend fun executeUseCase(): Result<List<Article>> {
        return run {
            val response = mainDataRepository.getTopHeadLines()
            if (response.isSuccess){
                val result = response.getOrNull()
                if(result!=null){
                    val list = result.articles
                    if(list.isNullOrEmpty()){
                        Result.Error()
                    }
                    else{
                        Result.Success(list)
                    }
                }
                else{
                    Result.Error(response.exceptionOrNull()?.message)
                }
            }
            else{
                Result.Error(response.exceptionOrNull()?.message)
            }
        }
    }
}