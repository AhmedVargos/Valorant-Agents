package com.ahmedvargos.base.data


data class AgentsResponse(
    val status: Int,
    val data: List<AgentInfo>
)

data class AgentInfo(
    val uuid: String,
    val displayName: String,
    val description: String,
    val bustPortrait: String,
    val role: Role?,
    val abilities: List<Abilities>,
    var isFav: Boolean = false
)

data class Abilities(
    val slot: String,
    val displayName: String,
    val description: String,
    val displayIcon: String
)

data class Role(
    val uuid: String,
    val displayName: String?,
    val description: String,
    val displayIcon: String,
)
