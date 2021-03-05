package com.ahmedvargos.agents_list.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.ahmedvargos.agents_list.data.data_sources.local.AgentsListLocalSource
import com.ahmedvargos.agents_list.data.data_sources.remote.AgentsListRemoteSource
import com.ahmedvargos.agents_list.domain.repo.AgentsListRepo
import com.ahmedvargos.agents_list.utils.createListOfAgentEntities
import com.ahmedvargos.agents_list.utils.createListOfAgents
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
internal class AgentsListRepoImplTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var repo: AgentsListRepo

    @RelaxedMockK
    private lateinit var localDataSource: AgentsListLocalSource

    @RelaxedMockK
    private lateinit var remoteDataSource: AgentsListRemoteSource

    @RelaxedMockK
    private lateinit var errorCodesMapper: ErrorCodesMapper
    private val toAgentInfoMapper by lazy {
        AgentEntityToAgentInfoMapper()
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testCoroutineDispatcher)

        repo = AgentsListRepoImpl(
            remoteDataSource,
            localDataSource,
            toAgentInfoMapper,
            TestDispatcherProvider(),
            errorCodesMapper
        )
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
            resultLiveData.assertHistorySize(2)
                .assertValueHistory(
                    Resource.Success(data = createListOfAgents(), source = DataSource.CACHE),
                    Resource.Success(data = createListOfAgents(), source = DataSource.REMOTE)
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
            resultLiveData.assertHistorySize(2)
                .assertValueHistory(
                    Resource.Success(data = mutableListOf(), DataSource.CACHE),
                    Resource.Success(data = createListOfAgents(), DataSource.REMOTE)
                )
        }
}
