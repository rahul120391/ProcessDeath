package com.network

import com.chuckerteam.chucker.api.ChuckerInterceptor
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

interface KtorService {
    companion object{
        private const val BASE_URL ="https://demonuts.com/Demonuts/JsonTest/Tennis/"
        fun provideClient(chukerInterceptor: ChuckerInterceptor):HttpClient{
            return HttpClient(OkHttp) {
                engine {
                    config {
                        followRedirects(true)
                    }
                    addInterceptor(
                        chukerInterceptor
                    )
                }
                install(Logging) {
                    logger = Logger.ANDROID
                    level = LogLevel.ALL
                }
                install(ContentNegotiation) {
                    json(Json {
                        isLenient = true
                    })
                }
                defaultRequest {
                    url(BASE_URL)
                    accept(ContentType.Application.Json)
                }
            }
        }
    }
}
