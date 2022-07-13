package com.data.main.repositories

import com.data.main.datasource.MainLocalDataSource
import com.data.main.datasource.MainRemoteDataSource
import com.example.mylibrary.main.TopHeadlines
import javax.inject.Inject

interface MainDataRepository {
    suspend fun getTopHeadLines():Result<TopHeadlines>
}

class MainDataRepositoryImpl @Inject constructor(private val localMainLocalDataSource: MainLocalDataSource,private val remoteMainDataSource: MainRemoteDataSource):MainDataRepository{


    override suspend fun getTopHeadLines(): Result<TopHeadlines>{
        val localData = localMainLocalDataSource.getTopHeadLines()
        return if(localData.isSuccess){
            localData
        } else{
            val remoteData = remoteMainDataSource.getTopHeadLines()
            val data = remoteData.getOrNull()
            if(remoteData.isSuccess && data!=null && !data.articles.isNullOrEmpty()){
                localMainLocalDataSource.writeDataToFile(data)
            }
            localMainLocalDataSource.getTopHeadLines()
        }
    }

}