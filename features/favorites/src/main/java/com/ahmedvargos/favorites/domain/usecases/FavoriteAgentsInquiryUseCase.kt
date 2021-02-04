package com.ahmedvargos.favorites.domain.usecases

import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.base.domain.FlowUseCase
import com.ahmedvargos.favorites.domain.repo.FavoriteAgentsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteAgentsInquiryUseCase(
    private val repo: FavoriteAgentsRepo
) : FlowUseCase<Nothing?, List<AgentInfo>>() {
    override suspend fun execute(parameters: Nothing?): Flow<Resource<List<AgentInfo>>> {
        return repo.getFavoriteAgents().map { Resource.success(it) }
    }
}
