package com.abrahamlay.movielicious.kmm.movie.data.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class VideoDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("results")
    val results: List<ResultVideo>?
)