package com.ahmedvargos.agents_list.data

import com.ahmedvargos.agents_list.data.data_sources.local.AgentsListLocalSource
import com.ahmedvargos.agents_list.data.data_sources.remote.AgentsListRemoteSource
import com.ahmedvargos.agents_list.domain.repo.AgentsListRepo
import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.remote.NetworkBoundResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class AgentsListRepoImpl(
    private val remoteSource: AgentsListRemoteSource,
    private val localSource: AgentsListLocalSource
) : AgentsListRepo {
    override suspend fun getPopularAgents(): Flow<Resource<List<AgentInfo>>> {
        return object : NetworkBoundResource<List<AgentInfo>>() {
            override suspend fun remoteFetch(): List<AgentInfo> {
                return remoteSource.getPopularAgents()
            }

            override suspend fun saveFetchResult(data: List<AgentInfo>) {
                localSource.saveAgentsList(data)
            }

            override suspend fun localFetch(): List<AgentInfo> {
                return localSource.getPopularAgents().map { agentEntity ->
                    agentEntity.data.apply {
                        this.isFav = agentEntity.isFav
                    }
                }
            }

            override fun shouldFetchWithLocalData() = true
        }.asFlow()
    }
}
