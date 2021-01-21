package com.ahmedvargos.home.di

import com.ahmedvargos.agents_list.presentation.AgentsListFragment
import com.ahmedvargos.favorites.FavoriteAgentsFragment
import org.koin.dsl.module

fun getHomeModules() = module {
    single {
        AgentsListFragment.newInstance()
    }

    single {
        FavoriteAgentsFragment.newInstance()
    }

}