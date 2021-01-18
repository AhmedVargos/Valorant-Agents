package com.ahmedvargos.remote

import com.ahmedvargos.base.data.FailureData
import com.ahmedvargos.base.data.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart


abstract class NetworkBoundResource<T> {

    @ExperimentalCoroutinesApi
    fun asFlow(): Flow<Resource<T>> = flow {

        if (shouldFetch()) {
            if (shouldFetchWithLocalData()) {
                emit(Resource.loading(data = localFetch()))
            }

            val response = safeApiCall(dispatcher = Dispatchers.IO) {
                remoteFetch()
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
            //get from cash
            emit(Resource.success(data = localFetch()))
        }

    }.onStart {
        //get From cache
        emit(Resource.loading(data = null))
    }


    abstract suspend fun remoteFetch(): T
    abstract suspend fun saveFetchResult(data: T)
    abstract suspend fun localFetch(): T
    open fun onFetchFailed(throwable: Throwable) = Unit
    open fun shouldFetch() = true
    open fun shouldFetchWithLocalData() = false
}


