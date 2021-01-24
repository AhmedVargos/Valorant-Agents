package com.ahmedvargos.agent_details.domain.usecase

import com.ahmedvargos.agent_details.domain.repo.AgentDetailsRepo
import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.Resource
import kotlinx.coroutines.flow.Flow

class AgentDetailsUseCase(
    private val repo: AgentDetailsRepo
) {
    suspend fun invoke(agentId: String): Flow<Resource<AgentInfo?>> {
        return repo.getAgentDetails(agentId)
    }
}