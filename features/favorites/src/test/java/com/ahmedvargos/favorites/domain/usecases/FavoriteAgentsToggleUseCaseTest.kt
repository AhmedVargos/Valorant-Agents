package com.ahmedvargos.favorites.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.favorites.domain.repo.FavoriteAgentsRepo
import com.ahmedvargos.favorites.utils.createTempBoolEmissionsFlow
import com.ahmedvargos.favorites.utils.createTempFailureData
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
internal class FavoriteAgentsToggleUseCaseTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var useCase: FavoriteAgentsToggleUseCase

    @RelaxedMockK
    private lateinit var favsRepo: FavoriteAgentsRepo

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testCoroutineDispatcher)

        useCase = FavoriteAgentsToggleUseCase(favsRepo)
    }

    @Test
    fun `Given valid agentId and success, when invoke, then loading - success`() =
        runBlockingTest {
            // Arrange
            coEvery { favsRepo.toggleFavoriteAgent("1234") } returns
                    createTempBoolEmissionsFlow(true)
            // Act
            val resultLiveData = useCase.invoke("1234").asLiveData().test()
            // Assert
            resultLiveData.assertHistorySize(2)
                .assertValueHistory(
                    Resource.loading(),
                    Resource.success(true)
                )
        }

    @Test
    fun `Given invalid agentId and error, when invoke, then loading - error`() =
        runBlockingTest {
            // Arrange
            coEvery { favsRepo.toggleFavoriteAgent("-1") } returns
                    createTempBoolEmissionsFlow(false)
            // Act
            val resultLiveData = useCase.invoke("-1").asLiveData().test()
            // Assert
            resultLiveData.assertHistorySize(2)
                .assertValueHistory(
                    Resource.loading(),
                    Resource.error(createTempFailureData())
                )
        }
}
