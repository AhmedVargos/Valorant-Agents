package com.ahmedvargos.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.ahmedvargos.agents_list.presentation.AgentsListFragment
import com.ahmedvargos.agents_list.presentation.AgentsListViewModel
import com.ahmedvargos.agents_list.presentation.CacheStateSharedViewModel
import com.ahmedvargos.favorites.presentation.FavoriteAgentsFragment
import com.ahmedvargos.favorites.presentation.FavoriteAgentsViewModel
import com.ahmedvargos.navigator.di.getNavigatorModule
import com.ahmedvargos.uicomponents.view_models.AgentCellViewModel
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
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
class HomeActivityTest : KoinTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var cachedViewModel: CacheStateSharedViewModel

    @RelaxedMockK
    private lateinit var agentCellViewModel: AgentCellViewModel

    @RelaxedMockK
    private lateinit var favoriteAgentsViewModel: FavoriteAgentsViewModel

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
            favoriteAgentsViewModel
        }
        viewModel(override = true) {
            agentsListViewModel
        }
        single {
            AgentsListFragment.newInstance()
        }

        single {
            FavoriteAgentsFragment.newInstance()
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
    fun screenLunchesSuccessfully() {
        // Act
        ActivityScenario.launch(HomeActivity::class.java)
        // Assert
        assertDisplayed(R.id.tvChooseAgent)
    }

    @Test
    fun cachedViewHiddenByDefault() {
        // Act
        ActivityScenario.launch(HomeActivity::class.java)
        // Assert
        assertNotDisplayed(R.id.tvCachedDataDisplayed)
    }

    @Test
    fun cachedViewVisibleWhenCacheViewModelNeeds() {
        // Arrange
        every { cachedViewModel.cachedStateFlow } returns MutableStateFlow(true)
        // Act
        ActivityScenario.launch(HomeActivity::class.java)
        // Assert
        assertDisplayed(R.id.tvCachedDataDisplayed)
    }
}
