package com.ahmedvargos.remote

import com.ahmedvargos.base.data.FailureData
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.base.utils.SchedulerProvider
import com.ahmedvargos.remote.utils.ResultWrapper
import com.ahmedvargos.remote.utils.safeApiCall
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

abstract class NetworkBoundResource<T>(private val schedulerProvider: SchedulerProvider) {

    @ExperimentalCoroutinesApi
    fun asFlow(): Flow<Resource<T>> = flow {
        // check if should fetch data from remote or not
        if (shouldFetch()) {
            if (shouldFetchWithLocalData()) { // should emit cached date with loading state or not
                emit(Resource.loading(data = localFetch()))
            }

            val response = safeApiCall(dispatcher = schedulerProvider.io()) {
                remoteFetch() // fetch the remote source provided
            }

            when (response) {
                is ResultWrapper.Success -> {
                    response.value?.let {
                        saveFetchResult(response.value)
                    }
                    emit(Resource.success(data = response.value))
                }

                is ResultWrapper.GenericError -> {
                    emit(
                        Resource.error(
                            data = null,
                            error = FailureData(response.code, response.message)
                        )
                    )
                }
            }
        } else {
            // get from cache
            emit(Resource.success(data = localFetch()))
        }
    }.onStart {
        // get from cache
        emit(Resource.loading(data = null))
    }

    abstract suspend fun remoteFetch(): T
    abstract suspend fun saveFetchResult(data: T)
    abstract suspend fun localFetch(): T
    open fun onFetchFailed(throwable: Throwable) = Unit
    open fun shouldFetch() = true
    open fun shouldFetchWithLocalData() = false
}
