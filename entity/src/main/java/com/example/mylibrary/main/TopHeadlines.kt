package com.example.mylibrary.main


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopHeadlines(
    @SerialName("articles")
    val articles: List<Article>?,
    @SerialName("status")
    val status: String?,
    @SerialName("totalResults")
    val totalResults: Int?
)

@Serializable
data class Source(
    @SerialName("id")
    val id: String?,
    @SerialName("name")
    val name: String?
)


