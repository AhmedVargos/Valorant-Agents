package com.ahmedvargos.local.mapper

import com.ahmedvargos.local.entities.AgentEntity
import com.ahmedvargos.local.utils.createTempAgent
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class AgentInfoToEntityMapperTest {

    private lateinit var entityMapper: AgentInfoToEntityMapper

    @Before
    fun setup() {
        entityMapper = AgentInfoToEntityMapper()
    }

    @Test
    fun `Given AgentInfo, when map(), then return expected Entity`() {
        // Arrange
        val tempAgentInfo = createTempAgent()
        val expectedEntity = tempAgentInfo.let { agent ->
            AgentEntity(id = agent.uuid, isFav = agent.isFav, data = agent)
        }
        // Act
        val result = entityMapper.map(tempAgentInfo)
        // Assert
        Assert.assertEquals(result, expectedEntity)
    }
}
