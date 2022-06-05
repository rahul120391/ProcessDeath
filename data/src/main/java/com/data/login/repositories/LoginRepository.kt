package com.data.login.repositories

import com.data.login.datasource.LoginDataSource
import com.example.mylibrary.loginSignUp.LoginSignUpResponse
import com.model.login.LoginBody
import javax.inject.Inject

interface LoginRepository {
    suspend fun login(loginBody: LoginBody):Result<LoginSignUpResponse>
}


class LoginRepositoryImpl @Inject constructor(private val loginDataSource: LoginDataSource):LoginRepository{


    override suspend fun login(loginBody: LoginBody): Result<LoginSignUpResponse> = loginDataSource.login(loginBody)
}