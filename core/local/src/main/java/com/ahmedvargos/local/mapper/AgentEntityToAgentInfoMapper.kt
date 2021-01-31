package com.ahmedvargos.local.mapper

import com.ahmedvargos.base.data.AgentInfo
import com.ahmedvargos.local.entities.AgentEntity

class AgentEntityToAgentInfoMapper : BaseMapper<AgentEntity, AgentInfo>() {
    override fun map(value: AgentEntity): AgentInfo {
        return value.data.apply {
            this.isFav = value.isFav
        }
    }
}
