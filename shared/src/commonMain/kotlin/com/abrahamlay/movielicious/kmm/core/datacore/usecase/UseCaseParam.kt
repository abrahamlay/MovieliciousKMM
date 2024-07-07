package com.abrahamlay.movielicious.kmm.core.datacore.usecase

import com.abrahamlay.movielicious.kmm.core.datacore.network.CustomApiException
import com.abrahamlay.movielicious.kmm.core.datacore.network.IoException
import com.abrahamlay.movielicious.kmm.core.datacore.network.getErrorCode
import com.abrahamlay.movielicious.kmm.core.datacore.network.getErrorMessage
import com.abrahamlay.movielicious.kmm.core.datacore.usecase.base.BaseTypeUseCaseParam
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class UseCaseParam<Type : Any, Param : Any> : BaseTypeUseCaseParam<Type, Param>() {

    protected abstract suspend fun build(param: Param): Type

    override fun asFlow(param: Param): Flow<DataResult<Type>> = flow {
        emit(DataResult.Loading)
        channelFlow {
            launch(Dispatchers.IO) {
                val value = withExecutionTimeout { build(param) }
                send(value)
            }
        }.collect { emit(DataResult.Success(it)) }
    }.catch { cause ->
        if (cause is CustomApiException) {
            defaultException?.invoke(cause.getErrorCode(), cause.getErrorMessage())
        }
        emit(DataResult.Failure(cause.getErrorCode(), cause.getErrorMessage()))
    }

    operator fun invoke(param: Param, callback: (DataResult<Type>) -> Unit) {
        getScope().launch {
            try {
                callback(DataResult.Loading)
                val result = withContext(Dispatchers.IO) { withExecutionTimeout { build(param) } }
                callback(DataResult.Success(result))
            } catch (t: CustomApiException) {
                defaultException?.invoke(t.getErrorCode(), t.getErrorMessage())
                callback(DataResult.Failure(t.getErrorCode(), t.getErrorMessage()))
            } catch (t: IoException) {
                callback(DataResult.Failure(t.getErrorCode(), t.getErrorMessage()))
            } catch (e: Exception) {
                callback(DataResult.Failure(e.getErrorCode(), e.getErrorMessage()))
            }
        }
    }

    fun invoke(
        param: Param,
        onLoading: () -> Unit,
        onSuccess: (DataResult.Success<Type>) -> Unit,
        onFailure: (DataResult.Failure) -> Unit
    ) {
        getScope().launch {
            try {
                onLoading.invoke()
                val result = withContext(Dispatchers.IO) { withExecutionTimeout { build(param) } }
                onSuccess(DataResult.Success(result))
            } catch (t: CustomApiException) {
                defaultException?.invoke(t.getErrorCode(), t.getErrorMessage())
                onFailure(DataResult.Failure(t.getErrorCode(), t.getErrorMessage()))
            } catch (t: IoException) {
                onFailure(DataResult.Failure(t.getErrorCode(), t.getErrorMessage()))
            } catch (e: Exception) {
                onFailure(DataResult.Failure(e.getErrorCode(), e.getErrorMessage()))
            }
        }
    }

    fun getValue(param: Param, callback: Type.() -> Unit) {
        invoke(param) {
            if (it is DataResult.Success) {
                callback(it.result)
            }
        }
    }

}

