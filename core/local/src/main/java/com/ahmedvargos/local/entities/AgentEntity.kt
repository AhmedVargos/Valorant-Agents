package com.ahmedvargos.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "agent_entity")
class AgentEntity(
    @PrimaryKey
    val id: Long,
    val name: String,
    var isFav: Boolean = false
)

