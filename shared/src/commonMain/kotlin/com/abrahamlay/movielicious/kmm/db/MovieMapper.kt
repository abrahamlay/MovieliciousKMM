package com.abrahamlay.movielicious.kmm.db

import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie

fun MovieEntity.toDomain(): Movie {
    return Movie(
        voteCount = voteCount,
        id = id,
        video = video,
        voteAverage = voteAverage,
        title = title,
        popularity = popularity,
        posterPath = posterPath,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        genreIds = genreIds,
        backdropPath = backdropPath,
        adult = adult,
        overview = overview,
        releaseDate = releaseDate
    )
}

fun Movie.toEntity(type: String): MovieEntity {
    return MovieEntity(
        id = id,
        voteCount = voteCount,
        video = video,
        voteAverage = voteAverage,
        title = title,
        popularity = popularity,
        posterPath = posterPath,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        genreIds = genreIds,
        backdropPath = backdropPath,
        adult = adult,
        overview = overview,
        releaseDate = releaseDate,
        type = type
    )
}