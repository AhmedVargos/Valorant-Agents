package com.ahmedvargos.agent_details.presentation

import android.app.Activity
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import com.ahmedvargos.agent_details.R
import com.ahmedvargos.agent_details.utils.createTempAgentUI
import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.FailureData
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.favorites.presentation.FavoriteAgentsViewModel
import com.schibsted.spain.barista.assertion.BaristaListAssertions
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.MutableStateFlow
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.dsl.module
import org.koin.test.KoinTest

class AgentDetailsActivityTest : KoinTest {
    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var favoriteAgentsViewModel: FavoriteAgentsViewModel

    @RelaxedMockK
    private lateinit var agentDetailsViewModel: AgentDetailsViewModel

    /**
     * Mock modules for all the injections in the HomeActivity, AgentsList and
     * FavoriteAgents Fragments.
     */
    private val testModule = module {

        viewModel(override = true) {
            agentDetailsViewModel
        }
        viewModel(override = true) {
            favoriteAgentsViewModel
        }
    }

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        loadKoinModules(listOf(testModule))
    }

    @After
    fun teardown() {
        unloadKoinModules(listOf(testModule))
    }

    @Test
    fun screenLunchesSuccessfully() {
        // Act
        ActivityScenario.launch(AgentDetailsActivity::class.java)
        // Assert
        assertDisplayed(R.id.btnBack)
    }

    @Test
    fun givenAgentDetailsModel_ThenShouldShowLoadingThenFillViews() {
        // Arrange
        val testMutableStateFlow = MutableStateFlow<Resource<AgentInfo>>(Resource.loading())
        every { agentDetailsViewModel.agentsDetailsStateFlow } returns testMutableStateFlow
        val expectedAgent = createTempAgentUI()
        // Act
        ActivityScenario.launch(AgentDetailsActivity::class.java)
        // Assert
        assertDisplayed(R.id.progress)
        assertNotDisplayed(R.id.viewsGroup)
        // Act
        testMutableStateFlow.value = Resource.success(expectedAgent)
        // Assert
        assertNotDisplayed(R.id.progress)
        assertDisplayed(R.id.tvAgentName, expectedAgent.displayName)
        assertDisplayed(R.id.tvRoleType, expectedAgent.role?.displayName ?: "")
        assertDisplayed(R.id.rvSkills)
        assertRecyclerViewItemCount(
            R.id.rvSkills,
            expectedItemCount = expectedAgent.abilities.size
        )
        BaristaListAssertions.assertDisplayedAtPosition(
            R.id.rvSkills,
            0, R.id.tvSkillName,
            expectedAgent.abilities[0].displayName ?: ""
        )
    }

    @Test
    fun givenAError_ThenShouldShowLoadingThenError() {
        // Arrange
        val testMutableStateFlow = MutableStateFlow<Resource<AgentInfo>>(Resource.loading())
        every { agentDetailsViewModel.agentsDetailsStateFlow } returns testMutableStateFlow
        val errorScript = "Test Error"
        // Act
        val scenario = ActivityScenario.launch(AgentDetailsActivity::class.java)
        // Assert
        assertDisplayed(R.id.progress)
        assertNotDisplayed(R.id.viewsGroup)
        // Act
        testMutableStateFlow.value = Resource.error(FailureData(999, errorScript))
        // Assert
        assertNotDisplayed(R.id.progress)
        assertNotDisplayed(R.id.tvAgentName)

        var scenarioActivity: Activity? = null
        scenario.onActivity { activity ->
            scenarioActivity = activity
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
