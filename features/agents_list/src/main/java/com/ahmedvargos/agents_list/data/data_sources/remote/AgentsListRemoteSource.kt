package com.ahmedvargos.agents_list.data.data_sources.remote

import com.ahmedvargos.base.data.AgentInfo
import kotlinx.coroutines.flow.Flow

interface AgentsListRemoteSource {
    suspend fun getPopularAgents(): List<AgentInfo>
}