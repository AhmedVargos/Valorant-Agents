package com.ahmedvargos.agent_details.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.ahmedvargos.agent_details.data.data_source.AgentDetailsLocalDataSource
import com.ahmedvargos.agent_details.domain.repo.AgentDetailsRepo
import com.ahmedvargos.agent_details.utils.createTempAgent
import com.ahmedvargos.agent_details.utils.createTempAgentEntity
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.base.utils.SchedulerProvider
import com.ahmedvargos.base.utils.TestDispatcherProvider
import com.ahmedvargos.local.mapper.AgentEntityToAgentInfoMapper
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
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject

@ExperimentalCoroutinesApi
internal class AgentDetailsRepoImplTest : AutoCloseKoinTest() {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val repo by inject<AgentDetailsRepo>()

    @RelaxedMockK
    private lateinit var localDataSource: AgentDetailsLocalDataSource
    private lateinit var toAgentInfoMapper: AgentEntityToAgentInfoMapper

    private val testModule = module {
        factory<SchedulerProvider> { TestDispatcherProvider() }
        factory<AgentDetailsRepo> {
            AgentDetailsRepoImpl(
                localDataSource,
                toAgentInfoMapper,
                get()
            )
        }
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testCoroutineDispatcher)
        startKoin {
            modules(testModule)
        }

        toAgentInfoMapper = AgentEntityToAgentInfoMapper()
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
            resultLiveData.assertHistorySize(2)
                .assertValueHistory(
                    Resource.loading(),
                    Resource.success(expectedResult)
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
            resultLiveData.assertHistorySize(2)
                .assertValueHistory(
                    Resource.loading(),
                    Resource.success(null)
                )
        }
}
