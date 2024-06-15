package com.abrahamlay.movielicious.kmm.movie.data.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ReviewDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("page")
    val page: Int?,
    @SerialName("results")
    val results: List<ResultReview>?,
    @SerialName("total_pages")
    val totalPages: Int?,
    @SerialName("total_results")
    val totalResults: Int?
)