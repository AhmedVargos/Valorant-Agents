package com.ahmedvargos.favorites.domain.repo

import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.Resource
import kotlinx.coroutines.flow.Flow

interface FavoriteAgentsRepo {
    suspend fun getFavoriteAgents(): Flow<List<AgentInfo>>
    suspend fun toggleFavoriteAgent(agentId: String): Flow<Resource<Boolean>>
}
