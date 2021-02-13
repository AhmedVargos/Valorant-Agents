package com.ahmedvargos.favorites.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.DataSource
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.favorites.domain.repo.FavoriteAgentsRepo
import com.ahmedvargos.favorites.utils.createTempAgentList
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

@ExperimentalCoroutinesApi
internal class FavoriteAgentsInquiryUseCaseTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var useCase: FavoriteAgentsInquiryUseCase

    @RelaxedMockK
    private lateinit var favsRepo: FavoriteAgentsRepo

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testCoroutineDispatcher)

        useCase = FavoriteAgentsInquiryUseCase(favsRepo)
    }

    @Test
    fun `Given valid list of fav agents, when invoke, then loading - success`() =
        runBlockingTest {
            // Arrange
            coEvery { favsRepo.getFavoriteAgents() } returns
                    flow {
                        emit(createTempAgentList())
                    }
            val expectedAgentsList = createTempAgentList()
            // Act
            val resultLiveData = useCase().asLiveData().test()
            // Assert
            resultLiveData.assertHistorySize(1)
                .assertValueHistory(
                    Resource.Success(expectedAgentsList, DataSource.CACHE)
                )
        }

    @Test
    fun `Given empty list, when invoke, then loading - success`() =
        runBlockingTest {
            // Arrange
            coEvery { favsRepo.getFavoriteAgents() } returns
                    flow { emit(mutableListOf<AgentInfo>()) }
            // Act
            val resultLiveData = useCase().asLiveData().test()
            // Assert
            resultLiveData.assertHistorySize(1)
                .assertValueHistory(
                    Resource.Success(mutableListOf(), DataSource.CACHE)
                )
        }
}
