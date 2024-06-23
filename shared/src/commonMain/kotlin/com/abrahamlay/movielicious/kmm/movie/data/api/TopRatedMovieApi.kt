package com.abrahamlay.movielicious.kmm.movie.data.api

import com.abrahamlay.movielicious.kmm.core.datainfra.remote.BaseCollectionResponse
import com.abrahamlay.movielicious.kmm.core.datainfra.remote.BaseNetworkImpl
import com.abrahamlay.movielicious.kmm.core.datainfra.remote.BaseResponse
import com.abrahamlay.movielicious.kmm.movie.constants.Constants
import com.abrahamlay.movielicious.kmm.movie.data.dtos.MovieDto
import com.abrahamlay.movielicious.kmm.movie.domain.model.Movie
import io.ktor.client.call.body
import io.ktor.client.request.get

class TopRatedMovieApi {
    private val url = BaseNetworkImpl.getPath("3/movie/top_rated")
    suspend fun getTopRatedMovies(): MovieDto {
        return BaseNetworkImpl.networkClient.get(url){
            url {
                parameters.append("api_key", Constants.API_KEY)
            }
        }.body()
    }
}