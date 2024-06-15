package com.abrahamlay.movielicious.kmm.movie.domain.abstraction.datasource

import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie

interface MovieRemoteDataSource {
    suspend fun getPopular(): List<Movie>
}