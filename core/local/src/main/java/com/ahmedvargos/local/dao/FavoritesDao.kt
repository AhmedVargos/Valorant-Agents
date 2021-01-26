package com.ahmedvargos.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.ahmedvargos.local.entities.AgentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {

    @Query("SELECT * from agent_entity WHERE isFav = 1")
    fun getAllFavorites(): Flow<List<AgentEntity>>

    @Query("SELECT * from agent_entity WHERE id =:agentId")
    suspend fun getAgent(agentId: String): AgentEntity?

    @Query("UPDATE  agent_entity SET isFav =:newFavState WHERE id =:agentId")
    suspend fun updateAgentFav(agentId: String, newFavState: Boolean)

    @Transaction
    suspend fun toggleAgentFaveState(uuid: String): Boolean {
        val agent = getAgent(uuid)
        val toggleValue = agent?.isFav?.not() ?: false
        updateAgentFav(uuid, toggleValue)
        return true
    }
}
