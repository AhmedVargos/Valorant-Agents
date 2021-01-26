package com.ahmedvargos.favorites.data.data_sources.local

import com.ahmedvargos.local.entities.AgentEntity
import kotlinx.coroutines.flow.Flow

interface FavoritesLocalDataSource {
    suspend fun getFavoriteAgents(): Flow<List<AgentEntity>>
    suspend fun toggleFavoriteAgentFav(uuid: String): Boolean
}
