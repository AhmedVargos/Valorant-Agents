package com.ahmedvargos.agents_list.di

import com.ahmedvargos.agents_list.data.AgentsListRepoImpl
import com.ahmedvargos.agents_list.data.data_sources.local.AgentsListLocalSource
import com.ahmedvargos.agents_list.data.data_sources.local.AgentsListLocalSourceImpl
import com.ahmedvargos.agents_list.data.data_sources.remote.AgentsListApi
import com.ahmedvargos.agents_list.data.data_sources.remote.AgentsListRemoteSource
import com.ahmedvargos.agents_list.data.data_sources.remote.AgentsListRemoteSourceImpl
import com.ahmedvargos.agents_list.domain.repo.AgentsListRepo
import com.ahmedvargos.agents_list.domain.usecase.AgentsListUseCase
import com.ahmedvargos.agents_list.presentation.AgentsListViewModel
import com.ahmedvargos.base.utils.ApplicationDispatchersProvider
import com.ahmedvargos.base.utils.SchedulerProvider
import com.ahmedvargos.uicomponents.view_models.AgentCellViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

@ExperimentalCoroutinesApi
fun getAgentsListModule() = module {
    single {
        get<Retrofit>().create(AgentsListApi::class.java)
    }

    factory<AgentsListLocalSource> {
        AgentsListLocalSourceImpl(get(), get())
    }

    factory<AgentsListRemoteSource> {
        AgentsListRemoteSourceImpl(get())
    }

    factory<SchedulerProvider> { ApplicationDispatchersProvider() }

    single<AgentsListRepo> {
        AgentsListRepoImpl(get(), get(), get(), get())
    }

    factory {
        AgentsListUseCase(get())
    }

    viewModel {
        AgentCellViewModel()
    }

    viewModel {
        AgentsListViewModel(get())
    }
}
