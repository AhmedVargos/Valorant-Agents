package com.ahmedvargos.favorites.di

import com.ahmedvargos.base.utils.SchedulerProvider
import com.ahmedvargos.favorites.data.FavoriteAgentsRepoImpl
import com.ahmedvargos.favorites.data.data_sources.local.FavoritesLocalDataSource
import com.ahmedvargos.favorites.data.data_sources.local.FavoritesLocalDataSourceImpl
import com.ahmedvargos.favorites.domain.repo.FavoriteAgentsRepo
import com.ahmedvargos.favorites.domain.usecases.FavoriteAgentsInquiryUseCase
import com.ahmedvargos.favorites.domain.usecases.FavoriteAgentsToggleUseCase
import com.ahmedvargos.local.dao.FavoritesDao
import com.ahmedvargos.local.mapper.AgentEntityToAgentInfoMapper
import com.ahmedvargos.remote.utils.ErrorCodesMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Module
@InstallIn(ViewModelComponent::class)
object FavoriteAgentsModule {

    @Provides
    fun provideFavoritesLocalDataSource(
        favoritesDao: FavoritesDao
    ): FavoritesLocalDataSource {
        return FavoritesLocalDataSourceImpl(favoritesDao)
    }

    @Provides
    fun provideFavoriteAgentsRepo(
        localSource: FavoritesLocalDataSource,
        toAgentInfoMapper: AgentEntityToAgentInfoMapper,
        schedulerProvider: SchedulerProvider,
        errorCodesMapper: ErrorCodesMapper
    ): FavoriteAgentsRepo {
        return FavoriteAgentsRepoImpl(
            localSource,
            toAgentInfoMapper,
            schedulerProvider,
            errorCodesMapper
        )
    }

    @Provides
    fun provideFavoriteAgentsInquiryUseCase(
        repo: FavoriteAgentsRepo
    ): FavoriteAgentsInquiryUseCase {
        return FavoriteAgentsInquiryUseCase(repo)
    }

    @Provides
    fun provideFavoriteAgentsToggleUseCase(
        repo: FavoriteAgentsRepo
    ): FavoriteAgentsToggleUseCase {
        return FavoriteAgentsToggleUseCase(repo)
    }
}
