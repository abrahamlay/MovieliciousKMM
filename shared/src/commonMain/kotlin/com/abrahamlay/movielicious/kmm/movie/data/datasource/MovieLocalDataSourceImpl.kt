package com.abrahamlay.movielicious.kmm.movie.data.datasource

import com.abrahamlay.movielicious.kmm.db.MovieEntity
import com.abrahamlay.movielicious.kmm.db.MovieQueries
import com.abrahamlay.movielicious.kmm.db.toDomain
import com.abrahamlay.movielicious.kmm.db.toEntity
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
            movieQueries.insertMovie(movie.toEntity(MOVIE_TYPE_POPULAR))
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
            movieQueries.insertMovie(movie.toEntity(MOVIE_TYPE_TOP_RATED))
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
            movieQueries.insertMovie(movie.toEntity(MOVIE_TYPE_NOW_PLAYING))
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