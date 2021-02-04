package com.ahmedvargos.agent_details.domain.usecase

import com.ahmedvargos.agent_details.domain.repo.AgentDetailsRepo
import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.base.domain.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AgentDetailsUseCase(
    private val repo: AgentDetailsRepo
) : FlowUseCase<String, AgentInfo>() {

    override suspend fun execute(parameters: String?): Flow<Resource<AgentInfo>> {
        return repo.getAgentDetails(parameters ?: "-1")
            .map { result ->
                result.data?.let {
                    Resource.success(it)
                } ?: Resource.none()
            }
    }
}
