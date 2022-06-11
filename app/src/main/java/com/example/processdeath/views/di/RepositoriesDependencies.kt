package com.example.processdeath.views.di

import com.data.login.repositories.LoginDataStoreRepository
import com.data.login.repositories.LoginDataStoreRepositoryImpl
import com.data.login.repositories.LoginRepository
import com.data.login.repositories.LoginRepositoryImpl
import com.data.signUp.repositories.SignUpRepository
import com.data.signUp.repositories.SignUpRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoriesDependencies {

    @Binds
    internal abstract fun provideLoginRepoDependency(loginRepositoryImpl: LoginRepositoryImpl):LoginRepository

    @Binds
    internal abstract fun provideLoginDataStoreRepoDependency(loginDataStoreRepositoryImpl: LoginDataStoreRepositoryImpl):LoginDataStoreRepository

    @Binds
    internal abstract fun provideSignUpRepoDependency(signUpRepositoryImpl: SignUpRepositoryImpl): SignUpRepository
}