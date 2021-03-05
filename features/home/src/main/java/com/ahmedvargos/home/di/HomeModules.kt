package com.ahmedvargos.home.di

import com.ahmedvargos.agents_list.presentation.AgentsListFragment
import com.ahmedvargos.agents_list.presentation.CacheStateSharedViewModel
import com.ahmedvargos.favorites.presentation.FavoriteAgentsFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

fun getHomeModules() = module {
    single {
        AgentsListFragment.newInstance()
    }

    single {
        FavoriteAgentsFragment.newInstance()
    }

    viewModel {
        CacheStateSharedViewModel()
    }
}
