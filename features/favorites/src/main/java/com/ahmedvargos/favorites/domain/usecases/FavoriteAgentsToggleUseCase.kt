package com.ahmedvargos.favorites.domain.usecases

import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.base.domain.FlowUseCase
import com.ahmedvargos.favorites.domain.repo.FavoriteAgentsRepo
import kotlinx.coroutines.flow.Flow

class FavoriteAgentsToggleUseCase(
    private val repo: FavoriteAgentsRepo
) : FlowUseCase<String, Boolean>() {

    override suspend fun execute(parameters: String?): Flow<Resource<Boolean>> {
        return repo.toggleFavoriteAgent(parameters ?: "-1")
    }
}
