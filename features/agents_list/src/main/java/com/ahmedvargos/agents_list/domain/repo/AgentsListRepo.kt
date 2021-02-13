package com.ahmedvargos.agents_list.domain.repo

import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.Resource
import kotlinx.coroutines.flow.Flow

internal interface AgentsListRepo {
    suspend fun getPopularAgents(): Flow<Resource<List<AgentInfo>>>
}
