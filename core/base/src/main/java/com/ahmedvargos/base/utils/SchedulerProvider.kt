package com.ahmedvargos.base.utils

import kotlinx.coroutines.CoroutineDispatcher

interface SchedulerProvider {
    fun io(): CoroutineDispatcher
    fun ui(): CoroutineDispatcher
    fun default(): CoroutineDispatcher
}
