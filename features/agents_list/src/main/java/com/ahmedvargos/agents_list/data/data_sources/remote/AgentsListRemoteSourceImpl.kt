package com.ahmedvargos.agents_list.data.data_sources.remote

import com.ahmedvargos.base.data.AgentInfo

class AgentsListRemoteSourceImpl(private val agentsApi: AgentsListApi) : AgentsListRemoteSource {
    override suspend fun getPopularAgents(): List<AgentInfo> {
        return agentsApi.getPopularAgents().data
    }
}