package com.abrahamlay.movielicious.kmm.core.datainfra.remote

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin


actual fun NetworkClient(config: HttpClientConfig<*>.() -> Unit)= HttpClient(Darwin) {
    config(this)
    engine{
        configureRequest{
            setAllowsCellularAccess(true)
        }
    }
}