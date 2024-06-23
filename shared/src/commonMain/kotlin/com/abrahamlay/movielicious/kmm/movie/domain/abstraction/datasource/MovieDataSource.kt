package com.abrahamlay.movielicious.kmm.movie.domain.abstraction.datasource

import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie

interface MovieDataSource {
    suspend fun getPopular(): List<Movie>
    suspend fun getTopRated(): List<Movie>
    suspend fun getNowPlaying(): List<Movie>

    interface Remote {
        suspend fun getRemotePopular(): List<Movie>
        suspend fun getRemoteTopRated(): List<Movie>
        suspend fun getRemoteNowPlaying(): List<Movie>
    }

    interface Local {

    }

}