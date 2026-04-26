package com.abrahamlay.movielicious.kmm.movie.data.repository

import com.abrahamlay.movielicious.kmm.movie.data.datasource.MovieLocalDataSourceImpl
import com.abrahamlay.movielicious.kmm.movie.data.datasource.MovieRemoteDataSourceImpl
import com.abrahamlay.movielicious.kmm.movie.domain.abstraction.repository.MovieLocalDataSource
import com.abrahamlay.movielicious.kmm.movie.domain.abstraction.repository.MovieRepository
import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie

class MovieRepositoryImpl(
    private val remoteDataSource: MovieRemoteDataSourceImpl,
    private val localDataSource: MovieLocalDataSource
) : MovieRepository {

    override suspend fun getPopular(): List<Movie> {
        return try {
            val remoteMovies = remoteDataSource.getPopular()
            localDataSource.savePopularMovies(remoteMovies)
            remoteMovies
        } catch (e: Exception) {
            localDataSource.getPopularMovies().ifEmpty { throw e }
        }
    }

    override suspend fun getTopRated(): List<Movie> {
        return try {
            val remoteMovies = remoteDataSource.getTopRated()
            localDataSource.saveTopRatedMovies(remoteMovies)
            remoteMovies
        } catch (e: Exception) {
            localDataSource.getTopRatedMovies().ifEmpty { throw e }
        }
    }

    override suspend fun getNowPlaying(): List<Movie> {
        return try {
            val remoteMovies = remoteDataSource.getNowPlaying()
            localDataSource.saveNowPlayingMovies(remoteMovies)
            remoteMovies
        } catch (e: Exception) {
            localDataSource.getNowPlayingMovies().ifEmpty { throw e }
        }
    }
}