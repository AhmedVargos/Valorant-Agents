package com.ahmedvargos.agent_details.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.ahmedvargos.agent_details.domain.usecase.AgentDetailsUseCase
import com.ahmedvargos.agent_details.utils.createTempAgent
import com.ahmedvargos.agent_details.utils.createTempEmissionsFlow
import com.ahmedvargos.agent_details.utils.createTempFailureData
import com.ahmedvargos.base.data.Resource
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
internal class AgentDetailsViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: AgentDetailsViewModel

    @RelaxedMockK
    private lateinit var usecase: AgentDetailsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testCoroutineDispatcher)

        viewModel = AgentDetailsViewModel(usecase)
    }

    @Test
    fun `Given valid agentId, when invoke, then loading - success`() = runBlockingTest {
        // Arrange
        coEvery { usecase.invoke("1234") } returns createTempEmissionsFlow(true)
        val resultLiveData = viewModel.agentsDetailsStateFlow.asLiveData().test()
        val expectedAgentResult = createTempAgent()
        // Act
        viewModel.getAgentDetails("1234")
        // Assert
        resultLiveData.assertHistorySize(3)
            .assertValueHistory(
                Resource.none(),
                Resource.loading(),
                Resource.success(expectedAgentResult)
            )
    }

    @Test
    fun `Given invalid agentId, when invoke, then loading - error`() = runBlockingTest {
        // Arrange
        coEvery { usecase.invoke("-1") } returns createTempEmissionsFlow(false)
        val resultLiveData = viewModel.agentsDetailsStateFlow.asLiveData().test()
        // Act
        viewModel.getAgentDetails("-1")
        // Assert
        resultLiveData.assertHistorySize(3)
            .assertValueHistory(
                Resource.none(),
                Resource.loading(),
                Resource.error(createTempFailureData())
            )
    }
}
