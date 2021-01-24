package com.ahmedvargos.agent_details.data.data_source

import com.ahmedvargos.local.entities.AgentEntity

interface AgentDetailsLocalDataSource {
    suspend fun getAgentDetails(uuid: String): AgentEntity?
    suspend fun toggleFavState(uuid: String)
}