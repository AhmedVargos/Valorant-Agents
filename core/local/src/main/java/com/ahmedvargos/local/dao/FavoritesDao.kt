package com.ahmedvargos.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahmedvargos.local.entities.AgentEntity

@Dao
interface FavoritesDao {
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertFavorite(agentId: String)

    @Query("SELECT * from agent_entity WHERE isFav = 1")
    suspend fun getAllFavorites(): List<AgentEntity>
}