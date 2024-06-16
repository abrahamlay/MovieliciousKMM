package com.abrahamlay.movielicious.kmm.movie.domain.abstraction.repository

import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie

interface MovieRepository {

    suspend fun getPopular(): List<Movie>
    suspend fun getTopRated(): List<Movie>
    suspend fun getNowPlaying(): List<Movie>
}