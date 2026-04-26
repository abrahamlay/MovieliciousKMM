package com.abrahamlay.movielicious.kmm.movie.data.datasource

import com.abrahamlay.movielicious.kmm.db.MovieQueries
import com.abrahamlay.movielicious.kmm.db.genreIdsJson
import com.abrahamlay.movielicious.kmm.db.toDomain
import com.abrahamlay.movielicious.kmm.movie.domain.abstraction.repository.MovieLocalDataSource
import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie

class MovieLocalDataSourceImpl(
    private val movieQueries: MovieQueries
) : MovieLocalDataSource {

    override fun getPopularMovies(): List<Movie> {
        return movieQueries.getMoviesByType(MOVIE_TYPE_POPULAR)
            .executeAsList()
            .map { it.toDomain() }
    }

    override fun savePopularMovies(movies: List<Movie>) {
        movieQueries.deleteMoviesByType(MOVIE_TYPE_POPULAR)
        movies.forEach { movie ->
            movieQueries.insertMovie(
                id = movie.id.toLong(),
                voteCount = movie.voteCount.toLong(),
                video = if (movie.video) 1L else 0L,
                voteAverage = movie.voteAverage,
                title = movie.title,
                popularity = movie.popularity,
                posterPath = movie.posterPath,
                originalLanguage = movie.originalLanguage,
                originalTitle = movie.originalTitle,
                genreIds = movie.genreIdsJson(),
                backdropPath = movie.backdropPath,
                adult = if (movie.adult) 1L else 0L,
                overview = movie.overview,
                releaseDate = movie.releaseDate,
                type = MOVIE_TYPE_POPULAR
            )
        }
    }

    override fun getTopRatedMovies(): List<Movie> {
        return movieQueries.getMoviesByType(MOVIE_TYPE_TOP_RATED)
            .executeAsList()
            .map { it.toDomain() }
    }

    override fun saveTopRatedMovies(movies: List<Movie>) {
        movieQueries.deleteMoviesByType(MOVIE_TYPE_TOP_RATED)
        movies.forEach { movie ->
            movieQueries.insertMovie(
                id = movie.id.toLong(),
                voteCount = movie.voteCount.toLong(),
                video = if (movie.video) 1L else 0L,
                voteAverage = movie.voteAverage,
                title = movie.title,
                popularity = movie.popularity,
                posterPath = movie.posterPath,
                originalLanguage = movie.originalLanguage,
                originalTitle = movie.originalTitle,
                genreIds = movie.genreIdsJson(),
                backdropPath = movie.backdropPath,
                adult = if (movie.adult) 1L else 0L,
                overview = movie.overview,
                releaseDate = movie.releaseDate,
                type = MOVIE_TYPE_TOP_RATED
            )
        }
    }

    override fun getNowPlayingMovies(): List<Movie> {
        return movieQueries.getMoviesByType(MOVIE_TYPE_NOW_PLAYING)
            .executeAsList()
            .map { it.toDomain() }
    }

    override fun saveNowPlayingMovies(movies: List<Movie>) {
        movieQueries.deleteMoviesByType(MOVIE_TYPE_NOW_PLAYING)
        movies.forEach { movie ->
            movieQueries.insertMovie(
                id = movie.id.toLong(),
                voteCount = movie.voteCount.toLong(),
                video = if (movie.video) 1L else 0L,
                voteAverage = movie.voteAverage,
                title = movie.title,
                popularity = movie.popularity,
                posterPath = movie.posterPath,
                originalLanguage = movie.originalLanguage,
                originalTitle = movie.originalTitle,
                genreIds = movie.genreIdsJson(),
                backdropPath = movie.backdropPath,
                adult = if (movie.adult) 1L else 0L,
                overview = movie.overview,
                releaseDate = movie.releaseDate,
                type = MOVIE_TYPE_NOW_PLAYING
            )
        }
    }

    override fun clearCache() {
        movieQueries.deleteAllMovies()
    }

    companion object {
        const val MOVIE_TYPE_POPULAR = "POPULAR"
        const val MOVIE_TYPE_TOP_RATED = "TOP_RATED"
        const val MOVIE_TYPE_NOW_PLAYING = "NOW_PLAYING"
    }
}
