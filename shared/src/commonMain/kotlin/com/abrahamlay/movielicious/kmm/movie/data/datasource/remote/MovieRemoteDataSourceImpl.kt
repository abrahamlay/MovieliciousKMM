package com.abrahamlay.movielicious.kmm.movie.data.datasource.remote

import com.abrahamlay.movielicious.kmm.core.datainfra.remote.BaseNetworkImpl
import com.abrahamlay.movielicious.kmm.movie.data.api.NowPlayingMovieApi
import com.abrahamlay.movielicious.kmm.movie.data.api.PopularMovieApi
import com.abrahamlay.movielicious.kmm.movie.data.api.TopRatedMovieApi
import com.abrahamlay.movielicious.kmm.movie.data.dtos.toData
import com.abrahamlay.movielicious.kmm.movie.domain.abstraction.datasource.MovieDataSource
import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie

class MovieRemoteDataSourceImpl(
    private val popularMovieApi: PopularMovieApi,
    private val topRatedMovieApi: TopRatedMovieApi,
    private val nowPlayingMovieApi: NowPlayingMovieApi
) : MovieDataSource.Remote {
    override suspend fun getRemotePopular(): List<Movie> {
        val response = popularMovieApi.getPopular()
        val movies = mutableListOf<Movie>()
//        movies.addAll(BaseNetworkImpl.validateCollectionResponse(response).toData())
        movies.addAll(response.results?.toData()?: emptyList())
        return movies
    }

    override suspend fun getRemoteTopRated(): List<Movie> {
        val response = topRatedMovieApi.getTopRatedMovies()
        val movies = mutableListOf<Movie>()
//        movies.addAll(BaseNetworkImpl.validateCollectionResponse(response).toData())
        movies.addAll(response.results?.toData()?: emptyList())
        return movies
    }

    override suspend fun getRemoteNowPlaying(): List<Movie> {
        val response = nowPlayingMovieApi.getNowPlayingMovies()
        val movies = mutableListOf<Movie>()
//        movies.addAll(BaseNetworkImpl.validateCollectionResponse(response).toData())
        movies.addAll(response.results?.toData()?: emptyList())
        return movies
    }
}



