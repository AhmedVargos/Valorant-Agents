package com.ahmedvargos.agent_details.di

import com.ahmedvargos.agent_details.data.AgentDetailsRepoImpl
import com.ahmedvargos.agent_details.data.data_source.AgentDetailsLocalDataSource
import com.ahmedvargos.agent_details.data.data_source.AgentDetailsLocalDataSourceImpl
import com.ahmedvargos.agent_details.domain.repo.AgentDetailsRepo
import com.ahmedvargos.agent_details.domain.usecase.AgentDetailsUseCase
import com.ahmedvargos.agent_details.presentation.AgentDetailsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@ExperimentalCoroutinesApi
fun getAgentDetailsModule() = module {

    factory<AgentDetailsLocalDataSource> {
        AgentDetailsLocalDataSourceImpl(get())
    }

    single<AgentDetailsRepo> {
        AgentDetailsRepoImpl(get())
    }

    factory {
        AgentDetailsUseCase(get())
    }

    viewModel {
        AgentDetailsViewModel(get())
    }
}
