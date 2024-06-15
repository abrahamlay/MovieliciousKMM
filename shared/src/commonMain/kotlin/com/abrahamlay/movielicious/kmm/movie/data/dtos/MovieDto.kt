package com.abrahamlay.movielicious.kmm.movie.data.dtos

import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive


@Serializable
data class MovieDto(
    @SerialName("page")
    var page: Int = 0,

    @SerialName("total_results")
    val totalResults: Int = 0,

    @SerialName("total_pages")
    var totalPages: Int = 0,

    @SerialName("results")
    var results: List<Movie>
) {

    @Serializable
    class Movie(
        @SerialName("vote_count")
        var voteCount: Int = 0,

        @SerialName("id")
        var id: Int = 0,

        @SerialName("video")
        var video: Boolean = false,

        @SerialName("vote_average")
        var voteAverage: Double = 0.0,

        @SerialName("title")
        var title: String? = null,

        @SerialName("popularity")
        var popularity: Double = 0.0,

        @SerialName("poster_path")
        var posterPath: String? = null,

        @SerialName("original_language")
        var originalLanguage: String? = null,

        @SerialName("original_title")
        var originalTitle: String? = null,

        @SerialName("genre_ids")
        var genreIds: List<Int>,

        @SerialName("backdrop_path")
        var backdropPath: String? = null,

        @SerialName("adult")
        var adult: Boolean = false,

        @SerialName("overview")
        var overview: String? = null,

        @SerialName("release_date")
        var releaseDate: String? = null
    )
}


fun MovieDto.toData(): List<Movie> {
    return this.results.map { movie ->
        Movie(
            movie.voteCount,
            movie.id,
            movie.video,
            movie.voteAverage,
            movie.originalTitle,
            movie.popularity,
            movie.posterPath,
            movie.originalLanguage,
            movie.originalTitle,
            movie.genreIds,
            movie.backdropPath,
            movie.adult,
            movie.overview,
            movie.releaseDate

        )
    }
}
