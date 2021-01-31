package com.ahmedvargos.local.mapper

import com.ahmedvargos.local.utils.createTempAgentEntity
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AgentEntityToAgentInfoMapperTest {

    private lateinit var agentMapper: AgentEntityToAgentInfoMapper

    @Before
    fun setup() {
        agentMapper = AgentEntityToAgentInfoMapper()
    }

    @Test
    fun `Given AgentInfo, when map(), then return expected Entity`() {
        // Arrange
        val tempAgentEntity = createTempAgentEntity()
        val expectedAgentInfo = tempAgentEntity.data.apply {
            this.isFav = tempAgentEntity.isFav
        }
        // Act
        val result = agentMapper.map(tempAgentEntity)
        // Assert
        Assert.assertEquals(result, expectedAgentInfo)
    }
}
