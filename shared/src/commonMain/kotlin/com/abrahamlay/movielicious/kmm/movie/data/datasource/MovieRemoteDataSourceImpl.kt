package com.abrahamlay.movielicious.kmm.movie.data.datasource

import com.abrahamlay.movielicious.kmm.core.datainfra.remote.BaseNetworkImpl
import com.abrahamlay.movielicious.kmm.movie.data.api.PopularMovieApi
import com.abrahamlay.movielicious.kmm.movie.data.dtos.toData
import com.abrahamlay.movielicious.kmm.movie.domain.abstraction.datasource.MovieRemoteDataSource
import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie

class MovieRemoteDataSourceImpl(
    private val movieApi: PopularMovieApi
) : MovieRemoteDataSource {
    override suspend fun getPopular(): List<Movie> {
        val response = movieApi.getPopular()
        val movies = mutableListOf<Movie>()
        movies.addAll(BaseNetworkImpl.validateResponse(response).toData())
        return movies
    }
}