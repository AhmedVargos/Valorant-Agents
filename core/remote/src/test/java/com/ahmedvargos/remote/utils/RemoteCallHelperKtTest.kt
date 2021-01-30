package com.ahmedvargos.remote.utils

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahmedvargos.base.utils.NetworkCodes
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import java.io.IOException

@ExperimentalCoroutinesApi
internal class RemoteCallHelperKtTest : AutoCloseKoinTest() {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @RelaxedMockK
    private lateinit var context: Context

    private val testModule = module {
        factory { context }
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testCoroutineDispatcher)
        startKoin {
            modules(testModule)
        }
    }

    @Test
    fun `Given a successful api call, when safeApiCall(), then return success result`() =
        runBlockingTest {
            // Act
            val result = safeApiCall(testCoroutineDispatcher) {
                getSuccessfulResult()
            }
            // Assert
            assert(result is ResultWrapper.Success && result.value == true)
        }

    @Test
    fun `Given a time out exception call, when safeApiCall(), then return error timeout`() =
        runBlockingTest {
            // Arrange
            every { context.getString(any()) } returns "Error test script"
            // Act
            val result = safeApiCall(testCoroutineDispatcher) {
                getTimeoutException()
            }
            // Assert
            assert(result is ResultWrapper.GenericError && result.code
                    == NetworkCodes.CONNECTION_ERROR)
        }

    @Test
    fun `Given a generic exception call, when safeApiCall(), then return error generic`() =
        runBlockingTest {
            // Arrange
            every { context.getString(any()) } returns "Error test script"
            // Act
            val result = safeApiCall(testCoroutineDispatcher) {
                getGenericException()
            }
            // Assert
            assert(result is ResultWrapper.GenericError && result.code
                    == NetworkCodes.GENERIC_ERROR)
        }

    private fun getSuccessfulResult(): Boolean {
        return true
    }

    @Throws(IOException::class)
    private fun getTimeoutException(): Boolean {
        throw IOException("timeout")
    }

    private fun getGenericException(): Boolean {
        throw IllegalStateException("Generic error")
    }
}
