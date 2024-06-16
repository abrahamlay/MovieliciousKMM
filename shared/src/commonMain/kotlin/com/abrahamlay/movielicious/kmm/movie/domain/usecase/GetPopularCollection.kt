package com.abrahamlay.movielicious.kmm.movie.domain.usecase

import com.abrahamlay.movielicious.kmm.core.datacore.usecase.UseCase
import com.abrahamlay.movielicious.kmm.movie.domain.abstraction.repository.MovieRepository
import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie

class GetPopularCollection(private val movieRepository: MovieRepository): UseCase<List<Movie>>() {
    override suspend fun build(): List<Movie> {
        return movieRepository.getPopular()
    }

}