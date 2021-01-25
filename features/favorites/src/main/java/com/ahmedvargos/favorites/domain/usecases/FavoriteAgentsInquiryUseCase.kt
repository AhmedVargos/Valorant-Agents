package com.ahmedvargos.favorites.domain.usecases

import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.favorites.domain.repo.FavoriteAgentsRepo
import kotlinx.coroutines.flow.Flow

class FavoriteAgentsInquiryUseCase(
    private val repo: FavoriteAgentsRepo
) {
    suspend fun invoke(): Flow<List<AgentInfo>> {
        return repo.getFavoriteAgents()
    }
}