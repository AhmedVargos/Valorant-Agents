package com.ahmedvargos.local.di

import android.content.Context
import com.ahmedvargos.local.AppDatabase
import com.ahmedvargos.local.dao.AgentsDao
import com.ahmedvargos.local.dao.FavoritesDao
import com.ahmedvargos.local.mapper.AgentEntityToAgentInfoMapper
import com.ahmedvargos.local.mapper.AgentInfoToEntityMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.buildDatabase(appContext)
    }

    @Provides
    @Singleton
    fun provideAgentsDao(database: AppDatabase): AgentsDao {
        return database.agentsDao()
    }

    @Provides
    @Singleton
    fun provideFavoritesDao(database: AppDatabase): FavoritesDao {
        return database.favoritesDao()
    }

    @Provides
    @Singleton
    fun provideAgentEntityToAgentInfoMapper(): AgentEntityToAgentInfoMapper {
        return AgentEntityToAgentInfoMapper()
    }

    @Provides
    @Singleton
    fun provideAgentInfoToEntityMapper(): AgentInfoToEntityMapper {
        return AgentInfoToEntityMapper()
    }
}
