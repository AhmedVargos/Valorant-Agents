package com.ahmedvargos.agents_list.domain.usecase

import com.ahmedvargos.agents_list.domain.repo.AgentsListRepo
import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.base.domain.FlowUseCase
import kotlinx.coroutines.flow.Flow

class AgentsListUseCase(
    private val repo: AgentsListRepo
) : FlowUseCase<Nothing?, List<AgentInfo>>() {
    override suspend fun execute(parameters: Nothing?): Flow<Resource<List<AgentInfo>>> {
        return repo.getPopularAgents()
    }
}
