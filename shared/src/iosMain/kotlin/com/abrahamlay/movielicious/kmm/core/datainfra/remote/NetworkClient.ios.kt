package com.abrahamlay.movielicious.kmm.core.datainfra.remote

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.HttpRequest
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import korlibs.io.net.http.Http


actual fun NetworkClient(config: HttpClientConfig<*>.() -> Unit)= HttpClient(Darwin) {
    config(this)
    defaultRequest {
        header(HttpHeaders.UserAgent, "KMM-TMDB-App")
    }
    engine{
        configureRequest{
            setAllowsCellularAccess(true)
        }
    }
}