package com.ahmedvargos.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ahmedvargos.base.data.AgentInfo

@Entity(tableName = "agent_entity")
class AgentEntity(
    @PrimaryKey
    val id: String,
    val data: AgentInfo,
    var isFav: Boolean = false
)
