package com.ahmedvargos.remote.utils

import com.ahmedvargos.base.utils.NetworkCodes
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.HttpException
import java.io.IOException

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class GenericError(val code: Int, val message: String? = null) : ResultWrapper<Nothing>()
}

internal suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T?
): ResultWrapper<T?> {
    return withContext(dispatcher) {
        try {
            val call = apiCall.invoke()
            ResultWrapper.Success(call)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            when (throwable) {
                is TimeoutCancellationException -> {
                    ResultWrapper.GenericError(
                        code = NetworkCodes.TIMEOUT_ERROR,
                        message = ErrorCodesMapper.getMessage(NetworkCodes.CONNECTION_ERROR)
                    )
                }
                is IOException -> {
                    ResultWrapper.GenericError(
                        code = NetworkCodes.CONNECTION_ERROR,
                        message = ErrorCodesMapper.getMessage(NetworkCodes.CONNECTION_ERROR)
                    )
                }
                is HttpException -> {
                    val code = throwable.code()
                    ResultWrapper.GenericError(
                        code = code, message = convertErrorBody(throwable)
                    )
                }
                else -> {
                    ResultWrapper.GenericError(
                        code = NetworkCodes.GENERIC_ERROR,
                        message = ErrorCodesMapper.getMessage(NetworkCodes.GENERIC_ERROR)
                    )
                }
            }
        }
    }
}

// for custom error body
private fun convertErrorBody(throwable: HttpException): String? {
    try {
        val json = JSONTokener(throwable.response()?.errorBody()?.string()).nextValue()
        if (json is JSONObject || json is JSONArray) {
            val moshi = Moshi.Builder().build()
            val typeCustom = Types.newParameterizedType(ErrorResponse::class.java)
            val errorResponse = moshi.adapter<ErrorResponse>(typeCustom).fromJson(json.toString())

            errorResponse?.let { return it.message }
        }
        return null
    } catch (exception: Exception) {
        return null
    }
}

class ErrorResponse(val message: String? = "")
