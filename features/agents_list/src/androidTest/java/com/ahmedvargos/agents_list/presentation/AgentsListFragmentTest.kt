package com.ahmedvargos.agents_list.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.ahmedvargos.agents_list.R
import com.ahmedvargos.agents_list.di.AgentsListModule
import com.ahmedvargos.agents_list.domain.repo.AgentsListRepo
import com.ahmedvargos.agents_list.domain.usecase.AgentsListUseCase
import com.ahmedvargos.agents_list.presentation.utils.createListOfAgentsUI
import com.schibsted.spain.barista.assertion.BaristaListAssertions.assertDisplayedAtPosition
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
import org.junit.runner.RunWith
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@UninstallModules(AgentsListModule::class)
@HiltAndroidTest
class AgentsListFragmentTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Module
    @InstallIn(SingletonComponent::class)
    object TestModule {
        @Singleton
        @Provides
        internal fun provideAgentDetailsUseCase(
            repo: AgentsListRepo
        ): AgentsListUseCase {
            return AgentsListUseCase(repo)
        }
    }

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun givenListOfAgents_ThenShouldShowLoadingThenListOfAgents() {
        // Arrange
        val expectedList = createListOfAgentsUI()
        // Act
        launchFragmentInContainer<AgentsListFragment>()
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

}

@ExperimentalCoroutinesApi
@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AgentsListModule::class]
)
object TestModule {

    @Singleton
    @Provides
    internal fun provideAgentsListRepo(): AgentsListRepo {
        return FakeAgentsListRepo()
    }
}
