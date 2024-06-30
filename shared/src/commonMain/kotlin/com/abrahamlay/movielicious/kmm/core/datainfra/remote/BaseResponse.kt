package com.abrahamlay.movielicious.kmm.core.datainfra.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
open class BaseEmptyBodyResponse(
    @SerialName("message") open var message: String? = null,
    @SerialName("status") open var status: String? = null
)


@Serializable
open class BaseResponse<T>(
    @SerialName("data") open var data: T? = null
) : BaseEmptyBodyResponse()


@Serializable
open class BaseEmptyCollectionBodyResponse(
    @SerialName("dates") open var dates: Dates = Dates(),
    @SerialName("page") open var page: Int = 0,
    @SerialName("total_results") open val totalResults: Int = 0,
    @SerialName("total_pages") open var totalPages: Int = 0,
) : BaseEmptyBodyResponse()

@Serializable
class Dates(
    @SerialName("maximum")
    open var maximum: String = "",
    @SerialName("minimum")
    open var minimum: String = ""
)

@Serializable
abstract class BaseCollectionResponse<DATA>(
    @SerialName("results") open var results: List<DATA>? = null,
) : BaseEmptyCollectionBodyResponse()

