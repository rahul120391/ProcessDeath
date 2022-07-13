package com.domain.signup

import com.data.signUp.repositories.SignUpRepository
import com.domain.base.BaseUseCaseWithRequestParam
import com.domain.base.Result
import com.example.mylibrary.loginSignUp.LoginSignUpResponse
import com.model.signup.SignUpBody
import javax.inject.Inject

interface SignUpUseCase : BaseUseCaseWithRequestParam<SignUpBody, LoginSignUpResponse>

class SignUpUseCaseImpl @Inject constructor(private val signUpRepository: SignUpRepository):SignUpUseCase{

    override suspend fun executeUseCase(params: SignUpBody): Result<LoginSignUpResponse> {
        return run {
            val result = signUpRepository.signUp(signUpBody = params)
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