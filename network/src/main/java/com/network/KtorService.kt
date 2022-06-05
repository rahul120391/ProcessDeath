package com.network

import io.ktor.client.*
import io.ktor.client.engine.cio.*
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
        fun provideClient():HttpClient{
            return HttpClient(CIO) {
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
