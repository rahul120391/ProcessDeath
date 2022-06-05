package com.data.login.datasource

import com.data.login.extensions.toPojo
import com.example.mylibrary.loginSignUp.LoginSignUpResponse
import com.model.signup.SignUpBody
import io.ktor.client.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import javax.inject.Inject

interface SignUpDataSource {
    suspend fun signUp(signUpBody: SignUpBody):Result<LoginSignUpResponse>
}

class SignUpDataSourceImpl @Inject constructor(private val httpClient: HttpClient):SignUpDataSource{

    companion object{
        private  const val SIGN_UP_END_POINT = "simpleregister.php"
    }

    override suspend fun signUp(signUpBody: SignUpBody): Result<LoginSignUpResponse> {
        return try {
            val loginSignUpResponse = httpClient.submitForm(url = SIGN_UP_END_POINT,
                formParameters = Parameters.build {
                    append("username",signUpBody.username)
                    append("password",signUpBody.password)
                    append("name",signUpBody.name)
                    append("hobby",signUpBody.hobby)
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