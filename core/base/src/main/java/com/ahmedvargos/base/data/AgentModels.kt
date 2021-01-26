package com.ahmedvargos.base.data

import com.squareup.moshi.Json

data class AgentsResponse(
    @field:Json(name = "status") val status: Int,
    @field:Json(name = "data") val data: List<AgentInfo>
)

data class AgentInfo(
    @field:Json(name = "uuid") val uuid: String,
    @field:Json(name = "displayName") val displayName: String,
    @field:Json(name = "description") val description: String,
    @field:Json(name = "bustPortrait") val bustPortrait: String?,
    @field:Json(name = "role") val role: Role?,
    @field:Json(name = "abilities") val abilities: List<Ability>,
    @field:Json(name = "isFav") var isFav: Boolean = false
) {
    fun toggleFav() {
        this.isFav = isFav.not()
    }
}

data class Ability(
    val displayName: String?,
    val description: String?,
    val displayIcon: String?
)

data class Role(
    val uuid: String,
    val displayName: String?
)
