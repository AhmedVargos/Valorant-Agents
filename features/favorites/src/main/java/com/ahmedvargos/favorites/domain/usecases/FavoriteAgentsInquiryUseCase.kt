package com.ahmedvargos.favorites.domain.usecases

import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.favorites.domain.repo.FavoriteAgentsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class FavoriteAgentsInquiryUseCase(
    private val repo: FavoriteAgentsRepo
) {
    suspend fun invoke(): Flow<Resource<List<AgentInfo>>> {
        return repo.getFavoriteAgents().map { Resource.success(it) }
            .onStart {
                emit(Resource.loading())
            }
    }
}
