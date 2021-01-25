package com.ahmedvargos.favorites.domain.usecases

import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.favorites.domain.repo.FavoriteAgentsRepo
import kotlinx.coroutines.flow.Flow

class FavoriteAgentsToggleUseCase(
    private val repo: FavoriteAgentsRepo
) {
    suspend fun invoke(agentId: String): Flow<Resource<Boolean>> {
        return repo.toggleFavoriteAgent(agentId)
    }
}