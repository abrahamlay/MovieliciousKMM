package com.abrahamlay.movielicious.kmm.movie.domain.usecase

import com.abrahamlay.movielicious.kmm.core.datacore.usecase.UseCaseResultWrapper
import com.abrahamlay.movielicious.kmm.movie.domain.abstraction.repository.MovieRepository
import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie

class GetPopularCollection(private val movieRepository: MovieRepository): UseCaseResultWrapper<List<Movie>>() {
    override suspend fun build(): List<Movie> {
        return movieRepository.getPopular()
    }

}