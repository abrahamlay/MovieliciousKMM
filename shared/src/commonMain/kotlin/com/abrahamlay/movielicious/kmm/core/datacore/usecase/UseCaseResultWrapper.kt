package com.abrahamlay.movielicious.kmm.core.datacore.usecase

import com.abrahamlay.movielicious.kmm.core.datacore.network.CustomApiException
import com.abrahamlay.movielicious.kmm.core.datacore.network.IoException
import com.abrahamlay.movielicious.kmm.core.datacore.network.getErrorCode
import com.abrahamlay.movielicious.kmm.core.datacore.network.getErrorMessage
import com.abrahamlay.movielicious.kmm.core.datacore.usecase.base.BaseTypeUseCase
import com.abrahamlay.movielicious.kmm.core.datacore.usecase.base.BaseTypeUseCaseResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class UseCaseResultWrapper<Type : Any> : BaseTypeUseCaseResultWrapper<Type>() {

    protected abstract suspend fun build(): Type

    override fun asFlow(): Flow<ResultWrapper<Type>> = flow {
        emit(ResultWrapper(status = DataStatus.LOADING))
        channelFlow {
            launch(Dispatchers.IO) {
                val value = withExecutionTimeout { build() }
                send(value)
            }
        }.collect { emit(ResultWrapper(status = DataStatus.SUCCESS, it)) }
    }.catch { cause ->
        if (cause is CustomApiException) {
            defaultException?.invoke(cause.getErrorCode(), cause.getErrorMessage())
        }
        emit(
            ResultWrapper(
                status = DataStatus.ERROR,
                errorCode = cause.getErrorCode(),
                errorMessage = cause.getErrorMessage()
            )
        )
    }

    operator fun invoke(callback: (ResultWrapper<Type>) -> Unit) {
        getScope().launch {
            try {
                callback(ResultWrapper(DataStatus.LOADING))
                val result = withContext(Dispatchers.IO) { withExecutionTimeout { build() } }
                callback(ResultWrapper(DataStatus.SUCCESS, result))
            } catch (t: CustomApiException) {
                defaultException?.invoke(t.getErrorCode(), t.getErrorMessage())
                callback(
                    ResultWrapper(
                        status = DataStatus.ERROR,
                        errorCode = t.getErrorCode(),
                        errorMessage = t.getErrorMessage()
                    )
                )
            } catch (t: IoException) {
                callback(
                    ResultWrapper(
                        status = DataStatus.ERROR,
                        errorCode = t.getErrorCode(),
                        errorMessage = t.getErrorMessage()
                    )
                )
            } catch (e: Exception) {
                callback(
                    ResultWrapper(
                        status = DataStatus.ERROR,
                        errorCode = e.getErrorCode(),
                        errorMessage = e.getErrorMessage()
                    )
                )
            }
        }
    }

    fun invoke(
        onLoading: () -> Unit,
        onSuccess: (ResultWrapper<Type>) -> Unit,
        onFailure: (ResultWrapper<Type>) -> Unit
    ) {
        getScope().launch {
            try {
                onLoading.invoke()
                val result = withContext(Dispatchers.IO) { withExecutionTimeout { build() } }
                onSuccess(ResultWrapper(DataStatus.SUCCESS, result))
            } catch (t: CustomApiException) {
                defaultException?.invoke(t.getErrorCode(), t.getErrorMessage())
                onFailure(
                    ResultWrapper(
                        status = DataStatus.ERROR,
                        errorCode = t.getErrorCode(),
                        errorMessage = t.getErrorMessage()
                    )
                )
            } catch (t: IoException) {
                onFailure(
                    ResultWrapper(
                        status = DataStatus.ERROR,
                        errorCode = t.getErrorCode(),
                        errorMessage = t.getErrorMessage()
                    )
                )
            } catch (e: Exception) {
                onFailure(
                    ResultWrapper(
                        status = DataStatus.ERROR,
                        errorCode = e.getErrorCode(),
                        errorMessage = e.getErrorMessage()
                    )
                )
            }
        }
    }

    fun getValue(defaultValue: Type, callback: Type.() -> Unit) {
        invoke {
            if (it.status == DataStatus.SUCCESS) {
                callback(it.data ?: defaultValue)
            }
        }
    }

}

