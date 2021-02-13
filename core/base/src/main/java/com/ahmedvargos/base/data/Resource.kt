package com.ahmedvargos.base.data

sealed class Resource<out T> {

    data class Success<T>(val data: T? = null, val source: DataSource) : Resource<T>()
    data class Failure(val failureData: FailureData) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
    object None : Resource<Nothing>()
}

enum class DataSource {
    CACHE,
    REMOTE
}
