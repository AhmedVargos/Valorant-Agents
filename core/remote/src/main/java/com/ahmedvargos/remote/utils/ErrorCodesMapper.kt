package com.ahmedvargos.remote.utils

import android.content.Context
import com.ahmedvargos.base.utils.NetworkCodes
import com.ahmedvargos.remote.R
import javax.inject.Inject

class ErrorCodesMapper @Inject constructor() {

    @Inject
    lateinit var appContext: Context

    fun getMessage(errorCode: Int) = when (errorCode) {
        NetworkCodes.CONNECTION_ERROR,
        NetworkCodes.TIMEOUT_ERROR -> appContext.getString(R.string.connection_time_out)
        else -> appContext.getString(R.string.generic_error)
    }
}
