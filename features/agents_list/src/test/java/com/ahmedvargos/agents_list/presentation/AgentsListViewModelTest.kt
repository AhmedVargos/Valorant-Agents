package com.ahmedvargos.agents_list.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import com.ahmedvargos.agents_list.domain.usecase.AgentsListUseCase
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
class AgentsListViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: AgentsListViewModel

    @RelaxedMockK
    private lateinit var agentsListUseCase: AgentsListUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testCoroutineDispatcher)

        viewModel = AgentsListViewModel(agentsListUseCase)
    }

    @Test
    fun `Given a valid agents list, when getPopularAgents(), then receive loading then success`() =
        runBlockingTest {
            // Arrange
            coEvery { agentsListUseCase.invoke() } returns createTempEmissionsFlow(true)
            val resultLiveData = viewModel.agentsStateFlow.asLiveData().test()
            val expectedAgentsList = createListOfAgents()
            // Act
            viewModel.getPopularAgents()
            // Assert
            resultLiveData.assertHistorySize(2)
                .assertValue {
                    it.status == Resource.Status.SUCCESS &&
                            it.data?.contains(expectedAgentsList[0]) == true
                }
        }

    @Test
    fun `Given an error, when getPopularAgents(), then receive loading then error`() =
        runBlockingTest {
            // Arrange
            coEvery { agentsListUseCase.invoke() } returns createTempEmissionsFlow(false)
            val resultLiveData = viewModel.agentsStateFlow.asLiveData().test()
            // Act
            viewModel.getPopularAgents()
            // Assert
            resultLiveData.assertHistorySize(2)
                .assertValue {
                    it.status == Resource.Status.ERROR &&
                            it.messageType?.code == 999
                }
        }
}
