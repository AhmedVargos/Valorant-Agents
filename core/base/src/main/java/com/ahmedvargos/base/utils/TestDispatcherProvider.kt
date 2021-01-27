package com.ahmedvargos.base.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher

@ExperimentalCoroutinesApi
class TestDispatcherProvider : SchedulerProvider {
    override fun io() = TestCoroutineDispatcher()
    override fun ui() = TestCoroutineDispatcher()
    override fun default() = TestCoroutineDispatcher()
}
