package com.ahmedvargos.agents_list.domain.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.ahmedvargos.agents_list.domain.repo.AgentsListRepo
import com.ahmedvargos.agents_list.utils.createListOfAgents
import com.ahmedvargos.agents_list.utils.createTempEmissionsFlow
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
internal class AgentsListUseCaseTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var useCase: AgentsListUseCase

    @RelaxedMockK
    private lateinit var agentsRepo: AgentsListRepo

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testCoroutineDispatcher)

        useCase = AgentsListUseCase(agentsRepo)
    }

    @Test
    fun `Given a valid agents list, when invoke(), then receive loading then success`() =
        runBlockingTest {
            // Arrange
            coEvery { agentsRepo.getPopularAgents() } returns createTempEmissionsFlow(true)
            val expectedAgentsList = createListOfAgents()
            // Act
            val resultLiveData = useCase().asLiveData().test()
            // Assert
            resultLiveData.assertHistorySize(2)
                .assertValue {
                    it.status == Resource.Status.SUCCESS &&
                            it.data?.contains(expectedAgentsList[0]) == true
                }
        }

    @Test
    fun `Given an error, when invoke(), then receive loading then error`() =
        runBlockingTest {
            // Arrange
            coEvery { agentsRepo.getPopularAgents() } returns
                    createTempEmissionsFlow(false)
            // Act
            val resultLiveData = useCase().asLiveData().test()
            // Assert
            resultLiveData.assertHistorySize(2)
                .assertValue {
                    it.status == Resource.Status.ERROR && it.messageType?.code == 999
                }
        }
}
