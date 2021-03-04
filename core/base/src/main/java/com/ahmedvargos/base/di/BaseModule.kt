package com.ahmedvargos.base.di

import com.ahmedvargos.base.utils.ApplicationDispatchersProvider
import com.ahmedvargos.base.utils.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object BaseModule {

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider {
        return ApplicationDispatchersProvider()
    }
}
