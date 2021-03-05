package com.ahmedvargos.agent_details.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.ahmedvargos.agent_details.data.data_source.AgentDetailsLocalDataSource
import com.ahmedvargos.agent_details.domain.repo.AgentDetailsRepo
import com.ahmedvargos.agent_details.utils.createTempAgent
import com.ahmedvargos.agent_details.utils.createTempAgentEntity
import com.ahmedvargos.base.data.DataSource
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.base.utils.TestDispatcherProvider
import com.ahmedvargos.local.mapper.AgentEntityToAgentInfoMapper
import com.ahmedvargos.remote.utils.ErrorCodesMapper
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
internal class AgentDetailsRepoImplTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var repo: AgentDetailsRepo

    @RelaxedMockK
    private lateinit var localDataSource: AgentDetailsLocalDataSource

    @RelaxedMockK
    private lateinit var errorCodesMapper: ErrorCodesMapper
    private val toAgentInfoMapper by lazy {
        AgentEntityToAgentInfoMapper()
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testCoroutineDispatcher)

        repo = AgentDetailsRepoImpl(
            localDataSource,
            toAgentInfoMapper,
            TestDispatcherProvider(),
            errorCodesMapper
        )
    }

    @Test
    fun `Given valid agentId, when getAgentDetails(), then emit loading - success`() =
        runBlockingTest {
            // Arrange
            coEvery { localDataSource.getAgentDetails("1234") } returns
                    createTempAgentEntity()
            val expectedResult = createTempAgent()
            // Act
            val resultLiveData = repo.getAgentDetails("1234").asLiveData().test()
            // Assert
            resultLiveData.assertHistorySize(1)
                .assertValueHistory(
                    Resource.Success(expectedResult, DataSource.CACHE)
                )
        }

    @Test
    fun `Given invalid agentId, when getAgentDetails(), then emit loading - success`() =
        runBlockingTest {
            // Arrange
            coEvery { localDataSource.getAgentDetails("1234") } returns null
            // Act
            val resultLiveData = repo.getAgentDetails("1234").asLiveData().test()
            // Assert
            resultLiveData.assertHistorySize(1)
                .assertValueHistory(
                    Resource.Success(null, DataSource.CACHE)
                )
        }
}
