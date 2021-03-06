package com.data.signUp.repositories

import com.data.signUp.datasource.SignUpDataSource
import com.example.mylibrary.loginSignUp.LoginSignUpResponse
import com.model.signup.SignUpBody
import javax.inject.Inject

interface SignUpRepository {
    suspend fun signUp(signUpBody: SignUpBody):Result<LoginSignUpResponse>
}

class SignUpRepositoryImpl @Inject constructor(private val signUpDataSource: SignUpDataSource):SignUpRepository{


    override suspend fun signUp(signUpBody: SignUpBody): Result<LoginSignUpResponse> = signUpDataSource.signUp(signUpBody)
}