package com.ahmedvargos.local.di

import com.ahmedvargos.local.AppDatabase
import com.ahmedvargos.local.mapper.AgentEntityToAgentInfoMapper
import com.ahmedvargos.local.mapper.AgentInfoToEntityMapper
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

fun getLocalModule() = module {
    single { AppDatabase.buildDatabase(androidContext()) }
    factory { get<AppDatabase>().agentsDao() }
    factory { get<AppDatabase>().favoritesDao() }
    factory { AgentEntityToAgentInfoMapper() }
    factory { AgentInfoToEntityMapper() }
}
