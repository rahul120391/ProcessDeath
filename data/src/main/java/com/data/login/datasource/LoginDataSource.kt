package com.data.login.datasource

import com.data.extensions.toPojo
import com.example.mylibrary.loginSignUp.LoginSignUpResponse
import com.model.login.LoginBody
import io.ktor.client.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import javax.inject.Inject


interface LoginDataSource {

    suspend fun login(loginBody: LoginBody):Result<LoginSignUpResponse> = Result.failure(Throwable())
}

interface LoginRemoteDataSource:LoginDataSource

class LoginRemoteDataSourceImpl @Inject constructor(private val httpClient:HttpClient):LoginRemoteDataSource{

    companion object{
        private  const val LOGIN_END_POINT = "simplelogin.php"
    }

    override suspend fun login(loginBody: LoginBody): Result<LoginSignUpResponse> {
          return try {
              val loginSignUpResponse = httpClient.submitForm(url = LOGIN_END_POINT,
                  formParameters = Parameters.build {
                      append("username",loginBody.username)
                      append("password",loginBody.password)
                  }
              ).bodyAsText()
              val pojo = loginSignUpResponse.toPojo<LoginSignUpResponse>()
              Result.success(pojo)
          }
          catch (e:Exception){
              Result.failure(Throwable(e.localizedMessage))
          }
    }
}