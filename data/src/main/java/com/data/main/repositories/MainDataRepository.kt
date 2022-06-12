package com.data.main.repositories

import com.data.main.datasource.MainRemoteDataSource
import com.example.mylibrary.main.TopHeadlines
import javax.inject.Inject

interface MainDataRepository {
    suspend fun getTopHeadLines():Result<TopHeadlines>
}

class MainDataRepositoryImpl @Inject constructor(private val remoteMainDataSource: MainRemoteDataSource):MainDataRepository{


    override suspend fun getTopHeadLines(): Result<TopHeadlines> = remoteMainDataSource.getTopHeadLines()

}