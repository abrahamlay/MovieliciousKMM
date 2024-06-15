package com.abrahamlay.movielicious.kmm.core.datacore.network

fun Throwable.getErrorMessage(): String = when (this) {
    is IoException -> fetchErrorMessage()
    is NetworkException -> fetchErrorMessage()
    is CustomApiException -> apiMessage
    else -> message.orEmpty()
}

fun Throwable.getErrorCode() = when (this) {
    is IoException -> code()
    is NetworkException -> code()
    is CustomApiException -> code
    else -> ApiConstant.HTTP_BAD_REQUEST
}
