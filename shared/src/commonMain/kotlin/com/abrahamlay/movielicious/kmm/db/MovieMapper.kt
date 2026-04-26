package com.abrahamlay.movielicious.kmm.db

import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie as DomainMovie
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val json = Json { ignoreUnknownKeys = true }

fun Movie.toDomain(): DomainMovie {
    return DomainMovie(
        voteCount = vote_count.toInt(),
        id = id.toInt(),
        video = video == 1L,
        voteAverage = vote_average,
        title = title,
        popularity = popularity,
        posterPath = poster_path,
        originalLanguage = original_language,
        originalTitle = original_title,
        genreIds = genre_ids?.let { json.decodeFromString<List<Int>>(it) } ?: emptyList(),
        backdropPath = backdrop_path,
        adult = adult == 1L,
        overview = overview,
        releaseDate = release_date
    )
}

fun DomainMovie.genreIdsJson(): String = json.encodeToString(genreIds)
