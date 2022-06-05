package com.domain.login

import com.data.login.repositories.LoginRepository
import com.domain.BaseUseCaseWithRequestParam
import com.domain.Result
import com.example.mylibrary.loginSignUp.LoginSignUpResponse
import com.model.login.LoginBody
import javax.inject.Inject

interface LoginUseCase:BaseUseCaseWithRequestParam<LoginBody,LoginSignUpResponse>

class LoginUseCaseImpl @Inject constructor(private val loginRepository: LoginRepository):LoginUseCase{

    override suspend fun executeUseCase(params: LoginBody): Result<LoginSignUpResponse> {
        return run {
             val result = loginRepository.login(loginBody = params)
             if(result.isSuccess){
                 val data  = result.getOrNull()
                 if(data?.status == "true"){
                     Result.Success(data)
                 }
                 else{
                     Result.Error(data?.message)
                 }
             }
             else {
                 Result.Error()
             }

        }
    }

}