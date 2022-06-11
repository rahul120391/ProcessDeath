package com.example.processdeath.views.di

import com.domain.SignUpUseCase
import com.domain.SignUpUseCaseImpl
import com.domain.login.LoginAvailabilityUseCase
import com.domain.login.LoginAvailabilityUseCaseImpl
import com.domain.login.LoginUseCase
import com.domain.login.LoginUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCasesDependencies {

    @Binds
    internal abstract fun provideLoginUseCaseDependency(loginUseCaseImpl: LoginUseCaseImpl):LoginUseCase

    @Binds
    internal abstract fun provideLoginAvailabilityUseCaseDependency(loginAvailabilityUseCaseImpl: LoginAvailabilityUseCaseImpl):LoginAvailabilityUseCase

    @Binds
    internal abstract fun provideSignUpUseCaseDependency(signUpUseCaseImpl: SignUpUseCaseImpl):SignUpUseCase
}