package com.abrahamlay.movielicious.kmm.core.datainfra.remote

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

expect fun NetworkClient(config: HttpClientConfig<*>.() -> Unit = {}): HttpClient