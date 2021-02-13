package com.ahmedvargos.agent_details.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.ahmedvargos.agent_details.domain.repo.AgentDetailsRepo
import com.ahmedvargos.agent_details.utils.createTempAgent
import com.ahmedvargos.agent_details.utils.createTempEmissionsFlow
import com.ahmedvargos.agent_details.utils.createTempFailureData
import com.ahmedvargos.base.data.DataSource
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
internal class AgentDetailsUseCaseTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var useCase: AgentDetailsUseCase

    @RelaxedMockK
    private lateinit var agentDetailsRepo: AgentDetailsRepo

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testCoroutineDispatcher)

        useCase = AgentDetailsUseCase(agentDetailsRepo)
    }

    @Test
    fun `Given valid agentId and success, when invoke, then loading - success`() =
        runBlockingTest {
            // Arrange
            coEvery { agentDetailsRepo.getAgentDetails("1234") } returns
                    createTempEmissionsFlow(true)
            val expectedAgentResult = createTempAgent()
            // Act
            val resultLiveData = useCase("1234").asLiveData().test()
            // Assert
            resultLiveData.assertHistorySize(1)
                .assertValueHistory(
                    Resource.Success(expectedAgentResult, DataSource.CACHE)
                )
        }

    @Test
    fun `Given invalid agentId, when invoke, then loading - error`() =
        runBlockingTest {
            // Arrange
            coEvery { agentDetailsRepo.getAgentDetails("-1") } returns
                    createTempEmissionsFlow(false)
            // Act
            val resultLiveData = useCase("-1").asLiveData().test()
            // Assert
            resultLiveData.assertHistorySize(1)
                .assertValueHistory(
                    Resource.Failure(createTempFailureData())
                )
        }
}
