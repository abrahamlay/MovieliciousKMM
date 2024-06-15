package com.abrahamlay.movielicious.kmm.movie.data.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResultReview(
    @SerialName("author")
    val author: String?,
    @SerialName("content")
    val content: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("url")
    val url: String?
)