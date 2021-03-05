package com.ahmedvargos.agent_details.presentation

import com.ahmedvargos.agent_details.domain.repo.AgentDetailsRepo
import com.ahmedvargos.agent_details.utils.createTempAgentUI
import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.DataSource
import com.ahmedvargos.base.data.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeAgentDetailsRepo : AgentDetailsRepo {
    override suspend fun getAgentDetails(agentId: String): Flow<Resource<AgentInfo?>> {
        return flow {
            emit(
                Resource.Success(
                    createTempAgentUI(), DataSource.CACHE
                )
            )
        }
    }
}