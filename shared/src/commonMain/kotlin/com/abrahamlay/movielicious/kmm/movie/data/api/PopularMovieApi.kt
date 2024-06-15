package com.abrahamlay.movielicious.kmm.movie.data.api

import com.abrahamlay.movielicious.kmm.core.datainfra.remote.BaseNetworkImpl
import com.abrahamlay.movielicious.kmm.core.datainfra.remote.BaseResponse
import com.abrahamlay.movielicious.kmm.movie.constants.Constants
import com.abrahamlay.movielicious.kmm.movie.data.dtos.MovieDto
import io.ktor.client.call.body
import io.ktor.client.request.get

class PopularMovieApi {
    private val url = BaseNetworkImpl.getPath("3/movie/popular")
    suspend fun getPopular(): BaseResponse<MovieDto> {
        return BaseNetworkImpl.networkClient.get(url){
            url {
                parameters.append("api_key", Constants.API_KEY)
            }
        }.body()
    }
}