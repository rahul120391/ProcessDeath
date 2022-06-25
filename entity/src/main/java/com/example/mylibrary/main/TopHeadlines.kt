package com.example.mylibrary.main


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
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

@Parcelize
@Serializable
data class Source(
    @SerialName("id")
    val id: String?,
    @SerialName("name")
    val name: String?
):Parcelable


