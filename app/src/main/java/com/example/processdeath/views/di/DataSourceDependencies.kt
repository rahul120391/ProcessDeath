package com.example.processdeath.views.di

import com.data.login.datasource.LoginRemoteDataSource
import com.data.login.datasource.LoginRemoteDataSourceImpl
import com.data.signUp.datasource.SignUpDataSource
import com.data.signUp.datasource.SignUpDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataSourceDependencies {

    @Binds
    internal abstract fun provideLoginDataSourceDependency(loginDataSourceImpl: LoginRemoteDataSourceImpl): LoginRemoteDataSource


    @Binds
    internal abstract fun provideSignUpDataSourceDependency(signUpDataSourceImpl: SignUpDataSourceImpl): SignUpDataSource
}