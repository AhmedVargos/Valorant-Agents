package com.ahmedvargos.remote.utils

import android.content.Context
import com.ahmedvargos.base.utils.NetworkCodes
import com.ahmedvargos.remote.R
import org.koin.core.KoinComponent
import org.koin.core.inject

object ErrorCodesMapper : KoinComponent {
    private val appContext: Context by inject()

    fun getMessage(errorCode: Int) = when (errorCode) {
        NetworkCodes.CONNECTION_ERROR,
        NetworkCodes.TIMEOUT_ERROR -> appContext.getString(R.string.connection_time_out)
        else -> appContext.getString(R.string.generic_error)
    }
}
