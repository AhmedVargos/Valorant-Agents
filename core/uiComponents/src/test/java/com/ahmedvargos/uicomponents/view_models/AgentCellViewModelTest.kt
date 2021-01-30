package com.ahmedvargos.uicomponents.view_models

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ahmedvargos.uicomponents.utils.createTempAgent
import com.jraska.livedata.test
import io.mockk.MockKAnnotations
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
internal class AgentCellViewModelTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: AgentCellViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testCoroutineDispatcher)

        viewModel = AgentCellViewModel()
    }

    @Test
    fun `Given agent for action, when onCellClicked, then emit agent clicked`() = runBlockingTest {
        // Arrange
        val resultLiveData = viewModel.openAgentDetails.test()
        val expectedAgentClicked = createTempAgent()
        // Act
        viewModel.onCellClicked(createTempAgent())
        // Assert
        resultLiveData.assertHistorySize(1)
            .assertValue {
                it.uuid == expectedAgentClicked.uuid
            }
    }
}
