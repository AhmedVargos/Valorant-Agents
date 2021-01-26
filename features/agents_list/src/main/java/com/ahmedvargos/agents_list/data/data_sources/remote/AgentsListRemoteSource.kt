package com.ahmedvargos.agents_list.data.data_sources.remote

import com.ahmedvargos.base.data.AgentInfo

interface AgentsListRemoteSource {
    suspend fun getPopularAgents(): List<AgentInfo>
}
