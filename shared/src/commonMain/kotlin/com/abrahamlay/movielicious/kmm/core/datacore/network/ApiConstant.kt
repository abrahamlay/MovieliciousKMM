package com.abrahamlay.movielicious.kmm.core.datacore.network

object ApiConstant {
    const val TIME_OUT_IN_SECONDS: Long = 300
    const val REQUEST_COUNT_LIMIT = 3

    const val HTTP_RESPONSE_UNAUTHORIZED = 401
    const val HTTP_RESPONSE_FORBIDDEN = 403
    const val HTTP_BAD_REQUEST = 400
    const val HTTP_TIME_OUT = 408

    const val HTTP_RESPONSE_OK = 200
    const val HTTP_RESPONSE_CREATED = 201
    const val HTTP_RESPONSE_NO_CONTENT = 204
    const val HTTP_RESPONSE_NOT_MODIFIED = 304

    const val HTTP_MESSAGE_BAD_REQUEST = "Terjadi kesalahan, silahkan coba lagi"
}

