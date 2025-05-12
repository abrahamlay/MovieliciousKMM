package com.abrahamlay.movielicious.kmm.core.datacore.network

import kotlinx.io.IOException

class CustomApiException(status: String?, val apiMessage: String) : IOException(apiMessage) {
    companion object {
        private val BAD_REQUEST_CODE = 400
    }
    val code = status?.toIntOrNull() ?: BAD_REQUEST_CODE
}
