package com.ahmedvargos.base.data


data class Resource<out T>(val status: Status, val data: T?, val messageType: FailureData?) {
    companion object {
        fun <T> success(data: T? = null): Resource<T> {
            return Resource(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(error: FailureData, data: T? = null): Resource<T> {
            return Resource(
                Status.ERROR,
                data,
                error
            )
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(
                Status.LOADING,
                data,
                null
            )
        }
    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}