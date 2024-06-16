package com.abrahamlay.movielicious.kmm.movie.data.repository

import com.abrahamlay.movielicious.kmm.movie.data.datasource.MovieRemoteDataSourceImpl
import com.abrahamlay.movielicious.kmm.movie.domain.abstraction.datasource.MovieRemoteDataSource
import com.abrahamlay.movielicious.kmm.movie.domain.abstraction.repository.MovieRepository
import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie

class MovieRepositoryImpl(private val dataSourceImpl: MovieRemoteDataSource) : MovieRepository {
    override suspend fun getPopular(): List<Movie> = dataSourceImpl.getPopular()

    override suspend fun getTopRated(): List<Movie> = dataSourceImpl.getTopRated()

    override suspend fun getNowPlaying(): List<Movie> = dataSourceImpl.getNowPlaying()

}