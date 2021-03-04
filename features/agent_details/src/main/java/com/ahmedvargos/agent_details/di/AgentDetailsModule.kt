package com.ahmedvargos.agent_details.di

import com.ahmedvargos.agent_details.data.AgentDetailsRepoImpl
import com.ahmedvargos.agent_details.data.data_source.AgentDetailsLocalDataSource
import com.ahmedvargos.agent_details.data.data_source.AgentDetailsLocalDataSourceImpl
import com.ahmedvargos.agent_details.domain.repo.AgentDetailsRepo
import com.ahmedvargos.agent_details.domain.usecase.AgentDetailsUseCase
import com.ahmedvargos.base.utils.SchedulerProvider
import com.ahmedvargos.local.dao.AgentsDao
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
object AgentDetailsModule {
    @Provides
    internal fun provideAgentDetailsLocalDataSource(
        agentsDao: AgentsDao
    ): AgentDetailsLocalDataSource {
        return AgentDetailsLocalDataSourceImpl(agentsDao)
    }

    @Provides
    internal fun provideAgentDetailsRepo(
        localSource: AgentDetailsLocalDataSource,
        toAgentInfoMapper: AgentEntityToAgentInfoMapper,
        schedulerProvider: SchedulerProvider,
        errorCodesMapper: ErrorCodesMapper
    ): AgentDetailsRepo {
        return AgentDetailsRepoImpl(
            localSource,
            toAgentInfoMapper,
            schedulerProvider,
            errorCodesMapper
        )
    }

    @Provides
    internal fun provideAgentDetailsUseCase(
        repo: AgentDetailsRepo
    ): AgentDetailsUseCase {
        return AgentDetailsUseCase(repo)
    }
}
