package com.abrahamlay.movielicious.kmm.movie.data.datasource

import com.abrahamlay.movielicious.kmm.core.datainfra.remote.BaseNetworkImpl
import com.abrahamlay.movielicious.kmm.movie.data.api.NowPlayingMovieApi
import com.abrahamlay.movielicious.kmm.movie.data.api.PopularMovieApi
import com.abrahamlay.movielicious.kmm.movie.data.api.TopRatedMovieApi
import com.abrahamlay.movielicious.kmm.movie.data.dtos.toData
import com.abrahamlay.movielicious.kmm.movie.domain.abstraction.datasource.MovieRemoteDataSource
import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie

class MovieRemoteDataSourceImpl(
    private val popularMovieApi: PopularMovieApi,
    private val topRatedMovieApi: TopRatedMovieApi,
    private val nowPlayingMovieApi: NowPlayingMovieApi
) : MovieRemoteDataSource {
    override suspend fun getPopular(): List<Movie> {
        val response = popularMovieApi.getPopular()
        val movies = mutableListOf<Movie>()
        movies.addAll(BaseNetworkImpl.validateResponse(response).toData())
        return movies
    }

    override suspend fun getTopRated(): List<Movie> {
        val response = topRatedMovieApi.getTopRatedMovies()
        val movies = mutableListOf<Movie>()
        movies.addAll(BaseNetworkImpl.validateResponse(response).toData())
        return movies
    }

    override suspend fun getNowPlaying(): List<Movie> {
        val response = nowPlayingMovieApi.getNowPlayingMovies()
        val movies = mutableListOf<Movie>()
        movies.addAll(BaseNetworkImpl.validateResponse(response).toData())
        return movies
    }
}