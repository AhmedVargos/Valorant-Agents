package com.ahmedvargos.agent_details.data.data_source

import com.ahmedvargos.local.dao.AgentsDao
import com.ahmedvargos.local.entities.AgentEntity

class AgentDetailsLocalDataSourceImpl(
    private val agentsDao: AgentsDao
) : AgentDetailsLocalDataSource {
    override suspend fun getAgentDetails(uuid: String): AgentEntity? {
        return agentsDao.getAgent(uuid)
    }
}
