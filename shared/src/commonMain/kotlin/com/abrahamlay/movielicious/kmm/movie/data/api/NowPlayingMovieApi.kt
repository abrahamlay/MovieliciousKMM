package com.abrahamlay.movielicious.kmm.movie.data.api

import com.abrahamlay.movielicious.kmm.core.datainfra.remote.BaseNetworkImpl
import com.abrahamlay.movielicious.kmm.movie.constants.Constants
import com.abrahamlay.movielicious.kmm.movie.data.dtos.MovieDto
import io.ktor.client.call.body
import io.ktor.client.request.get

class NowPlayingMovieApi {
    private val url = BaseNetworkImpl.getPath("3/movie/now_playing")
    suspend fun getNowPlayingMovies(): MovieDto {
        return BaseNetworkImpl.networkClient.get(url) {
            url {
                parameters.append("api_key", Constants.API_KEY)
            }
        }.body()
    }
}