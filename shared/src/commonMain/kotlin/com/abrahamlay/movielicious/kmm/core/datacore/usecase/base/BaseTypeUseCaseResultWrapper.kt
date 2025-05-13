package com.abrahamlay.movielicious.kmm.core.datacore.usecase.base

import com.abrahamlay.movielicious.kmm.core.datacore.usecase.DataStatus
import com.abrahamlay.movielicious.kmm.core.datacore.usecase.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseTypeUseCaseResultWrapper<Type : Any> : BaseUseCase() {
    abstract fun asFlow(): Flow<ResultWrapper<Type>>

    fun asFlowValue(defaultValue: Type): Flow<Type> = flow {
        asFlow().collect {
            if (it.status == DataStatus.SUCCESS) emit(it.data ?: defaultValue)
        }
    }
}