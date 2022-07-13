package com.domain.login

import com.data.login.repositories.LoginDataStoreRepository
import com.domain.base.BaseUseCaseWithoutRequestParam
import com.domain.base.Result
import javax.inject.Inject


interface LoginAvailabilityUseCase : BaseUseCaseWithoutRequestParam<Boolean>

class LoginAvailabilityUseCaseImpl @Inject constructor(private val loginDataStoreRepository: LoginDataStoreRepository):LoginAvailabilityUseCase{

    override suspend fun executeUseCase(): Result<Boolean> =  Result.Success(loginDataStoreRepository.readData())
}