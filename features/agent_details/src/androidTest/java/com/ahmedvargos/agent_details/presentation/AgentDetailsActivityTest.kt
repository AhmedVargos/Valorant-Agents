package com.ahmedvargos.agent_details.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import com.ahmedvargos.agent_details.R
import com.ahmedvargos.agent_details.di.AgentDetailsModule
import com.ahmedvargos.agent_details.domain.repo.AgentDetailsRepo
import com.ahmedvargos.agent_details.domain.usecase.AgentDetailsUseCase
import com.ahmedvargos.agent_details.utils.createTempAgentUI
import com.schibsted.spain.barista.assertion.BaristaListAssertions
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@UninstallModules(AgentDetailsModule::class)
@HiltAndroidTest
class AgentDetailsActivityTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    /**
     * Mock modules for all the injections in the HomeActivity, AgentsList and
     * FavoriteAgents Fragments.
     */
    
    @Module
    @InstallIn(SingletonComponent::class)
    object TestModule {

        @Singleton
        @Provides
        internal fun provideAgentDetailsUseCase(
            repo: AgentDetailsRepo
        ): AgentDetailsUseCase {
            return AgentDetailsUseCase(repo)
        }
    }


    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun screenLunchesSuccessfully() {
        // Act
        ActivityScenario.launch(AgentDetailsActivity::class.java)
        // Assert
        assertDisplayed(R.id.btnBack)
    }

    @Test
    fun givenAgentDetailsModel_ThenShouldFillTheViews() {
        // Arrange
        val expectedAgent = createTempAgentUI()
        // Act
        ActivityScenario.launch(AgentDetailsActivity::class.java)
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
}

@ExperimentalCoroutinesApi
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AgentDetailsModule::class]
)
object TestModule {

    @Singleton
    @Provides
    internal fun provideAgentDetailsRepo(): AgentDetailsRepo {
        return FakeAgentDetailsRepo()
    }
}
