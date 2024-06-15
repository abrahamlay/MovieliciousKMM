package com.abrahamlay.movielicious.kmm.core.datacore.network

import io.ktor.utils.io.errors.IOException

class CustomApiException(status: String?, val apiMessage: String) : IOException(apiMessage) {
    companion object {
        private val BAD_REQUEST_CODE = 400
    }
    val code = status?.toIntOrNull() ?: BAD_REQUEST_CODE
}
