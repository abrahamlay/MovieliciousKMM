package com.abrahamlay.movielicious.kmm.core.datacore.usecase.base

import com.abrahamlay.movielicious.kmm.core.datacore.usecase.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseTypeUseCaseParam<Type : Any, Param : Any> : BaseUseCase() {
    abstract fun asFlow(param: Param): Flow<DataResult<Type>>

    fun asFlowValue(param: Param): Flow<Type> = flow {
        asFlow(param).collect {
            if (it is DataResult.Success) emit(it.result)
        }
    }
}