package com.ahmedvargos.local.mapper

import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.local.entities.AgentEntity

class AgentInfoToEntityMapper : BaseMapper<AgentInfo, AgentEntity>() {
    override fun map(value: AgentInfo): AgentEntity {
        return AgentEntity(value.uuid, value, value.isFav)
    }
}
