package com.example.processdeath.views.di

import com.data.login.datasource.LoginDataSource
import com.data.login.datasource.LoginDataSourceImpl
import com.data.login.datasource.SignUpDataSource
import com.data.login.datasource.SignUpDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataSourceDependencies {

    @Binds
    internal abstract fun provideLoginDataSourceDependency(loginDataSourceImpl: LoginDataSourceImpl):LoginDataSource

    @Binds
    internal abstract fun provideSignUpDataSourceDependency(signUpDataSourceImpl: SignUpDataSourceImpl):SignUpDataSource
}