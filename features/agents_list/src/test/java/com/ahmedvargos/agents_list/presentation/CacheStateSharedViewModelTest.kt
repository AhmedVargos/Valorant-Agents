package com.ahmedvargos.agents_list.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.jraska.livedata.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
internal class CacheStateSharedViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: CacheStateSharedViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testCoroutineDispatcher)
        viewModel = CacheStateSharedViewModel()
    }

    @Test
    fun `Given a valid cache true state, when updateCachedDataState(), then emit false - true`() {
        // Arrange
        val testObserver = viewModel.cachedStateFlow.asLiveData()
            .test()
        // Act
        viewModel.updateCachedDataState(true)
        // Assert
        testObserver.assertHistorySize(2)
            .assertValue { value ->
                value == true
            }
    }

    @Test
    fun `Given a valid cache false state, when updateCachedDataState(), then emit false - false`() {
        // Arrange
        val testObserver = viewModel.cachedStateFlow.asLiveData()
            .test()
        // Act
        viewModel.updateCachedDataState(true)
        // Assert
        testObserver.assertHistorySize(2)
            .assertValue { value ->
                value == true
            }
    }
}
