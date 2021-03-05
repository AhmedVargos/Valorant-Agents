package com.ahmedvargos.agents_list.presentation

import com.ahmedvargos.agents_list.domain.repo.AgentsListRepo
import com.ahmedvargos.agents_list.presentation.utils.createListOfAgentsUI
import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.DataSource
import com.ahmedvargos.base.data.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeAgentsListRepo : AgentsListRepo {
    override suspend fun getPopularAgents(): Flow<Resource<List<AgentInfo>>> {
        return flow {
            emit(Resource.Loading)
            emit(
                Resource.Success(
                    createListOfAgentsUI(), DataSource.CACHE
                )
            )
        }
    }
}