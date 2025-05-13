package com.abrahamlay.movielicious.kmm.core.datacore.usecase

sealed class DataResult<out T : Any> {
    data class Success<T : Any>(val result: T) : DataResult<T>()
    object Loading : DataResult<Nothing>()
    data class Failure(val errorCode: Int, val message: String) : DataResult<Nothing>()
}

enum class DataStatus {
    SUCCESS, ERROR, LOADING
}

data class ResultWrapper<T>(
    val status: DataStatus,
    val data: T? = null,
    val errorCode: Int = 0,
    val errorMessage: String = "Unknown Error"
)