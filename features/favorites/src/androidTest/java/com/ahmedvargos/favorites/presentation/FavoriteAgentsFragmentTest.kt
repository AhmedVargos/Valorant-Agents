package com.ahmedvargos.favorites.presentation

import android.app.Activity
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.ahmedvargos.agents_list.presentation.utils.createListOfAgentsUI
import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.DataSource
import com.ahmedvargos.base.data.FailureData
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.favorites.R
import com.ahmedvargos.navigator.NavigationActions
import com.ahmedvargos.navigator.di.getNavigatorModule
import com.ahmedvargos.uicomponents.view_models.AgentCellViewModel
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
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
class FavoriteAgentsFragmentTest : KoinTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var agentCellViewModel: AgentCellViewModel

    @RelaxedMockK
    private lateinit var favoriteAgentsViewModel: FavoriteAgentsViewModel

    @RelaxedMockK
    private lateinit var navigator: NavigationActions

    /**
     * Mock modules for all the injections in the HomeActivity, AgentsList and
     * FavoriteAgents Fragments.
     */
    private val testModule = module {
        viewModel(override = true) {
            agentCellViewModel
        }
        viewModel(override = true) {
            favoriteAgentsViewModel
        }
        single(override = true) {
            navigator
        }
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        loadKoinModules(listOf(getNavigatorModule(), testModule))
    }

    @After
    fun teardown() {
        unloadKoinModules(listOf(getNavigatorModule(), testModule))
    }

    @Test
    fun givenFavoriteListOfAgents_ThenShouldShowLoadingThenListOfAgents() {
        // Arrange
        val testMutableStateFlow = MutableStateFlow<Resource<List<AgentInfo>>>(Resource.Loading)
        every { favoriteAgentsViewModel.agentsStateFlow } returns testMutableStateFlow
        val expectedList = createListOfAgentsUI()
        // Act
        launchFragmentInContainer<FavoriteAgentsFragment>()
        // Assert
        assertDisplayed(R.id.progress)
        assertNotDisplayed(R.id.rvAgents)
        assertNotDisplayed(R.id.tvNoFavs)
        // Act
        testMutableStateFlow.value = Resource.Success(expectedList, DataSource.CACHE)
        // Assert
        assertNotDisplayed(R.id.progress)
        assertNotDisplayed(R.id.tvNoFavs)
        assertDisplayed(R.id.rvAgents)
        assertRecyclerViewItemCount(R.id.rvAgents, expectedItemCount = expectedList.size)
        assertDisplayedAtPosition(
            R.id.rvAgents,
            0, R.id.tvAgentTitle,
            expectedList[0].displayName
        )
    }

    @Test
    fun givenEmptyFavoritesList_ThenShouldShowLoadingThenNoFavsText() {
        // Arrange
        val testMutableStateFlow = MutableStateFlow<Resource<List<AgentInfo>>>(Resource.Loading)
        every { favoriteAgentsViewModel.agentsStateFlow } returns testMutableStateFlow
        val expectedList = mutableListOf<AgentInfo>()
        // Act
        launchFragmentInContainer<FavoriteAgentsFragment>()
        // Assert
        assertDisplayed(R.id.progress)
        assertNotDisplayed(R.id.rvAgents)
        assertNotDisplayed(R.id.tvNoFavs)
        // Act
        testMutableStateFlow.value = Resource.Success(expectedList, DataSource.CACHE)
        // Assert
        assertNotDisplayed(R.id.progress)
        assertNotDisplayed(R.id.rvAgents)
        assertDisplayed(R.id.tvNoFavs)
    }

    @Test
    fun givenError_ThenShouldShowLoadingThenError() {
        // Arrange
        val testMutableStateFlow = MutableStateFlow<Resource<List<AgentInfo>>>(Resource.Loading)
        every { favoriteAgentsViewModel.agentsStateFlow } returns testMutableStateFlow
        val errorScript = "Test Error"
        // Act
        val scenario = launchFragmentInContainer<FavoriteAgentsFragment>()
        // Assert
        assertDisplayed(R.id.progress)
        assertNotDisplayed(R.id.rvAgents)
        assertNotDisplayed(R.id.tvNoFavs)
        // Act
        testMutableStateFlow.value = Resource.Failure(
            FailureData(999, errorScript)
        )
        // Assert
        assertNotDisplayed(R.id.progress)
        assertNotDisplayed(R.id.rvAgents)
        assertNotDisplayed(R.id.tvNoFavs)

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
