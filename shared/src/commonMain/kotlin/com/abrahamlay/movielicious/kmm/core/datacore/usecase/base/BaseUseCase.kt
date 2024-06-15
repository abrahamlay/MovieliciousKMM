package com.abrahamlay.movielicious.kmm.core.datacore.usecase.base


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withTimeout

/**
 * Shameless copy from UseCase Class
 */

abstract class BaseUseCase {

    companion object {
        const val MILLISECONDS = 1000L
        const val timeout = 20
    }

    open val timeOutInSeconds = timeout
    private fun getExecutionTimeOut() = timeOutInSeconds * MILLISECONDS

    var coroutineScope: CoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    fun getScope(): CoroutineScope {
        if (!coroutineScope.isActive) {
            initialize()
        }
        return coroutineScope
    }

    fun initialize() {
        coroutineScope = CoroutineScope(Job() + Dispatchers.Main)
    }

    var defaultException: ((Int, String) -> Unit)? = null

    internal suspend fun <T> withExecutionTimeout(func: suspend () -> T): T {
        return withTimeout(getExecutionTimeOut()) {
            func.invoke()
        }
    }

    fun dispose() {
        coroutineScope.cancel()
    }
}
