package com.abrahamlay.movielicious.kmm.core.datacore.network

import io.ktor.utils.io.errors.IOException

class IoException(
    private val errorMessage: String?,
    private val code: Int
) : IOException(errorMessage?: ApiConstant.HTTP_MESSAGE_BAD_REQUEST) {
    fun fetchErrorMessage(): String = errorMessage ?: ApiConstant.HTTP_MESSAGE_BAD_REQUEST
    fun code() = code
}