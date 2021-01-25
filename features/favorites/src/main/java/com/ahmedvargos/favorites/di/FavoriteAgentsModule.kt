package com.ahmedvargos.favorites.di

import com.ahmedvargos.favorites.data.FavoriteAgentsRepoImpl
import com.ahmedvargos.favorites.data.data_sources.local.FavoritesLocalDataSource
import com.ahmedvargos.favorites.data.data_sources.local.FavoritesLocalDataSourceImpl
import com.ahmedvargos.favorites.domain.repo.FavoriteAgentsRepo
import com.ahmedvargos.favorites.domain.usecases.FavoriteAgentsInquiryUseCase
import com.ahmedvargos.favorites.domain.usecases.FavoriteAgentsToggleUseCase
import com.ahmedvargos.favorites.presentation.FavoriteAgentsViewModel
import com.ahmedvargos.uicomponents.view_models.AgentCellViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


@ExperimentalCoroutinesApi
fun getFavoriteAgentsModules() = module {

    factory<FavoritesLocalDataSource> {
        FavoritesLocalDataSourceImpl(get())
    }

    single<FavoriteAgentsRepo> {
        FavoriteAgentsRepoImpl(get())
    }

    factory {
        FavoriteAgentsInquiryUseCase(get())
    }
    factory {
        FavoriteAgentsToggleUseCase(get())
    }

    viewModel {
        FavoriteAgentsViewModel(get(), get())
    }
}