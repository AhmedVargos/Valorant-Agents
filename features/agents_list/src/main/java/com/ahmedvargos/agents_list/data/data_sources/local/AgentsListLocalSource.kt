package com.ahmedvargos.agents_list.data.data_sources.local

import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.local.entities.AgentEntity

interface AgentsListLocalSource {
    suspend fun getPopularAgents(): List<AgentEntity>
    suspend fun saveAgentsList(list: List<AgentInfo>)
}
