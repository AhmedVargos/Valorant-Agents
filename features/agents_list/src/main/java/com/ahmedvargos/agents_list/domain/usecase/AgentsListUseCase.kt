package com.ahmedvargos.agents_list.domain.usecase

import com.ahmedvargos.agents_list.domain.repo.AgentsListRepo
import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.Resource
import kotlinx.coroutines.flow.Flow

class AgentsListUseCase(private val repo: AgentsListRepo) {

    suspend fun invoke(): Flow<Resource<List<AgentInfo>>> {
        return repo.getPopularAgents()
    }
}