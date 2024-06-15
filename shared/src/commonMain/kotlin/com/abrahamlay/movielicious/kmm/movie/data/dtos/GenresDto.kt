package com.abrahamlay.movielicious.kmm.movie.data.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GenresDto(
    @SerialName("genres")
    val genres: List<Genre>?
)