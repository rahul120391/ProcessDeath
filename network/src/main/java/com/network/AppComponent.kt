package com.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppComponent {

    @Provides
    @Singleton
    fun providesClient():HttpClient = KtorService.provideClient()
}