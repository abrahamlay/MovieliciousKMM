package com.abrahamlay.movielicious.kmm.db

import app.cash.sqldelight.Query
import app.cash.sqldelight.db.SqlDriver
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

expect class DriverFactory {
    val databaseName: String
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): AppDatabase {
    val driver = driverFactory.createDriver()
    val database = AppDatabase(driver)
    return database
}

class AppDatabase(private val driver: SqlDriver) {

    private val json = Json { ignoreUnknownKeys = true }

    fun movieQueries(): MovieQueries = MovieQueries(driver, json)
}

class MovieQueries(
    private val driver: SqlDriver,
    private val json: Json
) {
    fun insertMovie(movie: MovieEntity) {
        driver.execute(
            insertMovie,
            listOf(
                movie.id,
                movie.voteCount,
                if (movie.video) 1L else 0L,
                movie.voteAverage,
                movie.title,
                movie.popularity,
                movie.posterPath,
                movie.originalLanguage,
                movie.originalTitle,
                json.encodeToString(movie.genreIds),
                movie.backdropPath,
                if (movie.adult) 1L else 0L,
                movie.overview,
                movie.releaseDate,
                movie.type
            )
        )
    }

    fun getMoviesByType(type: String): Query<MovieEntity> {
        return driver.executeQuery(
            getMoviesByType,
            listOf(type),
            0
        ) { cursor ->
            MovieEntity(
                id = cursor.getLong(0)!!.toInt(),
                voteCount = cursor.getLong(1)!!.toInt(),
                video = cursor.getLong(2) == 1L,
                voteAverage = cursor.getDouble(3),
                title = cursor.getString(4),
                popularity = cursor.getDouble(5),
                posterPath = cursor.getString(6),
                originalLanguage = cursor.getString(7),
                originalTitle = cursor.getString(8),
                genreIds = cursor.getString(9)?.let { json.decodeFromString<List<Int>>(it) } ?: emptyList(),
                backdropPath = cursor.getString(10),
                adult = cursor.getLong(11) == 1L,
                overview = cursor.getString(12),
                releaseDate = cursor.getString(13),
                type = cursor.getString(14) ?: "POPULAR"
            )
        }
    }

    fun getMovieById(id: Int): Query<MovieEntity> {
        return driver.executeQuery(
            getMovieById,
            listOf(id),
            0
        ) { cursor ->
            MovieEntity(
                id = cursor.getLong(0)!!.toInt(),
                voteCount = cursor.getLong(1)!!.toInt(),
                video = cursor.getLong(2) == 1L,
                voteAverage = cursor.getDouble(3),
                title = cursor.getString(4),
                popularity = cursor.getDouble(5),
                posterPath = cursor.getString(6),
                originalLanguage = cursor.getString(7),
                originalTitle = cursor.getString(8),
                genreIds = cursor.getString(9)?.let { json.decodeFromString<List<Int>>(it) } ?: emptyList(),
                backdropPath = cursor.getString(10),
                adult = cursor.getLong(11) == 1L,
                overview = cursor.getString(12),
                releaseDate = cursor.getString(13),
                type = cursor.getString(14) ?: "POPULAR"
            )
        }
    }

    fun deleteAllMovies() {
        driver.execute(deleteAllMovies, emptyList())
    }

    fun deleteMoviesByType(type: String) {
        driver.execute(deleteMoviesByType, listOf(type))
    }
}

data class MovieEntity(
    val id: Int,
    val voteCount: Int,
    val video: Boolean,
    val voteAverage: Double,
    val title: String?,
    val popularity: Double,
    val posterPath: String?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val genreIds: List<Int>,
    val backdropPath: String?,
    val adult: Boolean,
    val overview: String?,
    val releaseDate: String?,
    val type: String = "POPULAR"
)