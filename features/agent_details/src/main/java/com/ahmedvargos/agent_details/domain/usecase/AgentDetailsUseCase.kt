package com.ahmedvargos.agent_details.domain.usecase

import com.ahmedvargos.agent_details.domain.repo.AgentDetailsRepo
import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.base.domain.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class AgentDetailsUseCase @Inject constructor(
    private val repo: AgentDetailsRepo
) : FlowUseCase<String, AgentInfo>() {

    override suspend fun execute(parameters: String?): Flow<Resource<AgentInfo>> {
        return repo.getAgentDetails(parameters ?: "-1")
            .map { result ->
                if (result is Resource.Success<*>) {
                    val successResult = (result as Resource.Success)
                    successResult.data?.let {
                        return@let Resource.Success(it, successResult.source)
                    } ?: Resource.None
                } else
                    result as Resource.Failure
            }
    }
}
