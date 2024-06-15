package com.abrahamlay.movielicious.kmm.core.datacore.network


class NetworkException(
    private val errorMessage: String?,
    private val code: Int
) : Exception(errorMessage) {
    fun fetchErrorMessage(): String = errorMessage ?: ApiConstant.HTTP_MESSAGE_BAD_REQUEST
    fun code() = code
}