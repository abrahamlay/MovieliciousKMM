package com.abrahamlay.movielicious.kmm.movie.domain.abstraction.repository

import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie

interface MovieRepository {
    suspend fun getPopular(): List<Movie>
    suspend fun getTopRated(): List<Movie>
    suspend fun getNowPlaying(): List<Movie>
}

interface MovieLocalDataSource {
    fun getPopularMovies(): List<Movie>
    fun savePopularMovies(movies: List<Movie>)
    fun getTopRatedMovies(): List<Movie>
    fun saveTopRatedMovies(movies: List<Movie>)
    fun getNowPlayingMovies(): List<Movie>
    fun saveNowPlayingMovies(movies: List<Movie>)
    fun clearCache()
}