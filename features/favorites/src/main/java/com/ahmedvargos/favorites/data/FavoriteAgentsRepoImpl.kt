package com.ahmedvargos.favorites.data

import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.base.data.Resource
import com.ahmedvargos.favorites.data.data_sources.local.FavoritesLocalDataSource
import com.ahmedvargos.favorites.domain.repo.FavoriteAgentsRepo
import com.ahmedvargos.remote.NetworkBoundResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
class FavoriteAgentsRepoImpl(
    private val localDataSource: FavoritesLocalDataSource
) : FavoriteAgentsRepo {
    override suspend fun getFavoriteAgents(): Flow<List<AgentInfo>> {
        return localDataSource.getFavoriteAgents().map {
            it.map { agentEntity ->
                agentEntity.data.apply {
                    this.isFav = agentEntity.isFav
                }
            }
        }
    }

    override suspend fun toggleFavoriteAgent(agentId: String): Flow<Resource<Boolean>> {
        return object : NetworkBoundResource<Boolean>() {
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
