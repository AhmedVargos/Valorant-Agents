package com.ahmedvargos.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ahmedvargos.local.entities.AgentEntity

@Dao
interface AgentsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(agents: List<AgentEntity>)

    @Query("SELECT * from agent_entity")
    suspend fun getAll(): List<AgentEntity>

    @Query("SELECT * from agent_entity WHERE id =:agentId")
    suspend fun getAgent(agentId: String): AgentEntity?
}