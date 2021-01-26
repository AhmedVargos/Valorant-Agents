package com.ahmedvargos.favorites.data.data_sources.local

import com.ahmedvargos.local.dao.FavoritesDao
import com.ahmedvargos.local.entities.AgentEntity
import kotlinx.coroutines.flow.Flow

class FavoritesLocalDataSourceImpl(private val favoritesDao: FavoritesDao) :
    FavoritesLocalDataSource {
    override suspend fun getFavoriteAgents(): Flow<List<AgentEntity>> {
        return favoritesDao.getAllFavorites()
    }

    override suspend fun toggleFavoriteAgentFav(uuid: String): Boolean {
        return favoritesDao.toggleAgentFaveState(uuid)
    }
}
