package com.ahmedvargos.agents_list.data.data_sources.local

import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.local.dao.AgentsDao
import com.ahmedvargos.local.entities.AgentEntity
import com.ahmedvargos.local.mapper.AgentInfoToEntityMapper

class AgentsListLocalSourceImpl(
    private val agentsDao: AgentsDao,
    private val agentInfoToEntityMapper: AgentInfoToEntityMapper
) : AgentsListLocalSource {
    override suspend fun getPopularAgents(): List<AgentEntity> {
        return agentsDao.getAll()
    }

    override suspend fun saveAgentsList(list: List<AgentInfo>) {
        agentsDao.insertAll(list.map(agentInfoToEntityMapper::map))
    }
}
