package com.ahmedvargos.navigator.di

import com.ahmedvargos.navigator.NavigationActions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NavigatorModule {
    @Provides
    fun provideNavigationActions(): NavigationActions {
        return NavigationActions()
    }
}
