package com.ahmedvargos.agents_list.di

import com.ahmedvargos.agents_list.data.AgentsListRepoImpl
import com.ahmedvargos.agents_list.data.data_sources.local.AgentsListLocalSource
import com.ahmedvargos.agents_list.data.data_sources.local.AgentsListLocalSourceImpl
import com.ahmedvargos.agents_list.data.data_sources.remote.AgentsListApi
import com.ahmedvargos.agents_list.data.data_sources.remote.AgentsListRemoteSource
import com.ahmedvargos.agents_list.data.data_sources.remote.AgentsListRemoteSourceImpl
import com.ahmedvargos.agents_list.domain.repo.AgentsListRepo
import com.ahmedvargos.agents_list.domain.usecase.AgentsListUseCase
import com.ahmedvargos.base.utils.SchedulerProvider
import com.ahmedvargos.local.dao.AgentsDao
import com.ahmedvargos.local.mapper.AgentEntityToAgentInfoMapper
import com.ahmedvargos.local.mapper.AgentInfoToEntityMapper
import com.ahmedvargos.remote.utils.ErrorCodesMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.Retrofit

@ExperimentalCoroutinesApi
@Module
@InstallIn(ViewModelComponent::class)
object AgentsListModule {
    @Provides
    internal fun provideAgentsListApi(retrofit: Retrofit): AgentsListApi {
        return retrofit.create(AgentsListApi::class.java)
    }

    @Provides
    internal fun provideAgentsListLocalSource(
        agentsDao: AgentsDao,
        agentInfoToEntityMapper: AgentInfoToEntityMapper
    ): AgentsListLocalSource {
        return AgentsListLocalSourceImpl(agentsDao, agentInfoToEntityMapper)
    }

    @Provides
    internal fun provideAgentsListRemoteSource(
        agentsListApi: AgentsListApi
    ): AgentsListRemoteSource {
        return AgentsListRemoteSourceImpl(agentsListApi)
    }

    @Provides
    internal fun provideAgentsListRepo(
        remoteSource: AgentsListRemoteSource,
        localSource: AgentsListLocalSource,
        toAgentInfoMapper: AgentEntityToAgentInfoMapper,
        schedulerProvider: SchedulerProvider,
        errorCodesMapper: ErrorCodesMapper
    ): AgentsListRepo {
        return AgentsListRepoImpl(
            remoteSource,
            localSource,
            toAgentInfoMapper,
            schedulerProvider,
            errorCodesMapper
        )
    }

    @Provides
    internal fun provideAgentsListUseCase(
        repo: AgentsListRepo
    ): AgentsListUseCase {
        return AgentsListUseCase(repo)
    }
}
