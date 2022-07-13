package com.data.main.datasource

import com.data.CacheUtils
import com.example.mylibrary.main.TopHeadlines
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import javax.inject.Inject

private const val API_KEY = "96c57c47d16049b780d8c51beb9808e0"


interface MainDataSource {
    suspend fun getTopHeadLines():Result<TopHeadlines>
}

interface MainLocalDataSource:MainDataSource{
    suspend fun writeDataToFile(data:TopHeadlines)
}

class MainLocalDataSourceImpl @Inject constructor(private val cacheUtils: CacheUtils):MainLocalDataSource{

    private val fileName:String = "latestNews"
    private val expiration = 10*60*1000L
    override suspend fun getTopHeadLines(): Result<TopHeadlines> {
         val data = cacheUtils.readDataUnderExpiration<TopHeadlines>(expiration,fileName)
         return data?.let {
             Result.success(it)
         }?: Result.failure(Throwable())
    }

    override suspend fun writeDataToFile(data: TopHeadlines) {
        cacheUtils.writeData(fileName,data)
    }
}


interface MainRemoteDataSource:MainDataSource

class MainRemoteDataSourceImpl @Inject constructor(private val httpClient: HttpClient):MainRemoteDataSource{

    companion object{
        private const val URL = "https://newsapi.org/v2/top-headlines?country=in&apiKey=$API_KEY"
    }

    override suspend fun getTopHeadLines(): Result<TopHeadlines> {
        return try {
              Result.success(httpClient.get(URL).body())
        }
        catch (e:Exception){
            Result.failure(Throwable(e.localizedMessage))
        }
    }
}