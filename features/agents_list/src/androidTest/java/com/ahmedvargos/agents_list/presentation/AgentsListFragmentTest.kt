package com.ahmedvargos.agents_list.presentation

import android.app.Activity
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.ahmedvargos.agents_list.R
import com.ahmedvargos.agents_list.presentation.utils.createListOfAgentsUI
import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.DataSource
import com.ahmedvargos.base.data.FailureData
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.navigator.di.getNavigatorModule
import com.ahmedvargos.uicomponents.view_models.AgentCellViewModel
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verifySequence
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class AgentsListFragmentTest : KoinTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var cachedViewModel: CacheStateSharedViewModel

    @RelaxedMockK
    private lateinit var agentCellViewModel: AgentCellViewModel

    @RelaxedMockK
    private lateinit var agentsListViewModel: AgentsListViewModel

    /**
     * Mock modules for all the injections in the HomeActivity, AgentsList and
     * FavoriteAgents Fragments.
     */
    private val testModule = module {
        viewModel(override = true) {
            cachedViewModel
        }
        viewModel(override = true) {
            agentCellViewModel
        }
        viewModel(override = true) {
            agentsListViewModel
        }
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        loadKoinModules(listOf(testModule, getNavigatorModule()))
    }

    @After
    fun teardown() {
        unloadKoinModules(listOf(testModule, getNavigatorModule()))
    }

    @Test
    fun givenListOfAgents_ThenShouldShowLoadingThenListOfAgents() {
        // Arrange
        val testMutableStateFlow = MutableStateFlow<Resource<List<AgentInfo>>>(Resource.Loading)
        every { agentsListViewModel.agentsStateFlow } returns testMutableStateFlow
        val expectedList = createListOfAgentsUI()
        // Act
        launchFragmentInContainer<AgentsListFragment>()
        // Assert
        assertDisplayed(R.id.progressView)
        assertNotDisplayed(R.id.rvAgentsList)
        // Act
        testMutableStateFlow.value = Resource.Success(expectedList, DataSource.REMOTE)
        // Assert
        assertNotDisplayed(R.id.progressView)
        assertDisplayed(R.id.rvAgentsList)
        assertRecyclerViewItemCount(R.id.rvAgentsList, expectedItemCount = expectedList.size)
        assertDisplayedAtPosition(
            R.id.rvAgentsList,
            0, R.id.tvAgentTitle,
            expectedList[0].displayName
        )
    }

    @Test
    fun givenCachedListOfAgents_ThenShouldSetCachedStreamToTrue() {
        // Arrange
        val testMutableStateFlow = MutableStateFlow(
            Resource.Success(
                createListOfAgentsUI(),
                DataSource.CACHE
            )
        )
        every { agentsListViewModel.agentsStateFlow } returns testMutableStateFlow
        // Act
        launchFragmentInContainer<AgentsListFragment>()
        // Assert
        verifySequence {
            cachedViewModel.updateCachedDataState(true)
        }
    }

    @Test
    fun givenError_ThenShouldShowLoadingThenErrorToast() {
        // Arrange
        val testMutableStateFlow = MutableStateFlow<Resource<List<AgentInfo>>>(Resource.Loading)
        every { agentsListViewModel.agentsStateFlow } returns testMutableStateFlow
        val errorScript = "Test Error"
        // Act
        val scenario = launchFragmentInContainer<AgentsListFragment>()
        // Assert
        assertDisplayed(R.id.progressView)
        assertNotDisplayed(R.id.rvAgentsList)
        // Act
        testMutableStateFlow.value = Resource.Failure(FailureData(999, errorScript))
        // Assert
        assertNotDisplayed(R.id.progressView)

        var scenarioActivity: Activity? = null
        scenario.onFragment { fragment ->
            scenarioActivity = fragment.activity
        }

        Espresso.onView(ViewMatchers.withText(errorScript))
            .inRoot(
                RootMatchers.withDecorView(
                    CoreMatchers.not(scenarioActivity?.window?.decorView)
                )
            )
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
