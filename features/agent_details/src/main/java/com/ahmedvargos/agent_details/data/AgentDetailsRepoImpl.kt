package com.ahmedvargos.agent_details.data

import com.ahmedvargos.agent_details.data.data_source.AgentDetailsLocalDataSource
import com.ahmedvargos.agent_details.domain.repo.AgentDetailsRepo
import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.remote.NetworkBoundResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class AgentDetailsRepoImpl(
    private val localDataSource: AgentDetailsLocalDataSource
) : AgentDetailsRepo {
    override suspend fun getAgentDetails(agentId: String): Flow<Resource<AgentInfo?>> {
        return object : NetworkBoundResource<AgentInfo?>() {
            override suspend fun remoteFetch(): AgentInfo? {
                return null
            }

            override suspend fun saveFetchResult(data: AgentInfo?) {

            }

            override suspend fun localFetch(): AgentInfo? {
                return localDataSource.getAgentDetails(agentId)?.let { agentEntity ->
                    agentEntity.data.apply {
                        this.isFav = agentEntity.isFav ?: false
                    }
                }
            }

            override fun shouldFetch() = false

        }.asFlow()

    }

    override suspend fun toggleAgentFavState(agentId: String): Flow<Resource<Boolean>> {
        TODO("Not yet implemented")
    }
}