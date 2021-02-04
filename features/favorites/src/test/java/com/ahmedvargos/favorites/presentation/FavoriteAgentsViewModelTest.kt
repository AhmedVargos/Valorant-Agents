package com.ahmedvargos.favorites.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.favorites.domain.usecases.FavoriteAgentsInquiryUseCase
import com.ahmedvargos.favorites.domain.usecases.FavoriteAgentsToggleUseCase
import com.ahmedvargos.favorites.utils.createTempAgentList
import com.ahmedvargos.favorites.utils.createTempBoolEmissionsFlow
import com.ahmedvargos.favorites.utils.createTempEmissionsFlow
import com.jraska.livedata.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
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

@ExperimentalCoroutinesApi
internal class FavoriteAgentsViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: FavoriteAgentsViewModel

    @RelaxedMockK
    private lateinit var inquiryUseCase: FavoriteAgentsInquiryUseCase

    @RelaxedMockK
    private lateinit var toggleUseCase: FavoriteAgentsToggleUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testCoroutineDispatcher)

        viewModel = FavoriteAgentsViewModel(inquiryUseCase, toggleUseCase)
    }

    @Test
    fun `Given valid list of agents, when invoke, then loading - success`() = runBlockingTest {
        // Arrange
        coEvery { inquiryUseCase() } returns createTempEmissionsFlow(true)
        val resultLiveData = viewModel.agentsStateFlow.asLiveData().test()
        val expectedAgentsList = createTempAgentList()
        // Act
        viewModel.getFavoriteAgents()
        // Assert
        resultLiveData.assertHistorySize(2)
            .assertValue {
                it.status == Resource.Status.SUCCESS &&
                        it.data?.contains(expectedAgentsList[0]) == true
            }
    }

    @Test
    fun `Given invalid list of agents, when invoke, then loading - error`() = runBlockingTest {
        // Arrange
        coEvery { inquiryUseCase() } returns createTempEmissionsFlow(false)
        val resultLiveData = viewModel.agentsStateFlow.asLiveData().test()
        // Act
        viewModel.getFavoriteAgents()
        // Assert
        resultLiveData.assertHistorySize(2)
            .assertValue {
                it.status == Resource.Status.ERROR &&
                        it.messageType?.code == 999
            }
    }

    @Test
    fun `Given toggle success, when invoke, then loading - success`() = runBlockingTest {
        // Arrange
        coEvery { toggleUseCase("1234") } returns
                createTempBoolEmissionsFlow(true)
        val resultLiveData = viewModel.favAgentToggleStateFlow.asLiveData().test()
        // Act
        viewModel.toggleFavoriteAgent("1234")
        // Assert
        resultLiveData.assertHistorySize(2)
            .assertValue {
                it.status == Resource.Status.SUCCESS &&
                        it.data == true
            }
    }

    @Test
    fun `Given invalid agentId error, when invoke, then loading - error`() = runBlockingTest {
        // Arrange
        coEvery { toggleUseCase("-1") } returns
                createTempBoolEmissionsFlow(false)
        val resultLiveData = viewModel.favAgentToggleStateFlow.asLiveData().test()
        // Act
        viewModel.toggleFavoriteAgent("-1")
        // Assert
        resultLiveData.assertHistorySize(2)
            .assertValue {
                it.status == Resource.Status.ERROR &&
                        it.messageType?.code == 999
            }
    }
}
