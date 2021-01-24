package com.ahmedvargos.agent_details.domain.repo

import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.Resource
import kotlinx.coroutines.flow.Flow

interface AgentDetailsRepo {
    suspend fun getAgentDetails(agentId: String): Flow<Resource<AgentInfo?>>
    suspend fun toggleAgentFavState(agentId: String): Flow<Resource<Boolean>>
}