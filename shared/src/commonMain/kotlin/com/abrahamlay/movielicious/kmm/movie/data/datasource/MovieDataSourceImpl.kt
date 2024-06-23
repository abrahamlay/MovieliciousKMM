package com.abrahamlay.movielicious.kmm.movie.data.datasource

import com.abrahamlay.movielicious.kmm.movie.domain.abstraction.datasource.MovieDataSource
import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie

class MovieDataSourceImpl(
    private val remote: MovieDataSource.Remote
) : MovieDataSource {
    override suspend fun getPopular(): List<Movie> {
        return remote.getRemotePopular()
    }

    override suspend fun getTopRated(): List<Movie> {
        return remote.getRemoteTopRated()
    }

    override suspend fun getNowPlaying(): List<Movie> {
        return remote.getRemoteNowPlaying()
    }
}