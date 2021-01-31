package com.ahmedvargos.favorites.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.base.utils.SchedulerProvider
import com.ahmedvargos.base.utils.TestDispatcherProvider
import com.ahmedvargos.favorites.data.data_sources.local.FavoritesLocalDataSource
import com.ahmedvargos.favorites.domain.repo.FavoriteAgentsRepo
import com.ahmedvargos.favorites.utils.createListOfAgentEntities
import com.ahmedvargos.favorites.utils.createTempAgentList
import com.ahmedvargos.local.mapper.AgentEntityToAgentInfoMapper
import com.jraska.livedata.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
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
internal class FavoriteAgentsRepoImplTest : AutoCloseKoinTest() {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private val repo by inject<FavoriteAgentsRepo>()

    @RelaxedMockK
    private lateinit var localDataSource: FavoritesLocalDataSource
    private lateinit var toAgentInfoMapper: AgentEntityToAgentInfoMapper

    private val testModule = module {
        factory<SchedulerProvider> { TestDispatcherProvider() }
        factory<FavoriteAgentsRepo> {
            FavoriteAgentsRepoImpl(
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
    fun `Given local list of agents, when getFavoriteAgents(), then emit list of agents`() =
        runBlockingTest {
            // Arrange
            coEvery { localDataSource.getFavoriteAgents() } returns flow {
                emit(createListOfAgentEntities())
            }
            val expectedResult = createTempAgentList()
            // Act
            val resultLiveData = repo.getFavoriteAgents().asLiveData().test()
            // Assert
            resultLiveData.assertHistorySize(1)
                .assertValueHistory(expectedResult)
        }

    @Test
    fun `Given valid agentId, when toggleFavoriteAgent(), then emit loading - success`() =
        runBlockingTest {
            // Arrange
            coEvery { localDataSource.toggleFavoriteAgentFav("1234") } returns true
            // Act
            val resultLiveData = repo.toggleFavoriteAgent("1234").asLiveData().test()
            // Assert
            resultLiveData.assertHistorySize(2)
                .assertValueHistory(
                    Resource.loading(),
                    Resource.success(true)
                )
        }

    @Test
    fun `Given invalid agentId, when toggleFavoriteAgent(), then emit loading - success`() =
        runBlockingTest {
            // Arrange
            coEvery { localDataSource.toggleFavoriteAgentFav("-1") } returns false
            // Act
            val resultLiveData = repo.toggleFavoriteAgent("-1").asLiveData().test()
            // Assert
            resultLiveData.assertHistorySize(2)
                .assertValueHistory(
                    Resource.loading(),
                    Resource.success(false)
                )
        }
}
