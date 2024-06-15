package com.abrahamlay.movielicious.kmm.core.datainfra.remote

import com.abrahamlay.movielicious.kmm.core.datacore.network.CustomApiException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object BaseNetworkImpl {
    val networkClient = NetworkClient {
        install(Logging)
        install(ContentNegotiation){
            json(Json{
                prettyPrint = true
                isLenient = true
            })
        }
    }

    fun getPath(url: String) : String {
        val baseUrl = "https://api.themoviedb.org/"
        return "$baseUrl$url"
    }

    @Throws(Exception::class)
    fun <Data> validateResponse(baseResponse: BaseResponse<Data>): Data {
        validateResponse(baseResponse as BaseEmptyBodyResponse)
        return baseResponse.data ?: throw CustomApiException(
            baseResponse.status,
            baseResponse.message.orEmpty()
        )
    }

    @Throws(Exception::class)
    fun validateResponse(baseResponse: BaseEmptyBodyResponse) {
        if (baseResponse.status != "00") throw CustomApiException(
            baseResponse.status,
            baseResponse.message.orEmpty()
        )
    }
}