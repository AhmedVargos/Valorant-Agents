package com.ahmedvargos.agents_list.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.ahmedvargos.agents_list.data.data_sources.local.AgentsListLocalSource
import com.ahmedvargos.agents_list.data.data_sources.remote.AgentsListRemoteSource
import com.ahmedvargos.agents_list.domain.repo.AgentsListRepo
import com.ahmedvargos.agents_list.utils.createListOfAgentEntities
import com.ahmedvargos.agents_list.utils.createListOfAgents
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
internal class AgentsListRepoImplTest : AutoCloseKoinTest() {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val repo by inject<AgentsListRepo>()

    @RelaxedMockK
    private lateinit var localDataSource: AgentsListLocalSource

    @RelaxedMockK
    private lateinit var remoteDataSource: AgentsListRemoteSource
    private lateinit var toAgentInfoMapper: AgentEntityToAgentInfoMapper

    private val testModule = module {
        factory<SchedulerProvider> { TestDispatcherProvider() }
        factory<AgentsListRepo> {
            AgentsListRepoImpl(
                remoteDataSource,
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
    fun `Given local + remote, when getPopularAgents(), then get loading - loading - success`() =
        runBlockingTest {
            // Arrange
            coEvery { localDataSource.getPopularAgents() } returns createListOfAgentEntities()
            coEvery { remoteDataSource.getPopularAgents() } returns createListOfAgents()
            // Act
            val resultLiveData = repo.getPopularAgents().asLiveData().test()
            // Assert
            resultLiveData.assertHistorySize(3)
                .assertValueHistory(
                    Resource.loading(),
                    Resource.loading(data = createListOfAgents()),
                    Resource.success(data = createListOfAgents())
                )
        }

    @Test
    fun `Given remote data, when getPopularAgents(), then get loading - loading - success`() =
        runBlockingTest {
            // Arrange
            coEvery { localDataSource.getPopularAgents() } returns mutableListOf()
            coEvery { remoteDataSource.getPopularAgents() } returns createListOfAgents()
            // Act
            val resultLiveData = repo.getPopularAgents().asLiveData().test()
            // Assert
            resultLiveData.assertHistorySize(3)
                .assertValueHistory(
                    Resource.loading(),
                    Resource.loading(data = mutableListOf()),
                    Resource.success(data = createListOfAgents())
                )
        }
}
