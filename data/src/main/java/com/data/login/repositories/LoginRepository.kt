package com.data.login.repositories

import com.data.login.datasource.LoginRemoteDataSource
import com.example.mylibrary.loginSignUp.LoginSignUpResponse
import com.model.login.LoginBody
import javax.inject.Inject

interface LoginRepository {
    suspend fun login(loginBody: LoginBody):Result<LoginSignUpResponse>
}


class LoginRepositoryImpl @Inject constructor(private val loginRemoteDataSource: LoginRemoteDataSource,
                                              private val loginDataStoreRepository: LoginDataStoreRepository
):LoginRepository{



    override suspend fun login(loginBody: LoginBody): Result<LoginSignUpResponse>{
        val result = loginRemoteDataSource.login(loginBody)
        if(result.isSuccess){
          loginDataStoreRepository.saveData(true)
        }
        return result
    }
}