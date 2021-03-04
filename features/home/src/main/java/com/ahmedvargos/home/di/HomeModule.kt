package com.ahmedvargos.home.di

import com.ahmedvargos.agents_list.presentation.AgentsListFragment
import com.ahmedvargos.favorites.presentation.FavoriteAgentsFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object HomeModule {

    @Provides
    fun provideAgentsListFragment(): AgentsListFragment {
        return AgentsListFragment.newInstance()
    }

    @Provides
    fun provideFavoriteAgentsFragment(): FavoriteAgentsFragment {
        return FavoriteAgentsFragment.newInstance()
    }
}
