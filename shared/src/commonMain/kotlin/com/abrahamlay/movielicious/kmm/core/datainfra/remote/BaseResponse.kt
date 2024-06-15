package com.abrahamlay.movielicious.kmm.core.datainfra.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class BaseEmptyBodyResponse (
    @SerialName("message") open var message : String? = null,
    @SerialName("status") open var status : String? = null
)

@Serializable
open class BaseResponse<T>(
    @SerialName("data") open var data: T? = null
) : BaseEmptyBodyResponse()
