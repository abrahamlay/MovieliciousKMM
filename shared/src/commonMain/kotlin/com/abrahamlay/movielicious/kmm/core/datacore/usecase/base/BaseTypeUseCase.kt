package com.abrahamlay.movielicious.kmm.core.datacore.usecase.base

import com.abrahamlay.movielicious.kmm.core.datacore.usecase.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseTypeUseCase <Type : Any> : BaseUseCase() {
    abstract fun asFlow() : Flow<DataResult<Type>>

    fun asFlowValue() : Flow<Type> = flow {
        asFlow().collect {
            if (it is DataResult.Success) emit(it.result)
        }
    }
}