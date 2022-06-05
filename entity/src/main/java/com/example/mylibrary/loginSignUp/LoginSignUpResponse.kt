package com.example.mylibrary.loginSignUp


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginSignUpResponse(
    @SerialName("data")
    val `data`: List<Data>? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("status")
    val status: String? = null
)

@Serializable
data class Data(
    @SerialName("hobby")
    val hobby: String? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String? =null,
    @SerialName("password")
    val password: String?,
    @SerialName("username")
    val username: String?
)