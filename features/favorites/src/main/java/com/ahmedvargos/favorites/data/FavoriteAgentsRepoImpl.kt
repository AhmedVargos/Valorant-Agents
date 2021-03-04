package com.ahmedvargos.favorites.data

import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.base.utils.SchedulerProvider
import com.ahmedvargos.favorites.data.data_sources.local.FavoritesLocalDataSource
import com.ahmedvargos.favorites.domain.repo.FavoriteAgentsRepo
import com.ahmedvargos.local.mapper.AgentEntityToAgentInfoMapper
import com.ahmedvargos.remote.NetworkBoundResource
import com.ahmedvargos.remote.utils.ErrorCodesMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ExperimentalCoroutinesApi
class FavoriteAgentsRepoImpl @Inject constructor(
    private val localDataSource: FavoritesLocalDataSource,
    private val toAgentInfoMapper: AgentEntityToAgentInfoMapper,
    private val schedulerProvider: SchedulerProvider,
    private val errorCodesMapper: ErrorCodesMapper
) : FavoriteAgentsRepo {
    override suspend fun getFavoriteAgents(): Flow<List<AgentInfo>> {
        return localDataSource.getFavoriteAgents().map {
            it.map(toAgentInfoMapper::map)
        }
    }

    override suspend fun toggleFavoriteAgent(agentId: String): Flow<Resource<Boolean>> {
        return object : NetworkBoundResource<Boolean>(schedulerProvider, errorCodesMapper) {
            override suspend fun remoteFetch(): Boolean {
                return false
            }

            override suspend fun saveFetchResult(data: Boolean) {
            }

            override suspend fun localFetch(): Boolean {
                return localDataSource.toggleFavoriteAgentFav(agentId)
            }

            override fun shouldFetch() = false
        }.asFlow()
    }
}
